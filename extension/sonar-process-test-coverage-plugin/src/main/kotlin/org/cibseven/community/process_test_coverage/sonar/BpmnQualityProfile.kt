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

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition


class BpmnQualityProfile : BuiltInQualityProfilesDefinition {
    override fun define(context: BuiltInQualityProfilesDefinition.Context) {
        val profile = context.createBuiltInQualityProfile("BPMN Rules", BpmnLanguage.KEY)
        profile.setDefault(true)
        profile.done()
    }
}
