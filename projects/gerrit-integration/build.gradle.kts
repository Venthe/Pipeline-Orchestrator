plugins {
    id("buildlogic.java-library-conventions")
    id("org.openapi.generator") version "7.4.0"
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

enum class versions(val version: String) {
    AWAITILITY("4.2.0"),
    BSON_4_JACKSON("2.13.1"),
    COMMONS_CODEC("1.15"),
    COMMONS_IO("2.15.1"),
    COMMONS_TEXT("1.11.0"),
    DOCKER_JAVA("3.3.4"),
    DOCKER_JAVA_TRANSPORT_ZERODEP("3.3.6"),
    GUAVA("33.0.0-jre"),
    HIBERNATE_VALIDATOR("8.0.1.Final"),
    JACKSON("2.14.2"),
    JGIT("6.8.0.202311291450-r"),
    JGRAPHT_CORE("1.5.2"),
    JUNIT_PLATFORM_LAUNCHER("1.9.1"),
    MOCKSERVER_CLIENT_JAVA("5.15.0"),
    TEST_CONTAINERS("1.17.6"),
    TOGGLZ_SPRING_BOOT_STARTER("4.4.0"),
}

dependencies {
    implementation("io.swagger.core.v3:swagger-annotations:2.2.20")
    implementation("io.swagger.core.v3:swagger-models:2.2.20")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
}

tasks.register("cleanGeneratedGerritClient") {
    description = ""
    group = "openapi"

    delete(
            "${layout.buildDirectory.get()}/generated/openapi/gerrit"
    )
}

val _group = group
val gerritApi by tasks.register(
        name = "generateGerritClient",
        type = org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class,
) {
    description = ""
    group = "openapi"
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi/gerrit")
    apiPackage.set("${_group}.gerrit.api")
    invokerPackage.set("${_group}.gerrit.invoker")
    modelPackage.set("${_group}.gerrit.model")
    templateDir.set("$projectDir/src/main/resources/template/")

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
    srcDir("${layout.buildDirectory.get()}/generated/openapi/gerrit/src/main/java")
}
