package bean;

public class GoalRecognitionResult {

	private float rankedFirstPercent;
	private float convergenceFirstPercent;
	
	public GoalRecognitionResult(float rankedFirstPercent, float convergenceFirstPercent){
		this.rankedFirstPercent = rankedFirstPercent;
		this.convergenceFirstPercent = convergenceFirstPercent;
	}
	
	public float getRankedFirstPercent() {
		return rankedFirstPercent;
	}
	
	public float getConvergenceFirstPercent() {
		return convergenceFirstPercent;
	}
}