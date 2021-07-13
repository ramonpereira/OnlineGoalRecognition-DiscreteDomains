package recognizer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javaff.JavaFF;
import javaff.data.Action;
import javaff.data.Fact;
import javaff.data.GroundFact;
import javaff.data.GroundProblem;
import javaff.data.Plan;
import javaff.planning.PlanningGraph;
import javaff.planning.STRIPSState;
import javaff.search.UnreachableGoalException;
import landmark.LandmarkExtractor;
import landmark.LandmarkOrdering;
import parser.PDDLParser;
import extracting.PartialLandmarkGenerator;

public abstract class GoalRecognition implements Callable<GoalRecognitionResult> {

	protected GroundProblem groundProblem;
	protected List<GroundFact> candidateGoals;
	protected GroundFact realGoal;
	protected Set<Fact> initialState;
	protected List<Action> observations;
	protected String recognitionFileName;
	protected STRIPSState initialSTRIPSState;
	protected STRIPSState initialStateSTRIPS;
	protected Map<GroundFact, LandmarkExtractor> goalsToLandmarks;
	protected Map<GroundFact, Set<Set<Fact>>> goalsToObservedLandmarks;
	protected Map<GroundFact, Map<Fact, Integer>> goalsToAchievedLandmarksCounter;
	
	public abstract GoalRecognitionResult recognizeOnline() throws UnreachableGoalException, IOException, InterruptedException;
	
	public abstract GoalRecognitionResult recognizeOffline() throws UnreachableGoalException, IOException, InterruptedException;
	
