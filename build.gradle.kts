import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    // Core
    idea

    // Kotlin
    val kotlinVersion = "1.9.25"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    // Spring
    id("org.springframework.boot") version "3.4.6"
    id("io.spring.dependency-management") version "1.1.7"

    // Third Party
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
}

group = "com.betomorrow.sandbox"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Documentation
    val springdocVersion = "2.8.8"
    implementation("org.springdoc:springdoc-openapi-starter-common:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")

    /**
     * Testing
     */
    // Testing: Spring
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito", module = "mockito-core")
    }

    // Testing: Assertions
    testImplementation(kotlin("test-junit5"))

    // Testing: Mockk
    testImplementation("io.mockk:mockk:1.14.2")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

ktlint {
    version = "0.50.0"
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

/**
 * Spring Boot
 */

springBoot {
    mainClass = "com.betomorrow.sandbox.ApplicationKt"
}

/**
 * Gradle Wrapper
 */

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
