import gradle.kotlin.dsl.accessors._4b51223d572bfee998f38a2d8ad9814e.testImplementation

plugins {
    id("buildlogic.java-common-conventions")

    `java-library`
}

enum class DependencyVersion(val version: String) {
    JUNIT_PLATFORM_LAUNCHER("1.9.1"),
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") {
        because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions")
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
