plugins {
    id("buildlogic.java-library-conventions")
    id("org.openapi.generator") version "7.4.0"
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

enum class DependencyVersion(val version: String) {
    SWAGGER("2.2.20"),
    JACKSON_DATABIND_NULLABLE("0.2.6"),
    JAKARTA_ANNOTATION_API("3.0.0-M1")
}

dependencies {
    implementation(project(":plugins:plugin-api"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("io.swagger.core.v3:swagger-annotations:${DependencyVersion.SWAGGER.version}")
    implementation("io.swagger.core.v3:swagger-models:${DependencyVersion.SWAGGER.version}")
    implementation("jakarta.annotation:jakarta.annotation-api:${DependencyVersion.JAKARTA_ANNOTATION_API.version}")
    implementation("org.openapitools:jackson-databind-nullable:${DependencyVersion.JACKSON_DATABIND_NULLABLE.version}")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-web")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.slf4j:slf4j-simple")
}

tasks.register("cleanGeneratedGerritClient") {
    description = ""
    group = "openapi"

    delete(
            "${layout.buildDirectory.get()}/generated/openapi"
    )
}

val _group = group
val gerritApi by tasks.register(
        name = "generateGerritClient",
        type = org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class,
) {
    description = ""
    group = "openapi"
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    apiPackage.set("${_group}.gerrit.api")
    invokerPackage.set("${_group}.gerrit.invoker")
    modelPackage.set("${_group}.gerrit.model")
    templateDir.set("$projectDir/src/main/resources/template/gerrit/")

    generatorName.set("java")
    inputSpec.set("$rootDir/schemas/gerrit.openapi.yaml")
    configOptions.set(mapOf(
            "useJakartaEe" to "true",
            "library" to "resttemplate",
            "licenseName" to "MIT",
            "licenseUrl" to "https://opensource.org/licenses/MIT"
    ))
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn(gerritApi)
    mustRunAfter(gerritApi)
}

openApiValidate {
    inputSpec.set("$rootDir/schemas/gerrit.openapi.yaml")
}

java.sourceSets["main"].java {
    srcDir("${layout.buildDirectory.get()}/generated/openapi/src/main/java")
}
