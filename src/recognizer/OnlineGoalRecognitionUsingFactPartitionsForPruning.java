package recognizer;

import javaff.search.UnreachableGoalException;
import bean.GoalRecognitionResult;

public class OnlineGoalRecognitionUsingFactPartitionsForPruning extends OnlineGoalRecognition {

	public OnlineGoalRecognitionUsingFactPartitionsForPruning(String fileName) {
		super(fileName);
	}

	/*
	 * TODO: Talk to Mor about using Fact Partitions for pruning candidate goals.
	 * Fact partitions we intend to use:
	 * 	- Strictly Activating: unless defined in the initial state, this fact can never be added;
	 *  - Unstable Activating: once deleted, this fact cannot be re-achieved; and
	 *  - Strictly Terminal: once added, cannot be deleted.
	 *  
	 * READING: https://arxiv.org/pdf/1604.01277.pdf (Subsection 3.2 - Fact Partitioning)
	 */
	@Override
	public GoalRecognitionResult recognizeOnline() throws UnreachableGoalException {
		return null;
	}
	
	@Override
	public GoalRecognitionResult call() throws Exception {
		return this.recognizeOnline();
	}
}