package recognize;
import static org.junit.Assert.assertTrue;
import javaff.JavaFF;
import javaff.data.GroundProblem;
import javaff.data.Plan;
import javaff.search.UnreachableGoalException;
import kplanning.DomainProblemAdapter;
import kplanning.planner.GraphplanPlanner;
import kplanning.util.DomainProblemUtil;

import org.junit.Test;

import parser.PDDLParser;
import recognize.NaiveOnlineGoalRecognition;
import recognize.OnlineGoalRecognitionMirroringBaseline;
import recognize.OnlineGoalRecognitionMirroringNoRecomputation;
import recognize.OnlineGoalRecognitionUsingHeuristic;
import domain.DomainName;

public class OnlineGoalRecognitionTest {

	//public static String GOALRECOGNITION_PROBLEM = "experiments/easy-ipc-grid/easy-ipc-grid_p10-10-10_hyp-7_full.tar.bz2";
	//public static String GOALRECOGNITION_PROBLEM = "experiments/logistics/logistics_p03_hyp-1_full.tar.bz2";
	//public static String GOALRECOGNITION_PROBLEM = "experiments/intrusion-detection/intrusion-detection_p20_hyp-18_full.tar.bz2";
	//public static String GOALRECOGNITION_PROBLEM = "experiments/blocks-world/block-words_p01_hyp-0_full.tar.bz2";
	public static String GOALRECOGNITION_PROBLEM = "experiments/campus/bui-campus_generic_hyp-0_full_74.tar.bz2";
	//public static String GOALRECOGNITION_PROBLEM = "experiments/kitchen/kitchen_generic_hyp-0_full_10.tar.bz2";
	
	@Test
	public void doPlanUsingKPlanning(){
		DomainProblemAdapter adapter = DomainProblemAdapter.newInstance(DomainProblemUtil.getDomainProblem("easy_ipc_grid", 1));
		GraphplanPlanner planner = new GraphplanPlanner(adapter);
		kplanning.plan.PlanSolution plan = planner.plan();
		System.out.println(plan);
	}
	
	@Test
	public void doPlanUsingJavaFF(){
		DomainName domainName = DomainName.EASY_IPC_GRID;
		int problem = 1;
		System.out.println("Test - (Java) FF2\n");
		GroundProblem ground = PDDLParser.getGroundDomainProblem(domainName, problem);
		JavaFF ff = new JavaFF("");
		try {
			Plan plan = ff.plan(ground, false);
			System.out.println(plan.getCost());
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
			assertTrue(e.getMessage(),false);
		}
	}
	
	@Test
	public void testNaiveOnlineGoalRecognition(){
		NaiveOnlineGoalRecognition naiveGoalRecognition = new NaiveOnlineGoalRecognition(GOALRECOGNITION_PROBLEM);
		try {
			naiveGoalRecognition.recognizeOnline();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionBaseLine(){
		OnlineGoalRecognitionMirroringBaseline goalRecognitionBaseline = new OnlineGoalRecognitionMirroringBaseline(GOALRECOGNITION_PROBLEM);
		try {
			goalRecognitionBaseline.recognizeOnline();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionNoRecomputation(){
		OnlineGoalRecognitionMirroringNoRecomputation goalRecognitionNoRecomputation = new OnlineGoalRecognitionMirroringNoRecomputation(GOALRECOGNITION_PROBLEM);
		try {
			goalRecognitionNoRecomputation.recognizeOnline();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionUsingHeuristic(){
		OnlineGoalRecognitionUsingHeuristic goalRecognition = new OnlineGoalRecognitionUsingHeuristic(GOALRECOGNITION_PROBLEM);
		try {
			goalRecognition.recognizeOnline();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
}