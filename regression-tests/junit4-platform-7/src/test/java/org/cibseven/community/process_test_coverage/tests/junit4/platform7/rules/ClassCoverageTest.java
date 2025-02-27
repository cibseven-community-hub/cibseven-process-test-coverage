package org.cibseven.community.process_test_coverage.tests.junit4.platform7.rules;

import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRule;
import org.cibseven.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class ClassCoverageTest {

    // Note, if you assert a coverage on the ClassRule, it means if you run a
    // test without the others, it will probably fail
    @Rule
    @ClassRule
    public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create()
    				.assertClassCoverageAtLeast(1.0).build();

    @Test
    @Deployment(resources = CoverageTestProcessConstants.BPMN_PATH)
    public void testPathA() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("path", "A");
        rule.getRuntimeService().startProcessInstanceByKey(CoverageTestProcessConstants.PROCESS_DEFINITION_KEY, variables);
    }

    @Test
    @Deployment(resources = CoverageTestProcessConstants.BPMN_PATH)
    public void testPathB() {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("path", "B");
        rule.getRuntimeService().startProcessInstanceByKey(CoverageTestProcessConstants.PROCESS_DEFINITION_KEY, variables);
    }

}
