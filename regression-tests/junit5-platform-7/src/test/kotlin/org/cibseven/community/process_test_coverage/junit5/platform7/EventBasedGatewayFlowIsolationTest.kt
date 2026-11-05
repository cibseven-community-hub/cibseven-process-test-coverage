package org.cibseven.community.process_test_coverage.junit5.platform7

import org.assertj.core.api.HamcrestCondition
import org.cibseven.bpm.engine.test.Deployment
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class EventBasedGatewayFlowIsolationTest {

    companion object {

        private const val PROCESS_DEFINITION_KEY = "throw-event-with-shared-id"

        @JvmField
        @RegisterExtension
        var extension: ProcessEngineCoverageExtension = ProcessEngineCoverageExtension.builder().build()
    }

    @Test
    @Deployment(resources = [CoverageTestProcessConstants.BPMN_PATH_EVENT_BASED_GATEWAY, "throwEventWithSharedId.bpmn"])
    fun testSharedElementIdDoesNotInheritEventBasedGatewayFlow() {
        extension.processEngine.runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY)
        extension.addTestMethodCoverageCondition("testSharedElementIdDoesNotInheritEventBasedGatewayFlow()", HamcrestCondition(Matchers.greaterThanOrEqualTo(1.0)))
        extension.addTestMethodCoverageCondition("testSharedElementIdDoesNotInheritEventBasedGatewayFlow()", HamcrestCondition(Matchers.lessThanOrEqualTo(1.0)))
    }

}
