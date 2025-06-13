import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Core
    idea

    // Kotlin
    val kotlinVersion = "1.7.22"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    // Spring
    id("org.springframework.boot") version "2.7.10"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    // Third Party
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
}

group = "com.betomorrow.sandbox"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Documentation
    val springdocVersion = "1.6.15"
    implementation("org.springdoc:springdoc-openapi-common:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-data-rest:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-ui:$springdocVersion")

    /**
     * Testing
     */
    // Testing: JUnit
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation(kotlin("test-junit5"))

    // Testing: Assertions
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")

    // Testing: Mock
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("com.ninja-squad:springmockk:3.1.2")

    // Testing: Spring
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito", module = "mockito-core")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

/**
 * Spring Boot
 */

springBoot {
    mainClass.set("com.betomorrow.sandbox.ApplicationKt")
}

/**
 * Gradle Wrapper
 */

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
