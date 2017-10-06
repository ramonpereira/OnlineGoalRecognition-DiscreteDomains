package bean;

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
			float numberOfLandmarks, float numberOfCallsPlanner){
		this.truePositiveRatio = truePositiveRatio;
		this.falsePositiveRatio = falsePositiveRatio;
		this.falseNegativeRatio = falseNegativeRatio;
		this.rankedFirstPercent = rankedFirstPercent;
		this.convergenceFirstPercent = convergenceFirstPercent;
		this.numberOfCandidateGoals = numberOfCandidateGoals;
		this.numberOfObservations = numberOfObservations;
		this.numberOfLandmarks = numberOfLandmarks;
		this.numberOfCallsPlanner = numberOfCallsPlanner;
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
}