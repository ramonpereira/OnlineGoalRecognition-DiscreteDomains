package recognizer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javaff.data.Action;
import javaff.data.GroundFact;
import javaff.data.Plan;
import javaff.planning.STRIPSState;
import javaff.search.UnreachableGoalException;
import bean.GoalRecognitionResult;

public class OnlineGoalRecognitionUsingLandmarksWithBaseline extends OnlineGoalRecognition {

	private float threshold;
	
	public OnlineGoalRecognitionUsingLandmarksWithBaseline(String fileName, float threshold) {
		super(fileName);
		this.threshold = threshold;
	}

	@Override
	public GoalRecognitionResult recognizeOnline() throws UnreachableGoalException, IOException, InterruptedException {
		Map<GroundFact, List<Action>> mObservationsGoals = new HashMap<>();
		Map<GroundFact, Plan> goalsIdealPlans= new HashMap<>();
		STRIPSState currentState = this.initialSTRIPSState;
		System.out.println("#> Real Goal: " + this.realGoal);
		float returnedGoals = 0;
		float numberOfCallsPlanner = 0;
		int observationCounter = 0;
		float topFirstFrequency = 0;
		float convergenceToTopRankedGoal = 0;
		this.extractLandmarks();
		
		float TPR = 0;
		float FPR = 0;
		float FNR = 0;
		
		long initialTime = System.currentTimeMillis();
		
		for(GroundFact goal: this.candidateGoals){
			System.out.println("\t # Goal:" + goal);
			Plan idealPlan = doPlanJavaFF(initialState, goal);
			numberOfCallsPlanner++;
			goalsIdealPlans.put(goal, idealPlan);
		}
		for(Action o: this.observations){
			System.out.println("$> Observation (" + (int) observationCounter + ") :" + o);
			observationCounter++;
			currentState = (STRIPSState) currentState.apply(o);
			Map<GroundFact, Float> goalsToScores = new HashMap<>();
			Map<GroundFact, Integer> goalsToMPlus = new HashMap<>();
			Set<GroundFact> filteredCandidateGoals = new HashSet<>();
			float sumOfScores = 0f;
			for(GroundFact goal: this.candidateGoals){
				this.computeAchievedLandmarks(goal, o, currentState);
				List<Action> mMinus = mObservationsGoals.get(goal);
				if(mMinus == null){
					List<Action> mMinusNew = new ArrayList<Action>();
					mMinus = mMinusNew;
					mMinusNew.add(o);
					mObservationsGoals.put(goal, mMinusNew);
				} else mMinus.add(o);
			}
			filteredCandidateGoals = this.filterCandidateGoalsUsingLandmarks();
			System.out.println("\n\t # Filtered Goals (out of " + this.candidateGoals.size() + "): " + filteredCandidateGoals.size());
			for(GroundFact goal: filteredCandidateGoals){
				System.out.println("\n\t # Goal:" + goal);
				Plan idealPlanOfG = goalsIdealPlans.get(goal);
				System.out.println("\t # Ideal Plan of G: " + idealPlanOfG.getPlanLength());
				Plan mPlus = doPlanJavaFF(currentState.getFacts(), goal);
				numberOfCallsPlanner++;
				List<Action> mMinus = mObservationsGoals.get(goal);
				System.out.println("\t # mMinus: " + mMinus.size());
				System.out.println("\t # mPlus: " + mPlus.getPlanLength());
				float mG = mMinus.size() + mPlus.getPlanLength();
				float score = this.match(mG, idealPlanOfG.getPlanLength());
				System.out.println("\t @@@@ Score: " + score);
				sumOfScores += score;
				goalsToScores.put(goal, score);
				goalsToMPlus.put(goal, mPlus.getPlanLength());
			}
			float normalizingFactor = (1/sumOfScores);
			GroundFact mostLikelyGoal = filteredCandidateGoals.iterator().next();
			float highestProbability = (normalizingFactor*goalsToScores.get(mostLikelyGoal));
			Map<GroundFact, Float> goalsProbabilities = new HashMap<>();
			for(GroundFact goal: filteredCandidateGoals){
				float probabilityOfG = (normalizingFactor*goalsToScores.get(goal));
				System.out.println("\t - Probability of " + goal + ": " + probabilityOfG);
				goalsProbabilities.put(goal, probabilityOfG);
				if(probabilityOfG > highestProbability){
					mostLikelyGoal = goal;
					highestProbability = probabilityOfG;
				}
			}
			Set<GroundFact> recognizedGoals = new HashSet<>();
			for(GroundFact goal: goalsProbabilities.keySet())
				if(goalsProbabilities.get(goal) == highestProbability)
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
			} else if(recognizedGoals.size() > 1){
				GroundFact recognizedGoal = recognizedGoals.iterator().next();
				for(GroundFact goal: recognizedGoals){
					if(goalsToMPlus.get(goal) < goalsToMPlus.get(recognizedGoal))
						recognizedGoal = goal;
				}
				if(recognizedGoal.equals(this.realGoal)){
					topFirstFrequency++;
					convergenceToTopRankedGoal++;
				}
			} else convergenceToTopRankedGoal = 0;
			
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
		return new GoalRecognitionResult(this.getRecognitionFileName(), (TPR/observationCounter), (FPR/observationCounter), (FNR/observationCounter), topFirstRankedPercent, convergencePercent, totalTime, this.candidateGoals.size(), this.observations.size(), this.getAverageOfFactLandmarks(), numberOfCallsPlanner);
	}

	private Set<GroundFact> filterCandidateGoalsUsingLandmarks() {
		float highestValue = 0;
		Map<GroundFact, Float> goalsToPercentageOfAchievedLandmarks = new HashMap<>();
		for(GroundFact goal: this.candidateGoals){
			float numberOfLandmarksOfGoal = this.goalsToLandmarks.get(goal).getAmountOfLandmarks();
			float numberOfAchivedLandmarksOfGoal = this.goalsToObservedLandmarks.get(goal).size();
			float percentageAchievedLandmarksOfGoal = (numberOfAchivedLandmarksOfGoal/numberOfLandmarksOfGoal);
			if(percentageAchievedLandmarksOfGoal > highestValue)
				highestValue = percentageAchievedLandmarksOfGoal;
			
			goalsToPercentageOfAchievedLandmarks.put(goal, percentageAchievedLandmarksOfGoal);
		}
		Set<GroundFact> filteredGoals = new HashSet<>();
		for(GroundFact goal: this.candidateGoals)
			if(goalsToPercentageOfAchievedLandmarks.get(goal) >= (highestValue - this.threshold))	
				filteredGoals.add(goal);
		
		return filteredGoals;
	}
	
	@Override
	public GoalRecognitionResult call() throws Exception {
		return this.recognizeOnline();
	}
}