package org.cibseven.community.process_test_coverage.spring_test.platform7

import org.cibseven.bpm.engine.RuntimeService
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestExecutionListeners

@SpringBootApplication
class DifferentReportDirectory

@SpringBootTest(classes = [DifferentReportDirectory::class])
@Import(DifferentReportDirectoryTestConfiguration::class, ProcessEngineCoverageConfiguration::class)
@TestExecutionListeners(value = [ProcessEngineCoverageTestExecutionListener::class], mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@Disabled
class DifferentReportDirectoryTest {

    @Autowired
    private lateinit var runtimeService: RuntimeService

    @Test
    fun testPathA() {
        val variables: MutableMap<String, Any> = HashMap()
        variables["path"] = "A"
        runtimeService.startProcessInstanceByKey(CoverageTestProcessConstants.PROCESS_DEFINITION_KEY, variables)
    }

    @Test
    fun testPathB() {
        val variables: MutableMap<String, Any> = HashMap()
        variables["path"] = "B"
        runtimeService.startProcessInstanceByKey(CoverageTestProcessConstants.PROCESS_DEFINITION_KEY, variables)
    }

}
