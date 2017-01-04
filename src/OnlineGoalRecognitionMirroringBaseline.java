import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javaff.data.Action;
import javaff.data.GroundFact;
import javaff.data.Plan;
import javaff.planning.STRIPSState;
import javaff.search.UnreachableGoalException;

public class OnlineGoalRecognitionMirroringBaseline extends OnlineGoalRecognition {

	public OnlineGoalRecognitionMirroringBaseline(String fileName){
		super(fileName);
	}
	
	public void onlineRecognize() throws UnreachableGoalException{
		Map<GroundFact, List<Action>> mObservationsGoals = new HashMap<>();
		Map<GroundFact, Plan> goalsIdealPlans= new HashMap<>();
		STRIPSState currentState = this.initialSTRIPSState;
		System.out.println("#> Real Goal: " + this.realGoal);
		int observationCounter = 0;
		float topFirstFrequency = 0;
		for(GroundFact goal: this.candidateGoals){
			System.out.println("\t # Goal:" + goal);
			Plan idealPlan = doPlanJavaFF(initialState, goal);
			goalsIdealPlans.put(goal, idealPlan);
		}
		for(Action o: this.observations){
			System.out.println("$> Observation (" + observationCounter + ") :" + o);
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
			for(GroundFact goal: this.candidateGoals){
				float probabilityOfG = (normalizingFactor*goalsToScores.get(goal));
				System.out.println("\t - Probability of " + goal + ": " + probabilityOfG);
				if(probabilityOfG > highestProbability){
					mostLikelyGoal = goal;
					highestProbability = probabilityOfG;
				}
			}
			if(this.realGoal.equals(mostLikelyGoal))
				topFirstFrequency++;
		}
		float totalFrequency = (topFirstFrequency/observationCounter);
		System.out.println("\n$$$$####> Frequecy: " + totalFrequency);
		System.out.println("$$$$####> Top First times: " + topFirstFrequency);
		System.out.println("$$$$####> Total observed actions: " + observationCounter);
	}
}