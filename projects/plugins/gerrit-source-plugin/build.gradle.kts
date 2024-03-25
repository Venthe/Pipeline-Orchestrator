plugins {
    id("buildlogic.java-library-conventions")
    id("eu.venthe.gradle.generate_openapi_client") version "internal"
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":plugins:plugin-api"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("io.swagger.core.v3:swagger-models")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.openapitools:jackson-databind-nullable")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework:spring-web")
    testImplementation("org.slf4j:slf4j-simple")
}

openApiGenerate {
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    templateDir.set("$projectDir/src/main/resources/template/gerrit/")
    inputSpec.set("$projectDir/src/main/resources/schemas/gerrit.openapi.yaml")
    validateSpec = true
    targetPackage = "eu.venthe.pipeline.gerrit"
}

java.sourceSets["main"].java {
    srcDir("${layout.buildDirectory.get()}/generated/openapi/src/main/java")
}
