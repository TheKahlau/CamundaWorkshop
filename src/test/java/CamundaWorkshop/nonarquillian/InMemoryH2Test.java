package CamundaWorkshop.nonarquillian;

import CamundaWorkshop.LoggerDelegate;
import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class InMemoryH2Test {

  @ClassRule
  @Rule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  private static final String PROCESS_DEFINITION_KEY = "TwitterQaTest";

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

  @Before
  public void setup() {
    init(rule.getProcessEngine());
    Mocks.register("createTweetDelegate", new LoggerDelegate());
  }

  /**
   * Just tests if the process definition is deployable.
   */
  @Test
  @Deployment(resources = PROCESS_DEFINITION_KEY + ".bpmn")
  public void testParsingAndDeployment() {
    // nothing is done here, as we just want to check for exceptions during deployment
  }

  @Test
  @Deployment(resources = PROCESS_DEFINITION_KEY + ".bpmn")
  public void testHappyPath() {
      // old code to start the process and complete the task
//      RuntimeService runtimeService = processEngine().getRuntimeService();
//      TaskService taskService = processEngine().getTaskService();
//      ProcessInstance processInstance = runtimeService
//              .startProcessInstanceByKey(PROCESS_DEFINITION_KEY, Variables.putValue("tweet", "I'M ON THE HIIIIIIIIIIIIIGHWAY TO HELL!"));
//      Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
//      taskService.complete(task.getId(), Variables.putValue("approved", true));

      ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, Variables.putValue("tweet", "I'M ON THE HIIIIIIIIIIIIIGHWAY TO HELL!"));
      assertThat(processInstance).isWaitingAt("checkTweet");
      complete(task(), Variables.putValue("approved", true));
	  assertThat(processInstance).isEnded();
	  assertThat(processInstance).hasNotPassed("tweetRejected");
	  assertThat(processInstance).hasPassed("tweetApproved");
	  // Now: Drive the process by API and assert correct behavior by camunda-bpm-assert
  }

}
