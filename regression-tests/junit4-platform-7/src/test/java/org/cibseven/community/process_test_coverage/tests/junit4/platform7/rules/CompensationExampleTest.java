package org.cibseven.community.process_test_coverage.tests.junit4.platform7.rules;

import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.bpm.engine.test.ProcessEngineRule;
import org.cibseven.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class CompensationExampleTest {

    @ClassRule
    @Rule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    private static final String PROCESS_DEFINITION_KEY = "compensation-test";

    @Test
    @Deployment(resources = "CompensationExample.bpmn")
    public void testHappyPath() {

        rule.getProcessEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
        rule.getTaskService().complete(rule.getTaskService().createTaskQuery().singleResult().getId());

    }

    @Test
    @Deployment(resources = "CompensationExample.bpmn")
    public void testCompensation() {

        rule.getProcessEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
        rule.getRuntimeService().correlateMessage("compensate");

    }

}
