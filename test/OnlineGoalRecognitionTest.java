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
import domain.DomainName;

public class OnlineGoalRecognitionTest {

	@Test
	public void doPlanKPlanning(){
		DomainProblemAdapter adapter = DomainProblemAdapter.newInstance(DomainProblemUtil.getDomainProblem("easy_ipc_grid", 1));
		GraphplanPlanner planner = new GraphplanPlanner(adapter);
		kplanning.plan.PlanSolution plan = planner.plan();
		System.out.println(plan);
	}
	
	@Test
	public void doPlanOnJavaFF(){
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
		NaiveOnlineGoalRecognition naiveGoalRecognition = new NaiveOnlineGoalRecognition("experiments/easy-ipc-grid/easy-ipc-grid_p5-5-5_hyp-2_full.tar.bz2");
		try {
			naiveGoalRecognition.onlineRecognize();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testOnlineGoalRecognitionNoRecomputation(){
		OnlineGoalRecognitionMirroringNoRecomputation goalRecognitionNoRecomputation = new OnlineGoalRecognitionMirroringNoRecomputation("experiments/easy-ipc-grid/easy-ipc-grid_p5-5-5_hyp-2_full.tar.bz2");
		try {
			goalRecognitionNoRecomputation.onlineRecognize();
		} catch (UnreachableGoalException e) {
			e.printStackTrace();
		}
	}
}