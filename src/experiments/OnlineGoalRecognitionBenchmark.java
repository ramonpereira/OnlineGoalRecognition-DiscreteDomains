package experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javaff.search.UnreachableGoalException;
import recognize.NaiveOnlineGoalRecognition;
import recognize.OnlineGoalRecognition;
import recognize.OnlineGoalRecognitionMirroringBaseline;
import recognize.OnlineGoalRecognitionMirroringNoRecomputation;
import recognize.OnlineGoalRecognitionUsingHeuristic;
import bean.GoalRecognitionResult;

public class OnlineGoalRecognitionBenchmark {

	public static void runExperiments(GoalRecognitionApproach approach, String directoryPath) throws UnreachableGoalException, IOException{
		File folder = new File(directoryPath);
		List<GoalRecognitionResult> results = new ArrayList<>();
		long initialTime = System.currentTimeMillis();
		for (File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	if(fileEntry.getName().equalsIgnoreCase(".gitignore") || fileEntry.getName().equalsIgnoreCase(".DS_Store")) 
	        		continue;
	        	
	        	OnlineGoalRecognition onlineRecognizer = getInstantiatedApproach(approach, fileEntry.toString());
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
		writeExperimentFile(outputFileContent, folder.getAbsolutePath().toString() + "_" + approach);
	}
	
	private static OnlineGoalRecognition getInstantiatedApproach(GoalRecognitionApproach approach, String goalRecognitionProblem){
		OnlineGoalRecognition instantiatedApproach = null;
		if(approach == GoalRecognitionApproach.BASELINE){
	        instantiatedApproach = new OnlineGoalRecognitionMirroringBaseline(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.NAIVE){
			instantiatedApproach = new NaiveOnlineGoalRecognition(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.NO_RECOMPUTATION){
			instantiatedApproach = new OnlineGoalRecognitionMirroringNoRecomputation(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.HEURISTIC)
			instantiatedApproach = new OnlineGoalRecognitionUsingHeuristic(goalRecognitionProblem);
		
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