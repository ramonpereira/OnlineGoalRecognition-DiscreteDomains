package recognize;

import java.io.IOException;

import org.junit.Test;

import javaff.search.UnreachableGoalException;
import recognizer.OnlineGoalRecognitionMirroringWithLandmarks;

public class OfflineGoalRecognitionTest {

	public static String GOALRECOGNITION_PROBLEM = 
			"dataset/ferry/50/ferry_p03_hyp-1_50_1.tar.bz2";
	
	@Test
	public void testOnlineGoalRecognitionMirroringWithLandmarks(){
		float threshold = 0;
		OnlineGoalRecognitionMirroringWithLandmarks recognizer = new OnlineGoalRecognitionMirroringWithLandmarks(GOALRECOGNITION_PROBLEM, threshold);
		try {
			recognizer.recognizeOffline();
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
