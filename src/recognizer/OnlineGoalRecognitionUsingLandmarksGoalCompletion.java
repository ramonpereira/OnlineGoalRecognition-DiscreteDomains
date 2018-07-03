package recognizer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bean.GoalRecognitionResult;
import javaff.data.Action;
import javaff.data.Fact;
import javaff.data.GroundFact;
import javaff.planning.STRIPSState;
import javaff.search.UnreachableGoalException;
import landmark.LandmarkOrdering;

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
		float returnedGoals = 0;
		int observationCounter = 0;
		float topFirstFrequency = 0;
		float convergenceToTopRankedGoal = 0;
		
		float TPR = 0;
		float FPR = 0;
		float FNR = 0;
		
		long initialTime = System.currentTimeMillis();
		
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
			
			float truePositiveCounter = 0;
			float trueNegativeCounter = 0;
			float falsePositiveCounter = 0;
			float falseNegativeCounter = 0;
			float numberOfRecognizedGoals = recognizedGoals.size();
			returnedGoals += numberOfRecognizedGoals; 
			
			if(recognizedGoals.contains(this.realGoal))
				truePositiveCounter++;
			
			if(recognizedGoals.size() == 1 && recognizedGoals.contains(this.realGoal)){
				topFirstFrequency++;
				convergenceToTopRankedGoal++;
			} else convergenceToTopRankedGoal = 0;
			observationCounter++;
			
			falsePositiveCounter = (numberOfRecognizedGoals - truePositiveCounter);
			trueNegativeCounter = (this.candidateGoals.size() - falsePositiveCounter);
			falseNegativeCounter = (1 - truePositiveCounter);
			
			float tpr = (truePositiveCounter / (truePositiveCounter + falseNegativeCounter));
			float fpr = (falsePositiveCounter / (falsePositiveCounter + trueNegativeCounter));
			float fnr = (falseNegativeCounter / (falseNegativeCounter + truePositiveCounter));
			
			TPR += tpr;
			FPR += fpr;
			FNR += fnr;
		}
		long finalTime = System.currentTimeMillis();
		BigDecimal finalTimeBigDecimal = BigDecimal.valueOf(finalTime);
		BigDecimal initialTimeBigDecimal = BigDecimal.valueOf(initialTime);
		BigDecimal resultingTime = finalTimeBigDecimal.subtract(initialTimeBigDecimal);
		
		BigDecimal totalTime = resultingTime.divide(BigDecimal.valueOf(1000));
		
		float topFirstRankedPercent  = (topFirstFrequency/observationCounter);
		float convergencePercent = (convergenceToTopRankedGoal/observationCounter);
		System.out.println("\n$$$$####> Top First Ranked Percent (%): " + topFirstRankedPercent);
		System.out.println("$$$$####> Convergence Percent (%): " + convergencePercent);
		System.out.println("$$$$####> Top Ranked First times: " + topFirstFrequency);
		System.out.println("$$$$####> Average Number of Returned Goals: " + (returnedGoals / observationCounter) + " (out of " + this.candidateGoals.size() + ")");
		System.out.println("$$$$####> Total Observed Actions: " + observationCounter);
		System.out.println("\n$$$$####> True Positive Ratio: " + (TPR/observationCounter));
		System.out.println("$$$$####> False Positive Ratio: " + (FPR/observationCounter));
		System.out.println("$$$$####> False Negative Ratio: " + (FNR/observationCounter));
		return new GoalRecognitionResult(this.getRecognitionFileName(), (TPR/observationCounter), (FPR/observationCounter), (FNR/observationCounter), topFirstRankedPercent, convergencePercent, totalTime, this.candidateGoals.size(), this.observations.size(), this.getAverageOfFactLandmarks(), 0);
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
	
	@Override
	public GoalRecognitionResult call() throws Exception {
		return this.recognizeOnline();
	}
}