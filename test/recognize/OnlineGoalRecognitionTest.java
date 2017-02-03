package recognize;
import java.io.IOException;

import javaff.search.UnreachableGoalException;

import org.junit.Test;

import recognizer.NaiveOnlineGoalRecognition;
import recognizer.OnlineGoalRecognitionMirroringBaseline;
import recognizer.OnlineGoalRecognitionMirroringNoRecomputation;
import recognizer.OnlineGoalRecognitionUsingHeuristic;
import recognizer.OnlineGoalRecognitionUsingLandmarksGoalCompletion;
import recognizer.OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic;
import recognizer.OnlineGoalRecognitionUsingLandmarksWithBaseline;

public class OnlineGoalRecognitionTest {

	public static String GOALRECOGNITION_PROBLEM = 
			"experiments/easy-ipc-grid/easy-ipc-grid_p5-10-10_hyp-7_full.tar.bz2";
	
	@Test
	public void testNaiveOnlineGoalRecognition(){
		NaiveOnlineGoalRecognition naiveGoalRecognition = new NaiveOnlineGoalRecognition(GOALRECOGNITION_PROBLEM);
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
	public void testOnlineGoalRecognitionUsingLandmarksWithBaseline(){
		float threshold = 0;
		OnlineGoalRecognitionUsingLandmarksWithBaseline recognizer = new OnlineGoalRecognitionUsingLandmarksWithBaseline(GOALRECOGNITION_PROBLEM, threshold);
		try {
			recognizer.recognizeOnline();
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}