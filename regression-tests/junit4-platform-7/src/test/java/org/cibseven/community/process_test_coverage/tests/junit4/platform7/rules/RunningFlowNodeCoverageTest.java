package org.cibseven.community.process_test_coverage.tests.junit4.platform7.rules;

import org.cibseven.bpm.engine.ProcessEngineException;
import org.cibseven.bpm.engine.runtime.ProcessInstance;
import org.cibseven.bpm.engine.task.Task;
import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRule;
import org.cibseven.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * This test should register a still uncompleted/running flow node as such. The
 * color of the flow node should differ from completed flow nodes in the
 * coverage diagram.
 * 
 * @author z0rbas
 *
 */
public class RunningFlowNodeCoverageTest {

    public static final String BPMN_PATH = "processStillRunning.bpmn";

    @Rule
    public TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    // TODO implement incident coverage handling and refactor this test to a separate class
    @Test(expected = ProcessEngineException.class)
    @Deployment(resources = { RunningFlowNodeCoverageTest.BPMN_PATH })
    public void shouldCoverGatewayAsStillRunningWithException() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("path", "UNEXPECTED");
        rule.getRuntimeService().startProcessInstanceByKey(CoverageTestProcessConstants.PROCESS_DEFINITION_KEY, variables);
    }

    @Test
    @Deployment(resources = { RunningFlowNodeCoverageTest.BPMN_PATH })
    public void shouldCoverManualTaskAsStillRunning() {

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("path", "B");
        final ProcessInstance processInstance = rule.getRuntimeService().startProcessInstanceByKey(
                CoverageTestProcessConstants.PROCESS_DEFINITION_KEY, variables);

        assertFalse("The process instance should still be running!", processInstance.isEnded());

        final Task runningTask = rule.getTaskService().createTaskQuery().active().taskDefinitionKey(
                "UserTask_B").singleResult();
        assertNotNull("One task instance should still be running!", runningTask);

    }

}
