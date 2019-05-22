package experiments;

import java.io.IOException;

import org.junit.Test;

import javaff.search.UnreachableGoalException;

public class OnlineGoalRecognitionExperiments {

	@Test
	public void runAllExperiments30Times(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments30times(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/campus/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsBaseLineApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/blocks-world/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/campus/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/depots/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/driverlog/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/dwr/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/easy-ipc-grid/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/ferry/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/intrusion-detection/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/kitchen/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/logistics/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/miconic/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/rovers/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_BASELINE, "experiments/satellite/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/sokoban/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/zeno_travel/100/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsLandmarksWithBaseLineApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/blocks-world/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/depots/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/driverlog/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/dwr/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/easy-ipc-grid/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/ferry/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/intrusion-detection/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/kitchen/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/logistics/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/miconic/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/rovers/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/satellite/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/sokoban/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/zeno_travel/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.MIRRORING_LANDMARKS, "experiments/dwr/100/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsNaiveApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/campus/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/easy-ipc-grid/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/intrusion-detection/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/logistics/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NAIVE, "experiments/kitchen/100/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsNoRecomputationApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/campus/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/easy-ipc-grid/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/intrusion-detection/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/logistics/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.NO_RECOMPUTATION, "experiments/kitchen/100/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsHeuristicApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/campus/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/easy-ipc-grid/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/intrusion-detection/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/logistics/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.HEURISTIC, "experiments/kitchen/100/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsLandmarksGoalCompletionApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/blocks-world/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/campus/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/depots/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/driverlog/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/dwr/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/easy-ipc-grid/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/ferry/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/intrusion-detection/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/kitchen/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/logistics/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/miconic/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/rovers/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/satellite/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/sokoban/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC, "experiments/zeno_travel/100/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void runAllExperimentsLandmarksUniquenessHeuristicApproach(){
		try {
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/blocks-world/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/campus/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/easy-ipc-grid/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/intrusion-detection/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/logistics/100/");
			OnlineGoalRecognitionBenchmark.runExperiments(GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC, "experiments/kitchen/100/");
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}