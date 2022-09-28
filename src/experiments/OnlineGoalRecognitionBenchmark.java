package experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javaff.search.UnreachableGoalException;
import recognizer.GoalRecognition;
import recognizer.GoalRecognitionResult;
import recognizer.OnlineGoalRecognitionMirroringBaseline;
import recognizer.OnlineGoalRecognitionMirroringNoRecomputation;
import recognizer.OnlineGoalRecognitionMirroringWithLandmarks;
import recognizer.OnlineGoalRecognitionNaive;
import recognizer.OnlineGoalRecognitionUsingHeuristic;
import recognizer.OnlineGoalRecognitionUsingLandmarksGoalCompletion;
import recognizer.OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic;

public class OnlineGoalRecognitionBenchmark {

	public static void runExperiments(GoalRecognitionApproach approach, String directoryPath) throws UnreachableGoalException, IOException, InterruptedException{
		runExperiments(approach, directoryPath, null);
	}
	
	public static void runExperiments30times(GoalRecognitionApproach approach, String directoryPath) throws UnreachableGoalException, IOException, InterruptedException{
		runExperiments30times(approach, directoryPath, null);
	}
	
	public static void runExperiments30times(final GoalRecognitionApproach approach, String directoryPath, final Float threshold) throws UnreachableGoalException, IOException, InterruptedException{
		File folder = new File(directoryPath);
		List<List<GoalRecognitionResult>> allResults = new ArrayList<>();
		@SuppressWarnings("unused")
		int numberOfTimeoutProblems = 0;
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	if(!fileEntry.getName().endsWith(".tar.bz2")) 
	        		continue;

	        	FutureTask<List<GoalRecognitionResult>> timeoutTask = new FutureTask<List<GoalRecognitionResult>>(new Callable<List<GoalRecognitionResult>>() {
	                @Override
	                public List<GoalRecognitionResult> call() throws Exception {
	                	List<GoalRecognitionResult> results = new ArrayList<>();
	                	for(int i=1; i<=30; i++){
		        			System.out.println(fileEntry.toString());
		        			System.out.println("# RUN: " + i);
	                		GoalRecognition onlineRecognizer = getInstantiatedApproach(approach, fileEntry.toString(), (threshold == null ? 0 : threshold));
	                		results.add(onlineRecognizer.recognizeOnline());
	                	}
	                    return results;
	                }
	            });
	        	new Thread(timeoutTask).start();
	        	try {
					List<GoalRecognitionResult> results = timeoutTask.get(120, TimeUnit.SECONDS);
					allResults.add(results);
				} catch (ExecutionException | TimeoutException e) {
					numberOfTimeoutProblems++;
					e.printStackTrace();
				}
	        }
	    }
		
		for(List<GoalRecognitionResult> goalRecognitionResults: allResults){
			String problemFileName = goalRecognitionResults.get(0).getProblemFilename();
			problemFileName = problemFileName.replace(".tar.bz2", "");
			
			String outputFileContent = "";
			for(GoalRecognitionResult result: goalRecognitionResults){
				outputFileContent += result.getTotalTime() + "\t" + result.getNumberOfCallsPlanner() 
									+ ";" + result.getTruePositiveRatio() + ";" + result.getFalsePositiveRatio() 
									+ ";" + result.getRankedFirstPercent() + ";" + result.getConvergenceFirstPercent() + "\n";
				
			}
			writeExperimentFile(outputFileContent, problemFileName + "_" + approach + (threshold != null ? "_" + threshold : ""));
		}
	}
	
	public static void runExperiments(final GoalRecognitionApproach approach, String directoryPath, final Float threshold) throws UnreachableGoalException, IOException, InterruptedException{
		File folder = new File(directoryPath);
		List<GoalRecognitionResult> results = new ArrayList<>();
		long initialTime = System.currentTimeMillis();
		int numberOfTimeoutProblems = 0;
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	if(!fileEntry.getName().endsWith(".tar.bz2")) 
	        		continue;

	        	FutureTask<GoalRecognitionResult> timeoutTask = new FutureTask<GoalRecognitionResult>(new Callable<GoalRecognitionResult>() {
	                @Override
	                public GoalRecognitionResult call() throws Exception {
	                	GoalRecognition onlineRecognizer = getInstantiatedApproach(approach, fileEntry.toString(), (threshold == null ? 0 : threshold));
	                    return onlineRecognizer.recognizeOnline();
	                }
	            });
	        	new Thread(timeoutTask).start();
	        	try {
					GoalRecognitionResult result = timeoutTask.get(120, TimeUnit.SECONDS);
					results.add(result);
				} catch (ExecutionException | TimeoutException e) {
					numberOfTimeoutProblems++;
					e.printStackTrace();
				}
	        }
	    }
		String outputFileContent = "";
		float totalTopFirstPercentage = 0;
		float totalConvergencePercentage = 0;
		float totalCandidateGoals = 0;
		float totalObservations = 0;
		float totalLandmarks = 0;
		float totalNumberOfCallsPlanner = 0;
		
		float totalTPR = 0;
		float totalFPR = 0;
		float totalFNR = 0;
		for(GoalRecognitionResult goalRecognitionResult: results){
			totalTopFirstPercentage += goalRecognitionResult.getRankedFirstPercent();
			totalConvergencePercentage += goalRecognitionResult.getConvergenceFirstPercent();
			totalCandidateGoals += goalRecognitionResult.getNumberOfCandidateGoals();
			totalObservations += goalRecognitionResult.getNumberOfObservations();
			totalLandmarks += goalRecognitionResult.getNumberOfLandmarks();
			totalNumberOfCallsPlanner += goalRecognitionResult.getNumberOfCallsPlanner();
			totalTPR += goalRecognitionResult.getTruePositiveRatio();
			totalFPR += goalRecognitionResult.getFalsePositiveRatio();
			totalFNR += goalRecognitionResult.getFalseNegativeRatio();
		}
		long finalTime = System.currentTimeMillis();
		float totalTime = ((finalTime - initialTime)/1000);
		float totalProblems = results.size();
		outputFileContent += "# Average Ranked First Percent (%): " + (totalTopFirstPercentage/totalProblems);
		outputFileContent += "\n# Average Convergence Percent (%): " + (totalConvergencePercentage/totalProblems);
		outputFileContent += "\n# Average Run-Time (sec): " + (totalTime / totalProblems);
		outputFileContent += "\n# Average Candidate Goals: " + (totalCandidateGoals / totalProblems);
		outputFileContent += "\n# Average Number of Observed Actions: " + (totalObservations / totalProblems);
		outputFileContent += "\n# Average Number of Landmarks: " + (totalLandmarks / totalProblems);
		outputFileContent += "\n# Average Number of Calls to Planner: " + (totalNumberOfCallsPlanner / totalProblems);
		outputFileContent += "\n# True Positive Ratio (TPR): " + (totalTPR / totalProblems);
		outputFileContent += "\n# False Positive Ratio (FPR): " + (totalFPR / totalProblems);
		outputFileContent += "\n# False Negative Ratio (FNR): " + (totalFNR / totalProblems);
		outputFileContent += "\n# Total Problems with Timeout: " + numberOfTimeoutProblems;
		outputFileContent += "\n\n# Total Problems: " + totalProblems;
		writeExperimentFile(outputFileContent, folder.getAbsolutePath().toString() + "_" + approach + (threshold != null ? "_" + threshold : ""));
	}
	
	public static void runExperimentsDeception(final GoalRecognitionApproach approach, String directoryPath, Integer numberOfProblems, String deceptiveApproachObservations) throws UnreachableGoalException, IOException, InterruptedException{
		List<GoalRecognitionResult> results = new ArrayList<>();
		long initialTime = System.currentTimeMillis();
		int numberOfTimeoutProblems = 0;

		for(int i=1; i<=numberOfProblems;i++) {
			final int index = i;
			System.out.println("\n@@@@@@@@> Starting recognition process for: \n\n" + directoryPath + "p" + index + "/");
        	FutureTask<GoalRecognitionResult> timeoutTask = new FutureTask<GoalRecognitionResult>(new Callable<GoalRecognitionResult>() {
                @Override
                public GoalRecognitionResult call() throws Exception {
                	GoalRecognition onlineRecognizer = getInstantiatedApproach(approach, directoryPath + "p" + index + "/", deceptiveApproachObservations);
                    return onlineRecognizer.recognizeOnline();
                }
            });
        	new Thread(timeoutTask).start();
        	try {
				GoalRecognitionResult result = timeoutTask.get(120, TimeUnit.SECONDS);
				results.add(result);
			} catch (ExecutionException | TimeoutException e) {
				numberOfTimeoutProblems++;
				e.printStackTrace();
			}
		}
		String outputFileContent = "";
		float totalTopFirstPercentage = 0;
		float totalConvergencePercentage = 0;
		float totalCandidateGoals = 0;
		float totalObservations = 0;
		float totalLandmarks = 0;
		float totalNumberOfCallsPlanner = 0;
		
		float totalTPR = 0;
		float totalFPR = 0;
		float totalFNR = 0;
		
		float acc10pctCounter = 0;
		float acc30pctCounter = 0;
		float acc50pctCounter = 0;
		float acc70pctCounter = 0;
		float acc100pctCounter = 0;
		
		for(GoalRecognitionResult goalRecognitionResult: results){
			totalTopFirstPercentage += goalRecognitionResult.getRankedFirstPercent();
			totalConvergencePercentage += goalRecognitionResult.getConvergenceFirstPercent();
			totalCandidateGoals += goalRecognitionResult.getNumberOfCandidateGoals();
			totalObservations += goalRecognitionResult.getNumberOfObservations();
			totalLandmarks += goalRecognitionResult.getNumberOfLandmarks();
			totalNumberOfCallsPlanner += goalRecognitionResult.getNumberOfCallsPlanner();
			totalTPR += goalRecognitionResult.getTruePositiveRatio();
			totalFPR += goalRecognitionResult.getFalsePositiveRatio();
			totalFNR += goalRecognitionResult.getFalseNegativeRatio();
			
			if(goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(30) == null)
				System.out.println();
			
			acc10pctCounter += (goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(10) != null && goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(10) ? 1 : 0);
			acc30pctCounter += (goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(30) != null && goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(30) ? 1 : 0);
			acc50pctCounter += (goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(50) != null && goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(50) ? 1 : 0);
			acc70pctCounter += (goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(70) != null && goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(70) ? 1 : 0);
			acc100pctCounter += (goalRecognitionResult.getObsLevelToRecognizedCorrectly().get(100) ? 1 : 0);
		}
		long finalTime = System.currentTimeMillis();
		float totalTime = ((finalTime - initialTime)/1000);
		float totalProblems = results.size();
		outputFileContent += "# Average Ranked First Percent (%): " + (totalTopFirstPercentage/totalProblems);
		outputFileContent += "\n# Average Convergence Percent (%): " + (totalConvergencePercentage/totalProblems);
		outputFileContent += "\n# Average Run-Time (sec): " + (totalTime / totalProblems);
		outputFileContent += "\n\n# Average Candidate Goals: " + (totalCandidateGoals / totalProblems);
		outputFileContent += "\n# Average Number of Observed Actions: " + (totalObservations / totalProblems);
		outputFileContent += "\n# Average Number of Landmarks: " + (totalLandmarks / totalProblems);
		outputFileContent += "\n# Average Number of Calls to Planner: " + (totalNumberOfCallsPlanner / totalProblems);
		outputFileContent += "\n\n# True Positive Ratio (TPR): " + (totalTPR / totalProblems);
		outputFileContent += "\n# False Positive Ratio (FPR): " + (totalFPR / totalProblems);
		outputFileContent += "\n# False Negative Ratio (FNR): " + (totalFNR / totalProblems);
		outputFileContent += "\n# Total Problems with Timeout: " + numberOfTimeoutProblems;
		
		outputFileContent += "\n\n# Accuracy 10% : " + (acc10pctCounter / totalProblems);
		outputFileContent += "\n# Accuracy 30% : " + (acc30pctCounter / totalProblems);
		outputFileContent += "\n# Accuracy 50% : " + (acc50pctCounter / totalProblems);
		outputFileContent += "\n# Accuracy 70% : " + (acc70pctCounter / totalProblems);
		outputFileContent += "\n# Accuracy 100%: " + (acc100pctCounter / totalProblems);
		
		outputFileContent += "\n\n# Total Problems: " + totalProblems;
		
		String deceptiveApproachName = deceptiveApproachObservations.replace("ops", "");
		deceptiveApproachName = deceptiveApproachName.replace(".obs", "");
		
		writeExperimentFile(outputFileContent, directoryPath + "" + approach + "" + deceptiveApproachName);
	}
	
	private static GoalRecognition getInstantiatedApproach(GoalRecognitionApproach approach, String goalRecognitionProblem, float threshold){
		GoalRecognition instantiatedApproach = null;
		if(approach == GoalRecognitionApproach.MIRRORING_BASELINE){
	        instantiatedApproach = new OnlineGoalRecognitionMirroringBaseline(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.NAIVE){
			instantiatedApproach = new OnlineGoalRecognitionNaive(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.NO_RECOMPUTATION){
			instantiatedApproach = new OnlineGoalRecognitionMirroringNoRecomputation(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.HEURISTIC){
			instantiatedApproach = new OnlineGoalRecognitionUsingHeuristic(goalRecognitionProblem);
		} else if(approach == GoalRecognitionApproach.MIRRORING_LANDMARKS){
				instantiatedApproach = new OnlineGoalRecognitionMirroringWithLandmarks(goalRecognitionProblem, threshold);
		} else if(approach == GoalRecognitionApproach.LANDMARKS_GOALCOMPLETION_HEURISTIC){
			instantiatedApproach = new OnlineGoalRecognitionUsingLandmarksGoalCompletion(goalRecognitionProblem, threshold);
		} else if(approach == GoalRecognitionApproach.LANDMARKS_UNIQUENESS_HEURISTIC){
			instantiatedApproach = new OnlineGoalRecognitionUsingLandmarksUniquenessHeuristic(goalRecognitionProblem, threshold);
		}
		
		return instantiatedApproach;
	}
	
	private static GoalRecognition getInstantiatedApproach(GoalRecognitionApproach approach, String benchmarksDomainPath, String deceptiveApproachObservations){
		String domain = benchmarksDomainPath + "domain.pddl";
		String problem= benchmarksDomainPath + "template.pddl";
		String goalsFile = benchmarksDomainPath + "hyps.dat";
		String realGoalFile = benchmarksDomainPath + "real_hyp.dat";
		String observations = benchmarksDomainPath + "observations/" + deceptiveApproachObservations;
		
		GoalRecognition instantiatedApproach = null;
		if(approach == GoalRecognitionApproach.MIRRORING_BASELINE){
	        instantiatedApproach = new OnlineGoalRecognitionMirroringBaseline(domain, problem, goalsFile, observations, realGoalFile);
		} else if(approach == GoalRecognitionApproach.MIRRORING_LANDMARKS){
			instantiatedApproach = new OnlineGoalRecognitionMirroringWithLandmarks(domain, problem, goalsFile, observations, realGoalFile);
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