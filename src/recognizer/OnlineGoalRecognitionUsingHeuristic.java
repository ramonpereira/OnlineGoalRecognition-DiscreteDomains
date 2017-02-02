package recognizer;
import heuristic.FFHeuristic;
import heuristic.Heuristic;

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

/**
 * Heuristic Approach.
 * For discrete domains, we use an inadmissible and domain-independent heuristic called Fast-Forward (FF).
 * @author ramonfragapereira
 *
 */
public class OnlineGoalRecognitionUsingHeuristic extends OnlineGoalRecognition {

	public OnlineGoalRecognitionUsingHeuristic(String fileName) {
		super(fileName);
	}
	
	@Override
	public GoalRecognitionResult recognizeOnline() throws UnreachableGoalException{
		Map<GroundFact, Plan> goalsIdealPlans= new HashMap<>();
		Map<GroundFact, List<Action>> mObservationsGoals = new HashMap<>();
		List<Action> observationsBuffer = new ArrayList<>();
		STRIPSState currentState = this.initialSTRIPSState;
		System.out.println("#> Real Goal: " + this.realGoal);
		int observationCounter = 0;
		float topFirstFrequency = 0;
		GroundFact topRankedGoal = null;
		Map<GroundFact, Float> goalsToScores = new HashMap<>();
		float convergenceToTopRankedGoal = 0;
		for(GroundFact goal: this.candidateGoals){
			System.out.println("\t # Goal:" + goal);
			Plan idealPlan = doPlanJavaFF(initialState, goal);
			goalsIdealPlans.put(goal, idealPlan);
		}
		Set<GroundFact> recognizedGoals = new HashSet<>();
		for(Action o: this.observations){
			System.out.println("$> Observation (" + (int) observationCounter + ") :" + o);
			observationsBuffer.add(o);
			currentState = (STRIPSState) currentState.apply(o);
			float sumOfScores = 0f;
			if(recompute(currentState, topRankedGoal, candidateGoals)){
				goalsToScores = new HashMap<>();
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
					System.out.println("\t # mMinus: " + mMinus.size());
					System.out.println("\t # mPlus: " + mPlus.getPlanLength());
					float mG = mMinus.size() + mPlus.getPlanLength();
					float score = this.match(mG, idealPlanOfG.getPlanLength());
					System.out.println("\t @@@@ Score: " + score);
					sumOfScores += score;
					goalsToScores.put(goal, score);
				}
				float normalizingFactor = (1/sumOfScores);
				GroundFact mostLikelyGoal = (topRankedGoal == null ? this.candidateGoals.get(0) : topRankedGoal);
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
				recognizedGoals = new HashSet<>();
				for(GroundFact goal: goalsProbabilities.keySet())
					if(goalsProbabilities.get(goal) == highestProbability)
						recognizedGoals.add(goal);
			} else System.out.println(" No recomputation of the probabilities is needed, the goal rankings remain the same.");
			observationCounter++;
			if(recognizedGoals.size() == 1 && recognizedGoals.contains(this.realGoal)){
				topFirstFrequency++;
				topRankedGoal = this.realGoal;
				convergenceToTopRankedGoal++;
			} else convergenceToTopRankedGoal = 0;
		}
		float topFirstRankedPercent  = (topFirstFrequency/observationCounter);
		float convergencePercent = (convergenceToTopRankedGoal/observationCounter);
		System.out.println("\n$$$$####> Top First Ranked Percent (%): " + topFirstRankedPercent);
		System.out.println("$$$$####> Convergence Percent (%): " + convergencePercent);
		System.out.println("$$$$####> Top Ranked First times: " + topFirstFrequency);
		System.out.println("$$$$####> Total Observed Actions: " + observationCounter);
		return new GoalRecognitionResult(topFirstRankedPercent, convergencePercent);
	}
	
	private boolean recompute(STRIPSState currentState, GroundFact topRankedGoal, List<GroundFact> candidateGoals) throws UnreachableGoalException{
		if(topRankedGoal == null)
			return true;
		
		/* Here, we can use any domain-independent heuristic. */
		/**
		 * TODO: Test recompute method using other planning heuristics, such as:
		 * Adjusted Sum family, Combo, Set Level, Sum Mutex, and others.
		 */
		Heuristic heuristic = new FFHeuristic(groundProblem, currentState.getFacts());
		int estimatedDistanceTopRankedGoal = heuristic.getEstimate(topRankedGoal);
		int minimumEstimatedDistance = Integer.MAX_VALUE;
		for(GroundFact goal: candidateGoals){
			if(goal.equals(topRankedGoal))
				continue;

			heuristic = new FFHeuristic(groundProblem, currentState.getFacts());
			int estimateOfG = heuristic.getEstimate(goal);
			if(estimateOfG < minimumEstimatedDistance)
				minimumEstimatedDistance = estimateOfG;
		}
		return !(estimatedDistanceTopRankedGoal <= minimumEstimatedDistance);
	}
}