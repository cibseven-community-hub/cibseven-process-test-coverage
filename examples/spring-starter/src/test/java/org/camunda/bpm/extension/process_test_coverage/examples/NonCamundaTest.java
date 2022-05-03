package org.camunda.bpm.extension.process_test_coverage.examples;

import org.camunda.bpm.extension.process_test_coverage.engine.ExcludeFromProcessCoverage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExcludeFromProcessCoverage
public class NonCamundaTest {

    @Test
    public void testSomethingElseAgain() {
        Assertions.assertTrue(true);
    }


}
