import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import javaff.JavaFF;
import javaff.data.Action;
import javaff.data.Fact;
import javaff.data.GroundFact;
import javaff.data.GroundProblem;
import javaff.data.Plan;
import javaff.planning.STRIPSState;
import javaff.search.UnreachableGoalException;
import parser.PDDLParser;

public abstract class OnlineGoalRecognition {

	protected GroundProblem groundProblem;
	protected List<GroundFact> candidateGoals;
	protected GroundFact realGoal;
	protected Set<Fact> initialState;
	protected List<Action> observations;
	protected String recognitionFileName;
	protected STRIPSState initialSTRIPSState;
	
	public OnlineGoalRecognition(String fileName){
		try{
			this.recognitionFileName = fileName;
			if(!Files.isReadable(Paths.get(fileName)))
				throw new IOException(fileName + " not found.");
			
			System.out.println("tar -jxvf " + this.recognitionFileName);
			Process p = Runtime.getRuntime().exec("tar -jxvf " + this.recognitionFileName);
			p.waitFor();
			String domainFilePath = "domain.pddl";
			Path path = Paths.get(domainFilePath);
			String domainContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			domainContent = domainContent.replace("(increase (total-cost) 1)", "");
			File domain = new File("domain.pddl");
			FileWriter fw = new FileWriter(domain.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(domainContent);
			bw.close();
			
			String initialFilePath = "templateInitial.pddl";
			String observationsFilePath = "obs.dat";
			String goalsFilePath = "hyps.dat";
			String realGoalFilePath = "real_hyp.dat";
			path = Paths.get("template.pddl");
			String initialContent = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			initialContent = initialContent.replace("<HYPOTHESIS>", "");
			File templateInitial = new File("templateInitial.pddl");
			fw = new FileWriter(templateInitial.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(initialContent);
			bw.close();
			
			this.groundProblem = PDDLParser.getGroundDomainProblem(domainFilePath, initialFilePath);
			this.observations = PDDLParser.getObservations(groundProblem, observationsFilePath);
			this.candidateGoals = PDDLParser.getGoals(groundProblem, goalsFilePath);
			this.realGoal = PDDLParser.getGoals(groundProblem, realGoalFilePath).get(0);
			this.initialState = groundProblem.getSTRIPSInitialState().getFacts();
			this.initialSTRIPSState = groundProblem.getSTRIPSInitialState();
		} catch (IOException | InterruptedException e){
			e.printStackTrace();
		}
	}
	
	Plan doPlanJavaFF(Set<Fact> initialState, GroundFact goalState) throws UnreachableGoalException{
		GroundProblem gp = (GroundProblem) this.groundProblem.clone();
		String domainPath = this.groundProblem.getDomainPath();
		gp.setInitial(initialState);
		gp.setGoal(goalState);
		JavaFF ff = new JavaFF(domainPath);
		return ff.plan(gp, false);
	}
	
	public float match(float mG, float iG){
		return (iG/mG);
	}
	
	public Plan doPlan(Set<Fact> state, Set<Fact> goalState){
		return null;
	}
	
	public float getNormalizingFactor(List<Float> goalsScore){
		return 0;
	}
}