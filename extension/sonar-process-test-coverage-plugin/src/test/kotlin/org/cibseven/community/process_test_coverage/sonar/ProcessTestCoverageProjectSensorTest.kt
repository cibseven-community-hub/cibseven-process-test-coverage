/*-
 * #%L
 * Camunda Process Test Coverage Sonar Plugin
 * %%
 * Copyright (C) 2019 - 2024 Camunda
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.cibseven.community.process_test_coverage.sonar

import org.assertj.core.api.Assertions.assertThat
import org.cibseven.community.process_test_coverage.core.export.CoverageStateJsonExporter
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.sonar.api.batch.fs.internal.TestInputFileBuilder
import org.sonar.api.batch.sensor.SensorDescriptor
import org.sonar.api.batch.sensor.internal.SensorContextTester
import org.sonar.api.batch.sensor.measure.Measure
import org.sonar.api.config.internal.MapSettings
import java.io.File

class ProcessTestCoverageProjectSensorTest {
    private val sensor = ProcessTestCoverageProjectSensor()

    @Test
    fun testDescribe() {
        val descriptor: SensorDescriptor = Mockito.mock(SensorDescriptor::class.java)
        sensor.describe(descriptor)
        Mockito.verify(descriptor).name("CIB seven Process Test Coverage (Project)")
    }

    @Test
    fun shouldSkipIfNoReport() {
        val context = SensorContextTester.create(File(""))
        val file = TestInputFileBuilder.create(
                "target/test-classes/one_result",
                "process.bpmn"
        ).setLanguage(BpmnLanguage.KEY).build()
        context.fileSystem().add(file)
        val settings = MapSettings()
        settings.setProperty(ReportsProvider.REPORT_PATHS_PROPERTY_KEY, "target/test-classes/one_result/unknown.json")
        context.setSettings(settings)
        sensor.execute(context)
        assertThat(context.measure(context.project().key(), ProcessTestCoverageMetrics.PROCESS_TEST_COVERAGE)).isNull()
        assertThat(context.measure(context.project().key(), ProcessTestCoverageMetrics.PROCESS_TEST_COVERAGE_REPORT)).isNull()
    }

    @Test
    fun shouldAnalyseOneResult() {
        val context = SensorContextTester.create(File(""))
        val file = TestInputFileBuilder.create(
                "target/test-classes",
                "process.bpmn"
        ).setLanguage(BpmnLanguage.KEY).build()
        context.fileSystem().add(file)
        val settings = MapSettings()
        settings.setProperty(ReportsProvider.REPORT_PATHS_PROPERTY_KEY, "one_result/report.json")
        context.setSettings(settings)
        sensor.execute(context)
        assertThat(context.measure(context.project().key(), ProcessTestCoverageMetrics.PROCESS_TEST_COVERAGE).value()).isEqualTo(100.0)
        assertCoverageStatusReport(context.measure(context.project().key(), ProcessTestCoverageMetrics.PROCESS_TEST_COVERAGE_REPORT),
                File("target/test-classes/one_result/expected_result.json"))
    }

    @Test
    fun shouldAnalyseTwoResults() {
        val context = SensorContextTester.create(File(""))
        val file = TestInputFileBuilder.create(
                "target/test-classes",
                "process.bpmn"
        ).setLanguage(BpmnLanguage.KEY).build()
        context.fileSystem().add(file)
        val settings = MapSettings()
        settings.setProperty(ReportsProvider.REPORT_PATHS_PROPERTY_KEY, "two_results/**/report.json")
        context.setSettings(settings)
        sensor.execute(context)
        assertThat(context.measure(context.project().key(), ProcessTestCoverageMetrics.PROCESS_TEST_COVERAGE).value()).isEqualTo(100.0)
        assertCoverageStatusReport(context.measure(context.project().key(), ProcessTestCoverageMetrics.PROCESS_TEST_COVERAGE_REPORT),
                File("target/test-classes/two_results/expected_result.json"))
    }

    @Test
    fun shouldAnalyseMultipleModules() {
        val context = SensorContextTester.create(File(""))
        val file = TestInputFileBuilder.create(
                "target/test-classes",
                "process.bpmn"
        ).setLanguage(BpmnLanguage.KEY).build()
        context.fileSystem().add(file)
        val file2 = TestInputFileBuilder.create(
                "target/test-classes",
                "process2.bpmn"
        ).setLanguage(BpmnLanguage.KEY).build()
        context.fileSystem().add(file2)
        val settings = MapSettings()
        settings.setProperty(ReportsProvider.REPORT_PATHS_PROPERTY_KEY, "multiple_projects/**/report.json")
        context.setSettings(settings)
        sensor.execute(context)
        assertThat(context.measure(context.project().key(), ProcessTestCoverageMetrics.PROCESS_TEST_COVERAGE).value())
                .isEqualTo((5.0 / 6.0) * 100.0)
        assertCoverageStatusReport(context.measure(context.project().key(), ProcessTestCoverageMetrics.PROCESS_TEST_COVERAGE_REPORT),
                File("target/test-classes/multiple_projects/expected_result.json"))

    }

    private fun assertCoverageStatusReport(measure: Measure<String>, expectedResult: File) {
        val coverageResult = CoverageStateJsonExporter.readCoverageStateResult(measure.value())
        val expectedCoverageResult = CoverageStateJsonExporter.readCoverageStateResult(
                expectedResult.readText()
        )
        assertThat(coverageResult.models).containsExactlyInAnyOrderElementsOf(expectedCoverageResult.models)
        assertThat(coverageResult.suites).containsExactlyInAnyOrderElementsOf(expectedCoverageResult.suites)
    }


}
