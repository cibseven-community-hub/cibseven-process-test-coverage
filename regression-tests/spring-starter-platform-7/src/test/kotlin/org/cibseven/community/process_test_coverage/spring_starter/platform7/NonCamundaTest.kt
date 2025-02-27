package org.cibseven.community.process_test_coverage.spring_starter.platform7

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import


@Configuration
class NonCamundaApplication

@SpringBootTest(classes = [NonCamundaApplication::class])
@Import(CoverageTestConfiguration::class)
class NonCamundaTest {

    @Test
    fun testSomethingNotCamunda() {
        val testString = "Testing"
        Assertions.assertThat(testString).isNotEmpty()
    }

}