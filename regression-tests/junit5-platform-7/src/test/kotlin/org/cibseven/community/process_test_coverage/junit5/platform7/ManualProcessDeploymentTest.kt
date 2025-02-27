package org.cibseven.community.process_test_coverage.junit5.platform7

import org.cibseven.bpm.engine.TaskService
import org.cibseven.bpm.engine.test.Deployment
import org.cibseven.bpm.engine.variable.Variables
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class ManualProcessDeploymentTest {

    companion object {
        @JvmField
        @RegisterExtension
        var extension: ProcessEngineCoverageExtension = ProcessEngineCoverageExtension.builder().assertClassCoverageAtLeast(1.0).build()
    }


    fun completeTask() {
        val taskService: TaskService = extension.processEngine.taskService
        val task = taskService.createTaskQuery().singleResult()
        taskService.complete(task.id)
    }

    fun deployProcess() {
        extension.processEngine.repositoryService.createDeployment()
            .name("manual-deployment")
            .addClasspathResource("coverage-ratio-test.bpmn")
            .deploy()
    }

    @Test
    fun testCoverageRatio_Ok() {
        deployProcess()
        extension.processEngine.runtimeService.startProcessInstanceByKey("testCoverageRatio", Variables.createVariables().putValue("ok", true))
        completeTask()
    }

    @Test
    fun testCoverageRatio_NotOk() {
        deployProcess()
        extension.processEngine.runtimeService.startProcessInstanceByKey("testCoverageRatio", Variables.createVariables().putValue("ok", false))
        completeTask()
    }

}
