import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.*
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm") version "1.6.0"
    application
}

group = "me.dennis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        exceptionFormat = FULL
//        showStandardStreams = true
        events(PASSED, FAILED, SKIPPED)

        afterSuite(KotlinClosure2({ desc: TestDescriptor, result: TestResult ->
            if (desc.parent == null) { // will match the outermost suite
                val results = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
                val len = results.count()
                println("\n"+"-".repeat(len))
                println(results)
                println("-".repeat(len))
            }
        }))
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
}