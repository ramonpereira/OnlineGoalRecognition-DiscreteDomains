package recognizer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javaff.data.Action;
import javaff.data.Fact;
import javaff.data.GroundFact;
import javaff.planning.STRIPSState;
import javaff.search.UnreachableGoalException;
import bean.GoalRecognitionResult;

/**
 * Online Goal Recognition Approach that uses a landmark-based heuristic (uniqueness heuristic).
 * @author ramonfragapereira
 *
 */
public class OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic extends OnlineGoalRecognition {

	private float threshold;
	private Map<GroundFact, Set<Set<Fact>>> mapGoalsLandmarks = new HashMap<>();
	private Map<Set<Fact>, Float> landmarksUniqueness = new HashMap<>();
	private Map<GroundFact, Float> goalsTotalLandmarksUniqueness = new HashMap<>();
	
	public OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic(String fileName, float threshold) {
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
		this.verifyFactLandmarksUniqueness();
		for(Action o: this.observations){
			System.out.println("$> Observation (" + (int) observationCounter + ") :" + o);
			currentState = (STRIPSState) currentState.apply(o);
			Map<GroundFact, Float> goalsToEstimateCompletion = new HashMap<>();
			for(GroundFact goal: this.candidateGoals){
				this.computeAchievedLandmarks(goal, o, currentState);
				float estimatedValueOfGoalCompletion = this.heuristicLandmarksUniqueness(goal);
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
		return new GoalRecognitionResult(topFirstRankedPercent, convergencePercent, this.candidateGoals.size(), this.observations.size(), this.getAverageOfFactLandmarks(), 0);
	}
	
	public void verifyFactLandmarksUniqueness(){
		Set<Set<Fact>> extractedLandmarks = new HashSet<>();
		for(GroundFact goal: this.candidateGoals){
			Set<Set<Fact>> landmarksGoal = this.goalsToLandmarks.get(goal).getLandmarks();
			extractedLandmarks.addAll(landmarksGoal);
			this.mapGoalsLandmarks.put(goal, landmarksGoal);
		}
		for(Set<Fact> landmark: extractedLandmarks){
			int count = 0;
			for(GroundFact goal: this.candidateGoals){
				Set<Set<Fact>> goalLandmarks = this.mapGoalsLandmarks.get(goal);
				if(goalLandmarks.contains(landmark))
					count++;
			}
			this.landmarksUniqueness.put(landmark, new Float((float) 1/count));
		}
		for(GroundFact goal: this.candidateGoals){
			Set<Set<Fact>> goalLandmarks = this.mapGoalsLandmarks.get(goal);
			float total = 0;
			for(Set<Fact> landmark: goalLandmarks){
				float landmarkValue = this.landmarksUniqueness.get(landmark);
				total = total + landmarkValue;
			}
			this.goalsTotalLandmarksUniqueness.put(goal, total);
		}
	}
	
	private float heuristicLandmarksUniqueness(GroundFact goal){
		Set<Set<Fact>> observedLandmarksGoal = this.goalsToObservedLandmarks.get(goal);
		float totalGoal = 0f;
		for(Set<Fact> obsLandmark: observedLandmarksGoal){
			float landmarkValue = this.landmarksUniqueness.get(obsLandmark);
			totalGoal = totalGoal + landmarkValue; 
		}
		float totalEstimateGoal = (totalGoal / this.goalsTotalLandmarksUniqueness.get(goal));
		BigDecimal totalEstimateGoalBigDecimal = new BigDecimal(totalEstimateGoal);
		totalEstimateGoal = totalEstimateGoalBigDecimal.setScale(2, BigDecimal.ROUND_UP).floatValue();
		return totalEstimateGoal;
	}
}
