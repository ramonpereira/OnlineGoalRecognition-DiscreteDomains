package experiments;

import java.io.IOException;

import javaff.search.UnreachableGoalException;

import org.junit.Test;

public class OnlineGoalRecognitionExperiments {

	@Test
	public void runAllExperimentsBaseLineApproach(){
		try {
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/blocks-world/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsLandmarksWithBaseLineApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/campus/", 0f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/campus/", 0.1f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/campus/", 0.2f);
			
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/easy-ipc-grid/", 0f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/easy-ipc-grid/", 0.1f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/easy-ipc-grid/", 0.2f);
			
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/intrusion-detection/", 0f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/intrusion-detection/", 0.1f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/intrusion-detection/", 0.2f);
			
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/logistics/", 0f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/logistics/", 0.1f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/logistics/", 0.2f);
			
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/kitchen/", 0f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/kitchen/", 0.1f);
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/kitchen/", 0.2f);
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsNaiveApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsNoRecomputationApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsHeuristicApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsLandmarksGoalCompletionApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/blocks-world/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsLandmarksUniquenessHeuristicApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/blocks-world/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/logistics/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/kitchen/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}