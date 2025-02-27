package org.cibseven.community.process_test_coverage.spring_starter.platform7;

import org.cibseven.community.process_test_coverage.spring_test.common.ProcessEngineCoverageProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CoverageTestConfiguration {

    @Bean
    public ProcessEngineCoverageProperties processEngineCoverageProperties() {
        return ProcessEngineCoverageProperties.builder()
                .assertClassCoverageAtLeast(1.0)
                .addInclusionPatternForTestClasses("*ProcessTest")
                .build();
    }

}
