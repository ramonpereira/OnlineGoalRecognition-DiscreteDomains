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
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/depots/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/driverlog/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/dwr/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/easy-ipc-grid/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/ferry/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/intrusion-detection/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/kitchen/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/logistics/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/miconic/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/rovers/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.BASELINE, "experiments/satellite/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/sokoban/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/zeno_travel/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsLandmarksWithBaseLineApproach(){
		try {
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/blocks-world/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/campus/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/depots/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/driverlog/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/dwr/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/easy-ipc-grid/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/ferry/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/intrusion-detection/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/kitchen/");
			
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/logistics/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/miconic/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/rovers/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/satellite/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/sokoban/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/zeno_travel/");
			
			
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_BASELINE, "experiments/dwr/");
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
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/campus/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/depots/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/driverlog/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/dwr/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/easy-ipc-grid/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/ferry/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/intrusion-detection/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/kitchen/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/logistics/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/miconic/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/rovers/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/satellite/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/sokoban/");
			//OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/zeno_travel/");
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