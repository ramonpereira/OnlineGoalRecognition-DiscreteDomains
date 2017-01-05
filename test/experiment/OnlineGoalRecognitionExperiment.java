package experiment;

import java.io.IOException;

import javaff.search.UnreachableGoalException;

import org.junit.Test;

import experiments.GoalRecognitionApproach;
import experiments.OnlineGoalRecognitionBenchmark;

public class OnlineGoalRecognitionExperiment {

	@Test
	public void runExperimentsBaseLineApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runExperimentsNaiveApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runExperimentsNoRecomputationApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runExperimentsHeuristicApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException e) {
			e.printStackTrace();
		}
	}
}