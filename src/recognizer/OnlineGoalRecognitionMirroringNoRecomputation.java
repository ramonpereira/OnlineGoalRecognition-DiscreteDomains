package recognizer;
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
 * No-recomputation Approach (no-recomputation of ideal plans and mG).
 * @author ramonfragapereira
 *
 */
public class OnlineGoalRecognitionMirroringNoRecomputation extends OnlineGoalRecognition {

	public OnlineGoalRecognitionMirroringNoRecomputation(String fileName) {
		super(fileName);
	}
	
	@Override
	public GoalRecognitionResult recognizeOnline() throws UnreachableGoalException{
		Map<GroundFact, List<Action>> mObservationsGoals = new HashMap<>();
		Map<GroundFact, Plan> goalsIdealPlans= new HashMap<>();
		Map<GroundFact, Plan> goalsMPlusPlans= new HashMap<>();
		STRIPSState currentState = this.initialSTRIPSState;
		System.out.println("#> Real Goal: " + this.realGoal);
		float returnedGoals = 0;
		float numberOfCallsPlanner = 0;
		int observationCounter = 0;
		float topFirstFrequency = 0;
		float convergenceToTopRankedGoal = 0;
		for(GroundFact goal: this.candidateGoals){
			System.out.println("\t # Goal:" + goal);
			Plan idealPlan = doPlanJavaFF(initialState, goal);
			numberOfCallsPlanner++;
			goalsIdealPlans.put(goal, idealPlan);
		}
		for(Action o: this.observations){
			System.out.println("$> Observation (" + (int) observationCounter + ") :" + o);
			currentState = (STRIPSState) currentState.apply(o);
			Map<GroundFact, Float> goalsToScores = new HashMap<>();
			float sumOfScores = 0f;
			for(GroundFact goal: this.candidateGoals){
				System.out.println("\n\t # Goal:" + goal);
				Plan idealPlanOfG = goalsIdealPlans.get(goal);
				System.out.println("\t # Ideal Plan of G: " + idealPlanOfG.getPlanLength());
				float mG = 0;
				if(observationCounter == 0){
					Plan mPlus = doPlanJavaFF(currentState.getFacts(), goal);
					numberOfCallsPlanner++;
					goalsMPlusPlans.put(goal, mPlus);
					mG = mPlus.getPlanLength()+1;
				} else {
					List<Action> mMinus = mObservationsGoals.get(goal);
					if(mMinus == null){
						List<Action> mMinusNew = new ArrayList<Action>();
						mMinus = mMinusNew;
						mMinusNew.add(o);
						mObservationsGoals.put(goal, mMinusNew);
					} else mMinus.add(o);
					Plan mGPlus = goalsMPlusPlans.get(goal);
					if(containsActions(mGPlus, mMinus))
						mG = mGPlus.getPlanLength()+1;
				}
				float score = 0;
				if(mG > 0)
					score = this.match(mG, idealPlanOfG.getPlanLength());
				System.out.println("\t @@@@ Score: " + score);
				sumOfScores += score;
				goalsToScores.put(goal, score);
			}
			observationCounter++;
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
			
			returnedGoals += recognizedGoals.size(); 
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
		System.out.println("$$$$####> Average Number of Returned Goals: " + (returnedGoals / observationCounter) + " (out of " + this.candidateGoals.size() + ")");
		System.out.println("$$$$####> Total Observed Actions: " + observationCounter);
		return new GoalRecognitionResult(topFirstRankedPercent, convergencePercent, this.candidateGoals.size(), this.observations.size(), this.getAverageOfFactLandmarks(), numberOfCallsPlanner);
	}
	
	private boolean containsActions(Plan mGPlus, List<Action> observations){
		for(int i=0;i<observations.size();i++)
			if(!observations.get(i).toString().equalsIgnoreCase(mGPlus.getActions().get(i).toString()))
				return false;
		return true;
	}
	
	@Override
	public GoalRecognitionResult call() throws Exception {
		return this.recognizeOnline();
	}
}