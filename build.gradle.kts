plugins {
    java

    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.freefair.lombok") version "8.10"
}

group = "eu.venthe"
version = "2.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

enum class Version(val value: String) {
    ASSERTJ("3.26.3");
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.assertj:assertj-core:${Version.ASSERTJ.value}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()

    // Added to explicitly enable ByteBuddy injection for IntelliJ IDEA debugging
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}
