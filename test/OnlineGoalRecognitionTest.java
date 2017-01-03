import static org.junit.Assert.assertTrue;
import javaff.JavaFF;
import javaff.data.GroundProblem;
import javaff.data.Plan;
import javaff.search.UnreachableGoalException;

import org.junit.Test;

import parser.PDDLParser;
import domain.DomainName;

public class OnlineGoalRecognitionTest {

	@Test
	public void doJavaFFPlan(){
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
		DomainName domainName = DomainName.EASY_IPC_GRID;
	}
}