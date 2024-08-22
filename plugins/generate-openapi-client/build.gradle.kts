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
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.8.0")
}

gradlePlugin {
    plugins {
        create("eu.venthe.gradle.generate_openapi_client") {
            id = "eu.venthe.gradle.generate_openapi_client"
            implementationClass = "eu.venthe.gradle.generate_openapi_client.GenerateOpenApiClientPlugin" // Replace with your plugin implementation class
            version = "internal"
        }
    }
}
