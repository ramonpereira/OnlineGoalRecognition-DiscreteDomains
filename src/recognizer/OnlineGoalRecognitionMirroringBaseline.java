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

/**
 * Baseline Approach (no-recomputation of ideal plans).
 * @author ramonfragapereira
 *
 */
public class OnlineGoalRecognitionMirroringBaseline extends GoalRecognition {

	public OnlineGoalRecognitionMirroringBaseline(String fileName){
		super(fileName);
	}
	
	public OnlineGoalRecognitionMirroringBaseline(String domainFile, String problemFile, String candidateGoalsFile, String observationsFile, String realGoalFile){
		super(domainFile, problemFile, candidateGoalsFile, observationsFile, realGoalFile);
	}
	
	@Override
	public GoalRecognitionResult recognizeOnline() throws UnreachableGoalException{
		Map<GroundFact, List<Action>> mObservationsGoals = new HashMap<>();
		Map<GroundFact, Plan> goalsIdealPlans= new HashMap<>();
		STRIPSState currentState = this.initialSTRIPSState;
		System.out.println("#> Real Goal: " + this.realGoal);
		float returnedGoals = 0;
		float numberOfCallsPlanner = 0;
		int observationCounter = 0;
		float topFirstFrequency = 0;
		float convergenceToTopRankedGoal = 0;
		
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
			System.out.println("$> Observation (" + (int) observationCounter + "): " + o);
			observationCounter++;
			currentState = (STRIPSState) currentState.apply(o);
			Map<GroundFact, Float> goalsToScores = new HashMap<>();
			float sumOfScores = 0f;
			for(GroundFact goal: this.candidateGoals){
				System.out.println("\n\t # Goal:" + goal);
				Plan idealPlanOfG = goalsIdealPlans.get(goal);
				System.out.println("\t # Ideal Plan of G: " + idealPlanOfG.getPlanLength());
				List<Action> mMinus = mObservationsGoals.get(goal);
				if(mMinus == null){
					List<Action> mMinusNew = new ArrayList<Action>();
					mMinus = mMinusNew;
					mMinusNew.add(o);
					mObservationsGoals.put(goal, mMinusNew);
				} else mMinus.add(o);
				Plan mPlus = doPlanJavaFF(currentState.getFacts(), goal);
				numberOfCallsPlanner++;
				System.out.println("\t # mMinus: " + mMinus.size());
				System.out.println("\t # mPlus: " + mPlus.getPlanLength());
				float mG = mMinus.size() + mPlus.getPlanLength();
				float score = this.match(mG, idealPlanOfG.getPlanLength());
				System.out.println("\t @@@@ Score: " + score);
				sumOfScores += score;
				goalsToScores.put(goal, score);
			}
			float normalizingFactor = (1/sumOfScores);
			GroundFact mostLikelyGoal = this.candidateGoals.get(0);
			float highestProbability = (normalizingFactor*goalsToScores.get(mostLikelyGoal));
			Map<GroundFact, Float> goalsProbabilities = new HashMap<>();
			for(GroundFact goal: this.candidateGoals){
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
			
			if(this.observationsIndexObservabilityLevelToObsLevel.keySet().contains((int) observationCounter-1)) {
				Integer obsLevel = this.observationsIndexObservabilityLevelToObsLevel.get((int) observationCounter-1);
				this.obsLevelToRecognizedCorrectly.put(obsLevel, recognizedGoals.contains(this.realGoal));
			}
			
			if(recognizedGoals.contains(this.realGoal))
				truePositiveCounter++;
			
			if(recognizedGoals.size() == 1 && recognizedGoals.contains(this.realGoal)){
				topFirstFrequency++;
				convergenceToTopRankedGoal++;
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
			
			System.out.println("\n-> TPR: " + tpr);
			System.out.println("-> FPR: " + fpr);
			System.out.println("-> FNR: " + fnr);
			System.out.println();
		}
		long finalTime = System.currentTimeMillis();
		BigDecimal finalTimeBigDecimal = BigDecimal.valueOf(finalTime);
		BigDecimal initialTimeBigDecimal = BigDecimal.valueOf(initialTime);
		BigDecimal resultingTime = finalTimeBigDecimal.subtract(initialTimeBigDecimal);
		
//		BigDecimal totalTime = resultingTime.divide(BigDecimal.valueOf(1000));
		
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
		
		return new GoalRecognitionResult((TPR/observationCounter), (FPR/observationCounter), (FNR/observationCounter), topFirstRankedPercent, convergencePercent, this.candidateGoals.size(), this.observations.size(), this.getAverageOfFactLandmarks(), numberOfCallsPlanner, obsLevelToRecognizedCorrectly);
	}
	
	@Override
	public GoalRecognitionResult recognizeOffline() throws UnreachableGoalException, IOException, InterruptedException {
		return null;
	}	
	
	@Override
	public GoalRecognitionResult call() throws Exception {
		return this.recognizeOnline();
	}
}