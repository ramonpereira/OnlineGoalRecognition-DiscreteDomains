package recognize;
import java.io.IOException;

import org.junit.Test;

import javaff.search.UnreachableGoalException;
import recognizer.OnlineGoalRecognitionMirroringBaseline;
import recognizer.OnlineGoalRecognitionMirroringNoRecomputation;
import recognizer.OnlineGoalRecognitionMirroringWithLandmarks;
import recognizer.OnlineGoalRecognitionNaive;
import recognizer.OnlineGoalRecognitionUsingHeuristic;
import recognizer.OnlineGoalRecognitionUsingLandmarksGoalCompletion;
import recognizer.OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic;

public class OnlineGoalRecognitionTest {

	public static String GOALRECOGNITION_PROBLEM = 
			"experiments/ferry/100/ferry_p02_hyp-1_full.tar.bz2";
	
	@Test
	public void testNaiveOnlineGoalRecognition(){
		OnlineGoalRecognitionNaive naiveGoalRecognition = new OnlineGoalRecognitionNaive(GOALRECOGNITION_PROBLEM);
		try {
			naiveGoalRecognition.recognizeOnline();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionBaseLine(){
		OnlineGoalRecognitionMirroringBaseline goalRecognitionBaseline = new OnlineGoalRecognitionMirroringBaseline(GOALRECOGNITION_PROBLEM);
		try {
			goalRecognitionBaseline.recognizeOnline();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionNoRecomputation(){
		OnlineGoalRecognitionMirroringNoRecomputation goalRecognitionNoRecomputation = new OnlineGoalRecognitionMirroringNoRecomputation(GOALRECOGNITION_PROBLEM);
		try {
			goalRecognitionNoRecomputation.recognizeOnline();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionUsingHeuristic(){
		OnlineGoalRecognitionUsingHeuristic goalRecognition = new OnlineGoalRecognitionUsingHeuristic(GOALRECOGNITION_PROBLEM);
		try {
			goalRecognition.recognizeOnline();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionUsingLandmarksGoalCompletion(){
		float threshold = 0.0f;
		OnlineGoalRecognitionUsingLandmarksGoalCompletion recognizer = new OnlineGoalRecognitionUsingLandmarksGoalCompletion(GOALRECOGNITION_PROBLEM, threshold);
		try {
			recognizer.recognizeOnline();
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionUsingLandmarksUniqueness(){
		float threshold = 0.0f;
		OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic recognizer = new OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic(GOALRECOGNITION_PROBLEM, threshold);
		try {
			recognizer.recognizeOnline();
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionMirroringWithLandmarks(){
		float threshold = 0;
		OnlineGoalRecognitionMirroringWithLandmarks recognizer = new OnlineGoalRecognitionMirroringWithLandmarks(GOALRECOGNITION_PROBLEM, threshold);
		try {
			recognizer.recognizeOnline();
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	public static String GOALRECOGNITION_PROBLEM_PATH = 
			"experiments-deception/blocks-words/p4/";
	
	public static String DECEPTIVE_APPROACH = 
//			"ops-vanilla.obs";
//			"ops-Combined-Landmarks-Approach.obs";
//			"ops-Most-Common-Landmarks.obs";
//			"ops-Shared-Landmark-Approach.obs";
			"ops-R-All-but-Real-Minimum-Coverinst-State-Approach.obs";
//			"ops-R-All-but-Real-Centroid-Approach.obs";
//			"ops-Closest-Minimum-Covering-State-Approach.obs";
	
	@Test
	public void testOnlineGoalRecognitionMirroringWithLandmarksSeparateFiles(){
		String domain = GOALRECOGNITION_PROBLEM_PATH + "domain.pddl";
		String problem= GOALRECOGNITION_PROBLEM_PATH + "template.pddl";
		String goalsFile = GOALRECOGNITION_PROBLEM_PATH + "hyps.dat";
		String realGoalFile = GOALRECOGNITION_PROBLEM_PATH + "real_hyp.dat";
		String observations = GOALRECOGNITION_PROBLEM_PATH + "observations/" + DECEPTIVE_APPROACH;
		
		OnlineGoalRecognitionMirroringWithLandmarks recognizer = new OnlineGoalRecognitionMirroringWithLandmarks(domain, problem, goalsFile, observations, realGoalFile);
		try {
			recognizer.recognizeOnline();
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}