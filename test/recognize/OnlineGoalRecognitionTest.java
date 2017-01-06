package recognize;
import javaff.search.UnreachableGoalException;

import org.junit.Test;

import recognizer.NaiveOnlineGoalRecognition;
import recognizer.OnlineGoalRecognitionMirroringBaseline;
import recognizer.OnlineGoalRecognitionMirroringNoRecomputation;
import recognizer.OnlineGoalRecognitionUsingHeuristic;

public class OnlineGoalRecognitionTest {

	public static String GOALRECOGNITION_PROBLEM = "experiments/easy-ipc-grid/easy-ipc-grid_p10-10-10_hyp-7_full.tar.bz2";
	
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
}