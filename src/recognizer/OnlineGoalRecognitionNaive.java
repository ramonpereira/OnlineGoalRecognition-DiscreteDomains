package recognizer;
import java.io.IOException;
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

public class OnlineGoalRecognitionNaive extends GoalRecognition {

	public OnlineGoalRecognitionNaive(String fileName){
		super(fileName);
	}
	
	@Override
	public GoalRecognitionResult recognizeOnline() throws UnreachableGoalException{
		Map<GroundFact, List<Action>> mObservationsGoals = new HashMap<>();
		STRIPSState currentState = this.initialSTRIPSState;
		System.out.println("#> Real Goal: " + this.realGoal);
		float numberOfCallsPlanner = 0;
		float observationCounter = 0;
		float topFirstFrequency = 0;
		float convergenceToTopRankedGoal = 0;
		for(Action o: this.observations){
			System.out.println("$> Observation (" + (int) observationCounter + ") :" + o);
			observationCounter++;
			currentState = (STRIPSState) currentState.apply(o);
			Map<GroundFact, Float> goalsToScores = new HashMap<>();
			float sumOfScores = 0f;
			for(GroundFact goal: this.candidateGoals){
				System.out.println("\n\t # Goal:" + goal);
				Plan idealPlan = doPlanJavaFF(initialState, goal);
				numberOfCallsPlanner++;
				System.out.println("\t # Ideal Plan of G: " + idealPlan.getPlanLength());
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
				float score = this.match(mG, idealPlan.getPlanLength());
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

			if(recognizedGoals.size() == 1 && recognizedGoals.contains(this.realGoal)){
				topFirstFrequency++;
				convergenceToTopRankedGoal++;
			} else convergenceToTopRankedGoal = 0;
		}
		float topFirstRankedPercent  = (topFirstFrequency/observationCounter);
		float convergencePercent = (convergenceToTopRankedGoal/observationCounter);
		System.out.println("\n$$$$####> Top First Ranked Percent (%): " + topFirstRankedPercent);
		System.out.println("$$$$####> Convergence Percent (%): " + convergencePercent);
		System.out.println("$$$$####> Top Ranked First times: " + topFirstFrequency);
		System.out.println("$$$$####> Total Candidate Goals: " + this.candidateGoals.size());
		System.out.println("$$$$####> Total Observed Actions: " + observationCounter);
		System.out.println("$$$$####> Total Number of Landmarks: " + this.getAverageOfFactLandmarks());
		System.out.println("$$$$####> Total Number of Calls to Planner: " + numberOfCallsPlanner);
		return new GoalRecognitionResult(topFirstRankedPercent, convergencePercent, this.candidateGoals.size(), this.observations.size(), this.getAverageOfFactLandmarks(), numberOfCallsPlanner);
	}

	@Override
	public GoalRecognitionResult call() throws Exception {
		return this.recognizeOnline();
	}

	@Override
	public GoalRecognitionResult recognizeOffline() throws UnreachableGoalException, IOException, InterruptedException {
		return null;
	}
}