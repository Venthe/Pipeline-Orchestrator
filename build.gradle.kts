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
    ASSERTJ("3.26.3"),
    GUAVA("33.3.0-jre"),
    JACKSON_DATATYPE_JDK8("2.18.1"),
    JGIT("6.10.0.202406032230-r");
}

dependencies {
    implementation("com.google.guava:guava:${Version.GUAVA.value}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Version.JACKSON_DATATYPE_JDK8.value}")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.assertj:assertj-core:${Version.ASSERTJ.value}")
    testImplementation("org.eclipse.jgit:org.eclipse.jgit:${Version.JGIT.value}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()

    // Added to explicitly enable ByteBuddy injection for IntelliJ IDEA debugging
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}
