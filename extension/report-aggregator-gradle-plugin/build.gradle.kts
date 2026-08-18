plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish")
}

repositories {
    mavenCentral()
    // Developing purposes: 
    // The parent POM chain imports org.cibseven.bpm:cibseven-bom as snapshot, which is neither on Maven
    // Central nor copied into target/dependencies by copy-dependencies. The surrounding Maven
    // build already resolves it, so it is available from the local repository.
    mavenLocal()
    maven {
        url = uri(layout.projectDirectory.dir("target/dependencies"))
    }
}

dependencies {
    implementation(gradleApi())
    implementation(group = "org.cibseven.community.process_test_coverage", name = "cibseven-process-test-coverage-report-generator", version = "$version")
    testImplementation(gradleTestKit())
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "6.0.0")
    testRuntimeOnly(group = "org.junit.platform", name = "junit-platform-launcher")
    testImplementation(group = "org.assertj", name = "assertj-core", version = "3.27.6")
}

tasks.test {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("aggregateProcessTestCoverage") {
            id = "org.cibseven.community.process_test_coverage.report-aggregator"
            implementationClass = "org.cibseven.community.process_test_coverage.report.aggregator.ReportAggregatorPlugin"
        }
    }
}
