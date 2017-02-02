package recognizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javaff.data.Action;
import javaff.data.Fact;
import javaff.data.GroundFact;
import javaff.planning.STRIPSState;
import javaff.search.UnreachableGoalException;
import landmark.LandmarkOrdering;
import bean.GoalRecognitionResult;

/**
 * Online Goal Recognition Approach that uses a landmark-based heuristic (goal completion heuristic).
 * @author ramonfragapereira
 *
 */
public class OnlineGoalRecognitionUsingLandmarksGoalCompletion extends OnlineGoalRecognition {

	private float threshold;
	
	public OnlineGoalRecognitionUsingLandmarksGoalCompletion(String fileName, float threshold) {
		super(fileName);
		this.threshold = threshold;
	}

	@Override
	public GoalRecognitionResult recognizeOnline() throws UnreachableGoalException, IOException, InterruptedException {
		STRIPSState currentState = this.initialSTRIPSState;
		System.out.println("#> Real Goal: " + this.realGoal);
		int observationCounter = 0;
		float topFirstFrequency = 0;
		float convergenceToTopRankedGoal = 0;
		Set<GroundFact> recognizedGoals = new HashSet<>();
		this.extractLandmarks();
		for(Action o: this.observations){
			System.out.println("$> Observation (" + (int) observationCounter + ") :" + o);
			currentState = (STRIPSState) currentState.apply(o);
			Map<GroundFact, Float> goalsToEstimateCompletion = new HashMap<>();
			for(GroundFact goal: this.candidateGoals){
				this.computeAchievedLandmarks(goal, o, currentState);
				this.countAchievedLandmarksForSubgoals(goal);
				float estimatedValueOfGoalCompletion = this.heuristicLandmarksGoalCompletion(goal);
				System.out.println("\t-> " +  goal + ": " + estimatedValueOfGoalCompletion);
				goalsToEstimateCompletion.put(goal, estimatedValueOfGoalCompletion);
			}
			float maxGoalCompletion = 0f;
			for(GroundFact goal: this.candidateGoals){
				float goalCompletion = goalsToEstimateCompletion.get(goal);
				if(goalCompletion > maxGoalCompletion)
					maxGoalCompletion = goalCompletion;
			}
			recognizedGoals = new HashSet<>();
			for(GroundFact goal: this.candidateGoals)
				if(goalsToEstimateCompletion.get(goal) >= (maxGoalCompletion - this.threshold))
					recognizedGoals.add(goal);
			
			if(recognizedGoals.size() == 1 && recognizedGoals.contains(this.realGoal)){
				topFirstFrequency++;
				convergenceToTopRankedGoal++;
			} else convergenceToTopRankedGoal = 0;
			observationCounter++;
		}
		float topFirstRankedPercent  = (topFirstFrequency/observationCounter);
		float convergencePercent = (convergenceToTopRankedGoal/observationCounter);
		System.out.println("\n$$$$####> Top First Ranked Percent (%): " + topFirstRankedPercent);
		System.out.println("$$$$####> Convergence Percent (%): " + convergencePercent);
		System.out.println("$$$$####> Top Ranked First times: " + topFirstFrequency);
		System.out.println("$$$$####> Total Observed Actions: " + observationCounter);
		return new GoalRecognitionResult(topFirstRankedPercent, convergencePercent);
	}
	
	private float heuristicLandmarksGoalCompletion(GroundFact goal){
		float subgoalCompletion = 0f;
		List<LandmarkOrdering> subgoalLandmarksOrdering = this.goalsToLandmarks.get(goal).getLandmarksOrdering();
		Map<Fact, Integer> subgoalsAchievedLandmarks = this.goalsToAchievedLandmarksCounter.get(goal);
		for(LandmarkOrdering subgoalLandmarks: subgoalLandmarksOrdering){
			float subgoalAmountOfAchievedLandmarks = subgoalsAchievedLandmarks.get(subgoalLandmarks.getSubGoal());
			float subgoalAmountOfLandmarks = subgoalLandmarks.getAmountOfLandmarks();
			subgoalCompletion += (subgoalAmountOfAchievedLandmarks / subgoalAmountOfLandmarks);
		}
		return (subgoalCompletion / goal.getFacts().size());
	}
}