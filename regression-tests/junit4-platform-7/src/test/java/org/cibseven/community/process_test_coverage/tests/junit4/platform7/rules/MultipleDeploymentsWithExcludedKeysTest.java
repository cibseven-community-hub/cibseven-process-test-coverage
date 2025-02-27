package org.cibseven.community.process_test_coverage.tests.junit4.platform7.rules;

import org.cibseven.bpm.engine.test.Deployment;
import org.cibseven.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRule;
import org.cibseven.community.process_test_coverage.junit4.platform7.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * Multiple deployments per test method test.
 * 
 * @author z0rbas
 *
 */
public class MultipleDeploymentsWithExcludedKeysTest {

    private static final String PROCESS_DEFINITION_KEY = "super-process-test-coverage";
    
    @Rule
    @ClassRule
    public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().excludeProcessDefinitionKeys("process-test-coverage").build();

    @Test
    @Deployment(resources = { "superProcess.bpmn", "process.bpmn" })
    public void testPathAAndSuperPathA() {
        
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("path", "A");
        variables.put("superPath", "A");
        rule.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
        
        rule.addTestMethodCoverageAssertionMatcher("testPathAAndSuperPathA", greaterThan(6.9 / 11.0));
        rule.addTestMethodCoverageAssertionMatcher("testPathAAndSuperPathA", lessThan(9 / 11.0));

    }

    @Test
    @Deployment(resources = { "superProcess.bpmn", "process.bpmn" })
    public void testPathBAndSuperPathB() {
        
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("path", "B");
        variables.put("superPath", "B");
        
        rule.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
        
        rule.addTestMethodCoverageAssertionMatcher("testPathBAndSuperPathB", greaterThan(6.9 / 11.0));
        rule.addTestMethodCoverageAssertionMatcher("testPathBAndSuperPathB", lessThan(9 / 11.0));
    }
}
