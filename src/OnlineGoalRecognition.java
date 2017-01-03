import java.util.List;
import java.util.Set;

import javaff.data.Action;
import javaff.data.Fact;
import javaff.data.GroundFact;
import javaff.data.GroundProblem;
import javaff.data.Plan;
import javaff.planning.STRIPSState;
import parser.PDDLParser;

public abstract class OnlineGoalRecognition {

	private List<GroundFact> candidateGoals;
	private Set<Fact> initialState;
	private List<Action> observations;
	
	public OnlineGoalRecognition(String domainName){
		String domainFilePath = "experiments/" + domainName + "/" + domainName +".pddl";
		String initialFilePath = "experiments/" + domainName + "/initial.pddl";
		String observationsFilePath = "experiments/" + domainName + "/observations.pddl";
		String goalsFilePath = "experiments/" + domainName + "/goals.pddl";
		
		GroundProblem groundProblem = PDDLParser.getGroundDomainProblem(domainFilePath, initialFilePath);
		List<Action> observations = PDDLParser.getObservations(groundProblem, observationsFilePath);
		this.candidateGoals = PDDLParser.getGoals(groundProblem, goalsFilePath);
		
		System.out.println(observations);
		STRIPSState currentState = groundProblem.getSTRIPSInitialState();
		System.out.println("+ Initial state: " + currentState);
		this.initialState = currentState.getFacts();
		
		System.out.println("+ Goal(s) state: ");			
		for(GroundFact goal: this.candidateGoals)
			System.out.println(goal);
	}
	
	public Set<Fact> getInitialState() {
		return initialState;
	}
	
	public List<Action> getObservations() {
		return observations;
	}
	
	public float getScore(float mG, float iG){
		return 0;
	}
	
	public Plan doPlan(Set<Fact> state, Set<Fact> goalState){
		return null;
	}
	
	public float getNormalizingFactor(List<Float> goalsScore){
		return 0;
	}
}