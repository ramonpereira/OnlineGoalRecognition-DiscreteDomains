package recognizer;

import java.math.BigDecimal;
import java.util.Map;

public class GoalRecognitionResult {

	private float rankedFirstPercent;
	private float convergenceFirstPercent;
	
	private float truePositiveRatio;
	private float falsePositiveRatio;
	private float falseNegativeRatio;
	
	private float numberOfCandidateGoals;
	private float numberOfObservations;
	private float numberOfLandmarks;
	private float numberOfCallsPlanner;
	private int numberOfRecognizedGoals;
	
	private boolean goalWasRecognized;
	
	private Map<Integer, Boolean> obsLevelToRecognizedCorrectly;
	
	private BigDecimal totalTime;
	private String problemFilename;
	
	public GoalRecognitionResult(boolean goalWasRecognized, int numberOfRecognizedGoals, BigDecimal totalTime){
		this.goalWasRecognized = goalWasRecognized;
		this.numberOfRecognizedGoals = numberOfRecognizedGoals;
		this.totalTime = totalTime;
	}
	
	public GoalRecognitionResult(float rankedFirstPercent, float convergenceFirstPercent, 
			float numberOfCandidateGoals, float numberOfObservations, 
			float numberOfLandmarks, float numberOfCallsPlanner){
		this.rankedFirstPercent = rankedFirstPercent;
		this.convergenceFirstPercent = convergenceFirstPercent;
		this.numberOfCandidateGoals = numberOfCandidateGoals;
		this.numberOfObservations = numberOfObservations;
		this.numberOfLandmarks = numberOfLandmarks;
		this.numberOfCallsPlanner = numberOfCallsPlanner;
	}
	
	public GoalRecognitionResult(float truePositiveRatio, float falsePositiveRatio, float falseNegativeRatio, 
			float rankedFirstPercent, float convergenceFirstPercent, 
			float numberOfCandidateGoals, float numberOfObservations, 
			float numberOfLandmarks, float numberOfCallsPlanner, Map<Integer, Boolean> obsLevelToRecognizedCorrectly){
		this.truePositiveRatio = truePositiveRatio;
		this.falsePositiveRatio = falsePositiveRatio;
		this.falseNegativeRatio = falseNegativeRatio;
		this.rankedFirstPercent = rankedFirstPercent;
		this.convergenceFirstPercent = convergenceFirstPercent;
		this.numberOfCandidateGoals = numberOfCandidateGoals;
		this.numberOfObservations = numberOfObservations;
		this.numberOfLandmarks = numberOfLandmarks;
		this.numberOfCallsPlanner = numberOfCallsPlanner;
		this.obsLevelToRecognizedCorrectly = obsLevelToRecognizedCorrectly;
	}
	
	public GoalRecognitionResult(String problemFilename, float truePositiveRatio, float falsePositiveRatio, float falseNegativeRatio, 
			float rankedFirstPercent, float convergenceFirstPercent, BigDecimal totalTime, 
			float numberOfCandidateGoals, float numberOfObservations, 
			float numberOfLandmarks, float numberOfCallsPlanner){
		this.problemFilename = problemFilename;
		this.truePositiveRatio = truePositiveRatio;
		this.falsePositiveRatio = falsePositiveRatio;
		this.falseNegativeRatio = falseNegativeRatio;
		this.rankedFirstPercent = rankedFirstPercent;
		this.convergenceFirstPercent = convergenceFirstPercent;
		this.totalTime = totalTime;
		this.numberOfCandidateGoals = numberOfCandidateGoals;
		this.numberOfObservations = numberOfObservations;
		this.numberOfLandmarks = numberOfLandmarks;
		this.numberOfCallsPlanner = numberOfCallsPlanner;
	}
	
	public GoalRecognitionResult(String problemFilename, float truePositiveRatio, float falsePositiveRatio, float falseNegativeRatio, 
			float rankedFirstPercent, float convergenceFirstPercent, BigDecimal totalTime, 
			float numberOfCandidateGoals, float numberOfObservations, 
			float numberOfLandmarks, float numberOfCallsPlanner, Map<Integer, Boolean> obsLevelToRecognizedCorrectly){
		this.problemFilename = problemFilename;
		this.truePositiveRatio = truePositiveRatio;
		this.falsePositiveRatio = falsePositiveRatio;
		this.falseNegativeRatio = falseNegativeRatio;
		this.rankedFirstPercent = rankedFirstPercent;
		this.convergenceFirstPercent = convergenceFirstPercent;
		this.totalTime = totalTime;
		this.numberOfCandidateGoals = numberOfCandidateGoals;
		this.numberOfObservations = numberOfObservations;
		this.numberOfLandmarks = numberOfLandmarks;
		this.numberOfCallsPlanner = numberOfCallsPlanner;
		this.obsLevelToRecognizedCorrectly = obsLevelToRecognizedCorrectly;
	}
	
	public float getTruePositiveRatio() {
		return truePositiveRatio;
	}
	
	public float getFalsePositiveRatio() {
		return falsePositiveRatio;
	}
	
	public float getFalseNegativeRatio() {
		return falseNegativeRatio;
	}
	
	public float getRankedFirstPercent() {
		return rankedFirstPercent;
	}
	
	public float getConvergenceFirstPercent() {
		return convergenceFirstPercent;
	}
	
	public float getNumberOfCandidateGoals() {
		return numberOfCandidateGoals;
	}
	
	public float getNumberOfObservations() {
		return numberOfObservations;
	}
	
	public float getNumberOfLandmarks() {
		return numberOfLandmarks;
	}
	
	public float getNumberOfCallsPlanner() {
		return numberOfCallsPlanner;
	}
	
	public BigDecimal getTotalTime() {
		return totalTime;
	}
	
	public String getProblemFilename() {
		return problemFilename;
	}
	
	public int getNumberOfRecognizedGoals() {
		return numberOfRecognizedGoals;
	}

	public boolean getGoalWasRecognized() {
		return goalWasRecognized;
	}
	
	public Map<Integer, Boolean> getObsLevelToRecognizedCorrectly() {
		return obsLevelToRecognizedCorrectly;
	}
}