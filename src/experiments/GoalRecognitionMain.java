package experiments;

import java.io.IOException;

import javaff.search.UnreachableGoalException;
import recognizer.OnlineGoalRecognitionMirroringWithLandmarks;

public class GoalRecognitionMain {

	public static void main(String[] args) {
		try {
			if(args.length == 3){
				String goalRecognitionFile = args[1];
				float threshold = Float.valueOf(args[2]);
				if(args[0].equals("-offline")){
					OnlineGoalRecognitionMirroringWithLandmarks recognizer = new OnlineGoalRecognitionMirroringWithLandmarks(goalRecognitionFile, threshold);
					recognizer.recognizeOffline();
				} else if(args[0].equals("-online")){
					OnlineGoalRecognitionMirroringWithLandmarks recognizer = new OnlineGoalRecognitionMirroringWithLandmarks(goalRecognitionFile, threshold);
					recognizer.recognizeOnline();
				} else {
					printUsage();
				}
			} else {
				printUsage();
			}
		} catch (IOException | InterruptedException | UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	private static void printUsage(){
		System.out.println("- Option - Parameters needed: < -online | -offline > < tar.bz2 file > < threshold_value >");
		System.out.println("\t $> Example: -online experiments/logistics/100/logistics_p01_hyp-1_full.tar.bz2 0.0");
	}

}
