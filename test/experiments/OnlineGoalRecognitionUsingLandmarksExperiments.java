package experiments;

import java.io.IOException;

import javaff.search.UnreachableGoalException;

import org.junit.Test;

import recognizer.OnlineGoalRecognitionUsingLandmarksGoalCompletion;
import recognizer.OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic;

public class OnlineGoalRecognitionUsingLandmarksExperiments {

	@Test
	public void testOnlineGoalRecognitionUsingLandmarksGoalCompletion(){
		float threshold = 0.0f;
		OnlineGoalRecognitionUsingLandmarksGoalCompletion recognizer = new OnlineGoalRecognitionUsingLandmarksGoalCompletion("experiments/blocks-world/block-words_p01_hyp-0_full.tar.bz2", threshold);
		try {
			recognizer.recognizeOnline();
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionUsingLandmarksUniqueness(){
		float threshold = 0.0f;
		OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic recognizer = new OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic("experiments/blocks-world/block-words_p01_hyp-0_full.tar.bz2", threshold);
		try {
			recognizer.recognizeOnline();
		} catch (UnreachableGoalException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}