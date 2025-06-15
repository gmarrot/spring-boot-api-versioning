import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

val jvm: String by project
val ktlint: String by project

plugins {
    // Core
    idea

    // Kotlin
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)

    // Spring
    alias(libs.plugins.spring.dependency.management)

    // Third Party
    alias(libs.plugins.ktlint)
}

group = "com.betomorrow.spring"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)

    // Spring
    compileOnly(platform(libs.spring.boot.bom))
    compileOnly(libs.spring.boot.core)
    compileOnly(libs.spring.boot.starter.web)

    // Documentation
    compileOnly(libs.springdoc.openapi.common)

    /**
     * Testing
     */
    // Testing: Spring
    testImplementation(platform(libs.spring.boot.bom))
    testImplementation(libs.spring.boot.starter.test) {
        exclude(group = "org.mockito", module = "mockito-core")
    }

    // Testing: Assertions
    testImplementation(libs.kotlin.test.junit5)

    // Testing: Mockk
    testImplementation(libs.mockk)
    testImplementation(libs.springmockk)
}

kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

ktlint {
    version = libs.versions.ktlint
}

testing {
    suites {
        withType<JvmTestSuite> {
            useJUnitJupiter()

            targets.all {
                testTask.configure {
                    testLogging {
                        events(TestLogEvent.SKIPPED, TestLogEvent.FAILED, TestLogEvent.PASSED)
                        exceptionFormat = TestExceptionFormat.FULL
                        showExceptions = true
                        showCauses = true
                        showStackTraces = true
                    }

                    reports {
                        junitXml.required = true
                        html.required = true
                    }
                }
            }
        }
    }
}