	public GoalRecognition(String fileName){
		try{
			this.recognitionFileName = fileName;
			if(!Files.isReadable(Paths.get(fileName)))
				throw new IOException(fileName + " not found.");

			String cmdRemovingFiles = "rm -rf domain.pddl template.pddl templateInitial.pddl obs.dat hyps.dat plan.png real_hyp.dat";
			System.out.println(cmdRemovingFiles);
			Process p = Runtime.getRuntime().exec(cmdRemovingFiles);
			p.waitFor();
			System.out.println("tar -jxvf " + this.recognitionFileName);
			p = Runtime.getRuntime().exec("tar -jxvf " + this.recognitionFileName);
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
			this.initialStateSTRIPS = groundProblem.getSTRIPSInitialState();
			this.initialSTRIPSState = groundProblem.getSTRIPSInitialState();
			this.initialState = groundProblem.getSTRIPSInitialState().getFacts();
			
			this.goalsToAchievedLandmarksCounter = new HashMap<>();
			this.goalsToLandmarks = new HashMap<>();
			this.goalsToObservedLandmarks = new HashMap<>();
		} catch (IOException | InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Fast Forward - Suboptimal Planner
	 * @param initialState
	 * @param goalState
	 * @return
	 * @throws UnreachableGoalException
	 */
	Plan doPlanJavaFF(Set<Fact> initialState, GroundFact goalState) throws UnreachableGoalException{
		GroundProblem gp = (GroundProblem) this.groundProblem.clone();
		String domainPath = this.groundProblem.getDomainPath();
		gp.setInitial(initialState);
		gp.setGoal(goalState);
		JavaFF ff = new JavaFF(domainPath);
		return ff.plan(gp, false);
	}

	/**
	 * Graphplan - Optimal Planner
	 * @param initialState
	 * @param goalState
	 * @return Plan
	 * @throws UnreachableGoalException
	 */
	Plan doPlanGraphPlan(Set<Fact> initialState, GroundFact goalState) throws UnreachableGoalException{
		GroundProblem gp = (GroundProblem) this.groundProblem.clone();
		gp.setState(new STRIPSState(gp.getActions(), initialState, goalState));
		gp.getSTRIPSInitialState().setGoal(goalState);
		PlanningGraph graphPlan = new PlanningGraph(gp);
		return graphPlan.getPlan(gp.getSTRIPSInitialState());
	}
	
	void extractLandmarks(){
		for(GroundFact goal: this.candidateGoals){
			PartialLandmarkGenerator landmarkGenerator = new PartialLandmarkGenerator(this.initialStateSTRIPS, goal.getFacts(), groundProblem.getActions());
			landmarkGenerator.extractLandmarks();
			this.goalsToLandmarks.put(goal, landmarkGenerator);
			this.computeAchievedLandmarks(goal, null, initialStateSTRIPS);
			this.countAchievedLandmarksForSubgoals(goal);
		}
	}
	
	/**
	 * Computing achieved landmarks for a goal in an observed action.
	 * @param GroundFact goal
	 * @param Action o
	 */
	void computeAchievedLandmarks(GroundFact goal, Action o, STRIPSState currentState){
		List<LandmarkOrdering> landmarksOrdering = this.goalsToLandmarks.get(goal).getLandmarksOrdering();
		Set<Set<Fact>> observedLandmarks = this.goalsToObservedLandmarks.get(goal);
		if(observedLandmarks == null){
			observedLandmarks = new HashSet<>();
			this.goalsToObservedLandmarks.put(goal, observedLandmarks);
		}
		Set<Fact> observedFacts = new HashSet<>();
		if(o != null){
			observedFacts.addAll(o.getAddPropositions());
			observedFacts.addAll(o.getPreconditions());
		}
		observedFacts.addAll(currentState.getFacts());
		
		for(LandmarkOrdering landmarkOrdering: landmarksOrdering)
			for(Set<Fact> factsOrdering: landmarkOrdering.getOrdering())
				if(observedFacts.containsAll(factsOrdering) && !observedLandmarks.contains(factsOrdering))
					observedLandmarks.add(factsOrdering);

		this.goalsToObservedLandmarks.replace(goal, observedLandmarks);
	}

	/**
	 * Counting achieved landmarks for every subgoals.
	 * @param GroundFact goal
	 */
	void countAchievedLandmarksForSubgoals(GroundFact goal){
		Map<Fact, Integer> subgoalsAchievedLandmarks = new HashMap<>();
		List<LandmarkOrdering> landmarksOrdering = this.goalsToLandmarks.get(goal).getLandmarksOrdering();
		Set<Set<Fact>> observedLandmarks = this.goalsToObservedLandmarks.get(goal);
		for(LandmarkOrdering landmarkOrdering: landmarksOrdering){
			int subgoalCounter = 0;
			for(Set<Fact> obsLandmark: observedLandmarks)
				if(landmarkOrdering.getOrdering().contains(obsLandmark))
					subgoalCounter++;
			
			subgoalsAchievedLandmarks.put(landmarkOrdering.getSubGoal(), subgoalCounter);
		}
		goalsToAchievedLandmarksCounter.put(goal, subgoalsAchievedLandmarks);
	}
	
	void computeRecognitionResults(){
	}
	
	/**
	 * We round down to 1 because we allow the use of a 
	 * sub-optimal planner (JavaFF, obs: JavaFF is faster than GraphPlan).
	 * @param mG
	 * @param iG
	 * @return float
	 */
	public float match(float mG, float iG){
		float mathing = (iG/mG);
		return (mathing > 1 ? 1 : mathing);
	}
	
	public float getNormalizingFactor(List<Float> goalsScore){
		return 0;
	}
	
	public float getAverageOfFactLandmarks(){
		if(goalsToLandmarks.keySet().size() == 0) 
			return 0;
		
		int size = 0;
		for(GroundFact goal: this.candidateGoals){
			LandmarkExtractor landmarkGenerator = goalsToLandmarks.get(goal);
			size += (landmarkGenerator == null ? 0 : landmarkGenerator.getFactLandmarks().size());	
		}
		float avg = (size / this.candidateGoals.size());
		return avg;
	}
	
	public static void writeExperimentFile(String content, String outputFile) throws IOException{
		File file = new File(outputFile + ".txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
		System.out.println("\nWriting file " + file.getAbsolutePath());
	}
	
	public int getCandidateGoalsSize() {
		return candidateGoals.size();
	}
	
	public int getObservationsSize() {
		return observations.size();
	}
	
	public String getRecognitionFileName() {
		return recognitionFileName;
	}
}