package experiments;

import java.io.File;
import java.io.IOException;

import recognizer.GoalRecognition;
import recognizer.OnlineGoalRecognitionMirroringWithLandmarks;
import bean.GoalRecognitionResult;

public class OfflineGoalRecognitionBenchmark {

	public static void main(String[] args) {
		String domain_name = args[0];
		GoalRecognitionApproach approach = GoalRecognitionApproach.MIRRORING_LANDMARKS;
		
		String contentFile = "Obs % \tAccuracy \tPrecision \tRecall \tF1-score \tFall-out \tMiss-rate \tAvg. Goals \tAvg. Observations \tRecognized Goals \tTime(s) \n";
		float totalProblems = 0;
		for(int obs=10; obs<=100; obs+=20){
			String observability = String.valueOf(obs);
			
			File folder = new File("experiments/" + domain_name + "/" + observability);
			long initialTime = System.currentTimeMillis();
			int returnedGoalsCounter = 0;
			float totalPlanRecognitionProblems = 0;
			float truePositiveCounter = 0;
			int candidateGoals = 0;
			int observations = 0;
			
			for (File fileEntry : folder.listFiles()) {
		        if (!fileEntry.isDirectory()) {
		        	if(fileEntry.getName().equalsIgnoreCase(".gitignore") || fileEntry.getName().equalsIgnoreCase(".DS_Store")) 
		        		continue;
		        	
		        	try {
		        		OnlineGoalRecognitionMirroringWithLandmarks recognizer = new OnlineGoalRecognitionMirroringWithLandmarks(fileEntry.getAbsolutePath(), 0f);
			        	totalPlanRecognitionProblems++;
			        	
			        	GoalRecognitionResult result = recognizer.recognizeOffline();
						
						returnedGoalsCounter = returnedGoalsCounter + result.getNumberOfRecognizedGoals();
						boolean goalWasRecognized = result.getGoalWasRecognized();
						
						candidateGoals = candidateGoals + recognizer.getCandidateGoalsSize();
						observations = observations + recognizer.getObservationsSize();
						
						if(goalWasRecognized )
							truePositiveCounter++;
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
		    }
			float averageObservations = (observations / totalPlanRecognitionProblems);
			float averageCandidateGoals = (candidateGoals / totalPlanRecognitionProblems);
			float falsePositiveCounter = (returnedGoalsCounter - truePositiveCounter);
			float falseNegativeCounter = (totalPlanRecognitionProblems - truePositiveCounter);
			float trueNegativeCounter = ((totalPlanRecognitionProblems*averageCandidateGoals) - falsePositiveCounter);
			float accuracy = truePositiveCounter / totalPlanRecognitionProblems;
			float precision = truePositiveCounter / (truePositiveCounter + falsePositiveCounter);
			float recall = truePositiveCounter / (truePositiveCounter + falseNegativeCounter);
			float f1score = 2 * ( (precision*recall) / (precision+recall));
			float fallout = falsePositiveCounter / (falsePositiveCounter + trueNegativeCounter);
			float missrate = falseNegativeCounter / (falseNegativeCounter + truePositiveCounter);
			float avgRecognizedGoals = (returnedGoalsCounter/totalPlanRecognitionProblems);
			long finalTime = System.currentTimeMillis();
			float totalTime = ((finalTime - initialTime)/1000);
			
			contentFile += obs + "\t" + (accuracy) + "\t" + (precision) + "\t" + (recall) + "\t" + (f1score) + "\t" + (fallout) + "\t" + (missrate) + "\t" + (averageCandidateGoals) + "\t" + (averageObservations) + "\t" + (avgRecognizedGoals) + "\t" + (totalTime / totalPlanRecognitionProblems) + "\n";
			totalProblems = totalProblems + totalPlanRecognitionProblems;
		}
		contentFile += "\n$> Total Problems: " + totalProblems;
		System.out.println("###################################################################################\n");
		System.out.println(contentFile);
		System.out.println("###################################################################################");
		try {
			GoalRecognition.writeExperimentFile(contentFile, domain_name + "-" + approach.toString().toLowerCase());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
