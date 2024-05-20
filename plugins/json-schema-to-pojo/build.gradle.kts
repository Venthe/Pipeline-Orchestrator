plugins {
    kotlin("jvm") version "1.9.23"
    `java-gradle-plugin`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.jknack:handlebars:4.4.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")
}

gradlePlugin {
    plugins {
        create("eu.venthe.gradle.json_schema_to_pojo") {
            id = "eu.venthe.gradle.json_schema_to_pojo"
            implementationClass = "eu.venthe.gradle.json_schema_to_pojo.JsonSchemaToPojoPlugin" // Replace with your plugin implementation class
            version = "internal"
        }
    }
}
