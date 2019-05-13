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
			"experiments/hanoi/pb03_hanoi_out_100.tar.bz2";
	
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
}