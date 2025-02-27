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

import org.sonar.api.Plugin
import org.sonar.api.config.PropertyDefinition
import org.sonar.api.resources.Qualifiers


class ProcessTestCoveragePlugin : Plugin {

    override fun define(context: Plugin.Context) {
        context.addExtensions(BpmnLanguage::class.java, BpmnQualityProfile::class.java)
        context.addExtension(ProcessTestCoverageMetrics::class.java)
        context.addExtensions(ProcessTestCoverageSensor::class.java, ProcessTestCoverageProjectSensor::class.java)
        context.addExtension(ProcessTestCoveragePage::class.java)
        context.addExtension(
            PropertyDefinition.builder(ReportsProvider.REPORT_PATHS_PROPERTY_KEY)
                .onQualifiers(Qualifiers.PROJECT)
                .multiValues(true)
                .category("Process Test Coverage")
                .description(
                    "Paths to CIB seven Process Test Coverage JSON report files. Each path can be either absolute or relative" +
                            " to the project base directory. Wildcard patterns are accepted (*, ** and ?)."
                )
                .build()
        )
    }

}
