package experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javaff.search.UnreachableGoalException;
import recognizer.NaiveOnlineGoalRecognition;
import recognizer.OnlineGoalRecognition;
import recognizer.OnlineGoalRecognitionMirroringBaseline;
import recognizer.OnlineGoalRecognitionMirroringNoRecomputation;
import recognizer.OnlineGoalRecognitionUsingHeuristic;
import recognizer.OnlineGoalRecognitionUsingLandmarksGoalCompletion;
import recognizer.OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic;
import recognizer.OnlineGoalRecognitionUsingLandmarksWithBaseline;
import bean.GoalRecognitionResult;

public class OnlineGoalRecognitionBenchmark {

	public static void runExperiments(GoalRecognitionApproach approach, String directoryPath) throws UnreachableGoalException, IOException, InterruptedException{
		runExperiments(approach, directoryPath, null);
	}
	
	public static void runExperiments(GoalRecognitionApproach approach, String directoryPath, Float threshold) throws UnreachableGoalException, IOException, InterruptedException{
		File folder = new File(directoryPath);
		List<GoalRecognitionResult> results = new ArrayList<>();
		long initialTime = System.currentTimeMillis();
		for (File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	if(fileEntry.getName().equalsIgnoreCase(".gitignore") || fileEntry.getName().equalsIgnoreCase(".DS_Store")) 
	        		continue;
	        	
	        	OnlineGoalRecognition onlineRecognizer = getInstantiatedApproach(approach, fileEntry.toString(), (threshold == null ? 0 : threshold));
	        	results.add(onlineRecognizer.recognizeOnline());
	        }
	    }
		String outputFileContent = "";
		float totalTopFirstPercentage = 0;
		float totalConvergencePercentage = 0;
		for(GoalRecognitionResult goalRecognitionResult: results){
			totalTopFirstPercentage += goalRecognitionResult.getRankedFirstPercent();
			totalConvergencePercentage += goalRecognitionResult.getConvergenceFirstPercent();
		}
		long finalTime = System.currentTimeMillis();
		float totalTime = ((finalTime - initialTime)/1000);
		float totalProblems = results.size();
		outputFileContent += "# Average Ranked First Percent (%): " + (totalTopFirstPercentage/totalProblems);
		outputFileContent += "\n# Average Convergence Percent (%): " + (totalConvergencePercentage/totalProblems);
		outputFileContent += "\n# Average Run-Time (sec): " + (totalTime / totalProblems);
		outputFileContent += "\n\n# Total Problems: " + totalProblems;
		writeExperimentFile(outputFileContent, folder.getAbsolutePath().toString() + "_" + approach + (threshold != null ? "_" + threshold : ""));
	}
	
	private static OnlineGoalRecognition getInstantiatedApproach(GoalRecognitionApproach approach, String goalRecognitionProblem, float threshold){
		OnlineGoalRecognition instantiatedApproach = null;
		if(approach == GoalRecognitionApproach.BASELINE){
	        instantiatedApproach = new OnlineGoalRecognitionMirroringBaseline(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.NAIVE){
			instantiatedApproach = new NaiveOnlineGoalRecognition(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.NO_RECOMPUTATION){
			instantiatedApproach = new OnlineGoalRecognitionMirroringNoRecomputation(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.HEURISTIC){
			instantiatedApproach = new OnlineGoalRecognitionUsingHeuristic(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.LANDMARKS_BASELINE){
				instantiatedApproach = new OnlineGoalRecognitionUsingLandmarksWithBaseline(goalRecognitionProblem, threshold);
		} else if(approach == GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC){
			instantiatedApproach = new OnlineGoalRecognitionUsingLandmarksGoalCompletion(goalRecognitionProblem, threshold);
		} else if(approach == GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC){
			instantiatedApproach = new OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic(goalRecognitionProblem, threshold);
		}
		
		return instantiatedApproach;
	}
	
	private static void writeExperimentFile(String content, String outputFile) throws IOException{
		File file = new File(outputFile + ".txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
		System.out.println("\nWriting file " + file.getAbsolutePath());
	}
}