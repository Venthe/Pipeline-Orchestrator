import gradle.kotlin.dsl.accessors._4b51223d572bfee998f38a2d8ad9814e.implementation

plugins {
    id("buildlogic.java-common-conventions")

    `java-library`
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
}
