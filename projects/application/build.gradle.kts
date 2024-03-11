plugins {
    id("buildlogic.java-application-conventions")

    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"

    id("org.openapi.generator") version "7.4.0"
    // id("org.graalvm.buildtools.native") version "0.9.18"
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
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${versions.JACKSON.version}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${versions.JACKSON.version}")
    implementation("com.github.docker-java:docker-java-transport-zerodep:${versions.DOCKER_JAVA_TRANSPORT_ZERODEP.version}")
    implementation("com.github.docker-java:docker-java:${versions.DOCKER_JAVA.version}")
    implementation("com.google.guava:guava:${versions.GUAVA.version}")
    implementation("commons-codec:commons-codec:${versions.COMMONS_CODEC.version}")
    implementation("commons-io:commons-io:${versions.COMMONS_IO.version}")
    implementation("de.undercouch:bson4jackson:${versions.BSON_4_JACKSON.version}")
    implementation("org.apache.commons:commons-text:${versions.COMMONS_TEXT.version}")
    implementation("org.eclipse.jgit:org.eclipse.jgit:${versions.JGIT.version}")
    implementation("org.hibernate.validator:hibernate-validator:${versions.HIBERNATE_VALIDATOR.version}")
    implementation("org.jgrapht:jgrapht-core:${versions.JGRAPHT_CORE.version}")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-web")
    implementation("org.togglz:togglz-spring-boot-starter:${versions.TOGGLZ_SPRING_BOOT_STARTER.version}")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.awaitility:awaitility:${versions.AWAITILITY.version}")
    testImplementation("org.junit.platform:junit-platform-launcher:${versions.JUNIT_PLATFORM_LAUNCHER.version}")
    testImplementation("org.mock-server:mockserver-client-java:${versions.MOCKSERVER_CLIENT_JAVA.version}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:testcontainers:${versions.TEST_CONTAINERS.version}")

    // implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.20")
    implementation("io.swagger.core.v3:swagger-models:2.2.20")
    // implementation("io.vertx:vertx-json-schema:4.5.1")// https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    // implementation("javax.validation:validation-api:2.0.1.Final")
    // implementation("org.jetbrains:annotations:23.0.0")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("net.bytebuddy:byte-buddy:1.14.12")
    implementation("net.bytebuddy:byte-buddy-agent:1.14.12")
    // implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    // implementation("org.springframework.kafka:spring-kafka")
    // testImplementation("org.springframework.kafka:spring-kafka-test")
    // testImplementation("org.testcontainers:kafka:$versionsTestcontainers")
    // testImplementation("org.testcontainers:mockserver:$versionsTestcontainers")
    // testImplementation("org.testcontainers:mongodb:$versionsTestcontainers")
}

tasks.register("cleanOpenapiOutput") {
    description = ""
    group = "openapi"

    delete(
            "${layout.buildDirectory.get()}/generated/openapi"
    )
}

val _group = group
val gerritApi by tasks.register(
        name = "generateGerrit",
        type = org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class,
) {
    description = ""
    group = "openapi"
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    apiPackage.set("${_group}.gerrit.api")
    invokerPackage.set("${_group}.gerrit.invoker")
    modelPackage.set("${_group}.gerrit.model")
    //templateDir.set("$projectDir/src/main/resources/template/")

    generatorName.set("java")
    inputSpec.set("$rootDir/schemas/gerrit.openapi.yaml")
    configOptions.set(mapOf(
            //"library" to "spring-cloud",
            //"useOptional" to "true",
            //"useSwaggerUI" to "false",
            //"interfaceOnly" to "false",
            "useJakartaEe" to "true",
            "library" to "resttemplate",
            "licenseName" to "MIT",
            "licenseUrl" to "https://opensource.org/licenses/MIT"
    ))

    // doLast {
    //     delete(
    //             "${layout.buildDirectory.get()}/build/generated/src/main/java/eu/venthe/pipeline/gerrit_mediator/gerrit/invoker",
    //             "${layout.buildDirectory.get()}/build/generated/src/main/java/org/openapitools/configuration",
    //     )
    // }
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
