package experiments;

import java.io.IOException;

import org.junit.Test;

import javaff.search.UnreachableGoalException;

public class DeceptionGoalRecognitionExperiments {

	String[] DECEPTIVE_APPROACHES = new String[]{
			"ops-vanilla.obs",
			"ops-All-but-Real-Centroid-Approach.obs",
			"ops-All-but-Real-Minimum-Coverinst-State-Approach.obs",
			"ops-Centroid-Approach.obs",
			"ops-Closest-Centroid-Approach.obs",
			"ops-Closest-Minimum-Covering-State-Approach.obs",
			"ops-Combined-Landmarks-Approach.obs",
			"ops-Farthest-Centroid-Approach.obs",
			"ops-Farthest-Minimum-Covering-State-Approach.obs",
			"ops-Goal-to-Real-Goal-Approach.obs",
			"ops-Minimum-Covering-State-Approach.obs",
			"ops-Most-Common-Landmarks.obs",
			"ops-R-All-but-Real-Centroid-Approach.obs",
			"ops-R-All-but-Real-Minimum-Coverinst-State-Approach.obs",
			"ops-R-Centroid-Approach.obs",
			"ops-R-Closest-Centroid-Approach.obs",
			"ops-R-Closest-Minimum-Covering-State-Approach.obs",
			"ops-R-Farthest-Centroid-Approach.obs",
			"ops-R-Farthest-Minimum-Covering-State-Approach.obs",
			"ops-R-Minimum-Covering-State-Approach.obs",
			"ops-Shared-Landmark-Approach.obs"
			};
	
	@Test
	public void runExperimentsBlocksWords(){
		String DECEPTION_GOALRECOGNITION_PROBLEM_PATH = 
				"experiments-deception/blocks-words/";
		
		for(String approach: DECEPTIVE_APPROACHES) {
			try {
				//OnlineGoalRecognitionBenchmark.runExperimentsDeception(GoalRecognitionApproach.MIRRORING_LANDMARKS, DECEPTION_GOALRECOGNITION_PROBLEM_PATH, 10, approach);
				OnlineGoalRecognitionBenchmark.runExperimentsDeception(GoalRecognitionApproach.MIRRORING_BASELINE, DECEPTION_GOALRECOGNITION_PROBLEM_PATH, 10, approach);
			} catch (UnreachableGoalException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void runExperimentsFerry(){
		String DECEPTION_GOALRECOGNITION_PROBLEM_PATH = 
				"experiments-deception/ferry/";
		
		for(String approach: DECEPTIVE_APPROACHES) {
			try {
				//OnlineGoalRecognitionBenchmark.runExperimentsDeception(GoalRecognitionApproach.MIRRORING_LANDMARKS, DECEPTION_GOALRECOGNITION_PROBLEM_PATH, 10, approach);
				OnlineGoalRecognitionBenchmark.runExperimentsDeception(GoalRecognitionApproach.MIRRORING_BASELINE, DECEPTION_GOALRECOGNITION_PROBLEM_PATH, 10, approach);
			} catch (UnreachableGoalException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void runExperimentsGridNavigation(){
		String DECEPTION_GOALRECOGNITION_PROBLEM_PATH = 
				"experiments-deception/grid-navigation/";
		
		for(String approach: DECEPTIVE_APPROACHES) {
			try {
				//OnlineGoalRecognitionBenchmark.runExperimentsDeception(GoalRecognitionApproach.MIRRORING_LANDMARKS, DECEPTION_GOALRECOGNITION_PROBLEM_PATH, 10, approach);
				OnlineGoalRecognitionBenchmark.runExperimentsDeception(GoalRecognitionApproach.MIRRORING_BASELINE, DECEPTION_GOALRECOGNITION_PROBLEM_PATH, 10, approach);
			} catch (UnreachableGoalException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void runExperimentsLogistics(){
		String DECEPTION_GOALRECOGNITION_PROBLEM_PATH = 
				"experiments-deception/logistics/";
		
		for(String approach: DECEPTIVE_APPROACHES) {
			try {
				//OnlineGoalRecognitionBenchmark.runExperimentsDeception(GoalRecognitionApproach.MIRRORING_LANDMARKS, DECEPTION_GOALRECOGNITION_PROBLEM_PATH, 10, approach);
				OnlineGoalRecognitionBenchmark.runExperimentsDeception(GoalRecognitionApproach.MIRRORING_BASELINE, DECEPTION_GOALRECOGNITION_PROBLEM_PATH, 10, approach);
			} catch (UnreachableGoalException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
}