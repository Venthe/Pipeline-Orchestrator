plugins {
    java
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.openapi.generator") version "6.2.1"
    id("io.freefair.lombok") version "6.6-rc1"
    id("org.graalvm.buildtools.native") version "0.9.18"
    id("org.jsonschema2pojo") version "1.2.1"
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val versions = mapOf(
        "testcontainers" to "1.19.1",
        "jackson" to "2.15.3",
        "swagger" to "2.2.18"
)

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains:annotations:24.0.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Common tests
    testImplementation("org.awaitility:awaitility:4.2.0")
    testImplementation("org.mock-server:mockserver-client-java:5.15.0")

    // Mapping
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${versions["jackson"]}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${versions["jackson"]}")
    implementation("de.undercouch:bson4jackson:2.15.0")

    // Observability
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    // Docker
    // https://mvnrepository.com/artifact/com.github.docker-java/docker-java
    implementation("com.github.docker-java:docker-java:3.3.4")
    implementation("com.github.docker-java:docker-java-transport-httpclient5:3.3.4")


    // Git
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.7.0.202309050840-r")

    // MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    // Swagger
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    implementation("io.swagger.core.v3:swagger-annotations:${versions["swagger"]}")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0-M1")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("io.swagger.core.v3:swagger-models:${versions["swagger"]}")

    testImplementation("org.junit.platform:junit-platform-launcher:1.10.1")
    implementation("commons-codec:commons-codec:1.16.0")

    // Test containers
    testImplementation("org.testcontainers:kafka:${versions["testcontainers"]}")
    testImplementation("org.testcontainers:testcontainers:${versions["testcontainers"]}")
    testImplementation("org.testcontainers:junit-jupiter:${versions["testcontainers"]}")
    testImplementation("org.testcontainers:mongodb:${versions["testcontainers"]}")
    testImplementation("org.testcontainers:mockserver:${versions["testcontainers"]}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val commonProps = mapOf(
        "licenseName" to "MIT",
        "licenseUrl" to "https://opensource.org/licenses/MIT")

//val gerritApi by tasks.register("generateGerritWebhookApi", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
//    outputDir.set("$buildDir/generated")
//    apiPackage.set("eu.venthe.pipeline.gerrit_mediator.gerrit.api")
//    invokerPackage.set("eu.venthe.pipeline.gerrit_mediator.jenkins.invoker")
//    modelPackage.set("eu.venthe.pipeline.gerrit_mediator.jenkins.model")
//    templateDir.set("$projectDir/src/main/resources/template/")
//
//    generatorName.set("spring")
//    inputSpec.set("$rootDir/specs/gerrit-event-stream.openapi.yaml")
//    configOptions.set(
//            mapOf(
//                    "library" to "spring-boot",
//                    "useOptional" to "true",
//                    "reactive" to "true",
//                    "useSwaggerUI" to "false",
//                    "interfaceOnly" to "true"
//            ) + commonProps
//    )
//
//    doLast {
//        delete(
//                "${project.buildDir}/build/generated/src/main/java/eu/venthe/pipeline/gerrit_mediator/gerrit/invoker",
//                "${project.buildDir}/build/generated/src/main/java/org/openapitools/configuration",
//        )
//    }
//}

val jenkinsApi by tasks.register("generateJenkinsWebhookApi", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
    outputDir.set("${layout.buildDirectory.get()}/generated")
    apiPackage.set("eu.venthe.pipeline.gerrit_mediator.gerrit.api")
    invokerPackage.set("eu.venthe.pipeline.gerrit_mediator.jenkins.invoker")
    modelPackage.set("eu.venthe.pipeline.gerrit_mediator.jenkins.model")
    templateDir.set("$rootDir/openapi/template/")

    generatorName.set("java")
    inputSpec.set("$rootDir/specs/jenkins.openapi.yaml")
    configOptions.set(
            mapOf(
                    "library" to "resttemplate",
                    "openApiNullable" to "false",
                    "useSpringBoot3" to "true",
                    "dateLibrary" to "java11",
                    "useJakartaEe" to "true"
            ) + commonProps
    )

    doLast {
        delete(
                "${layout.buildDirectory.get()}/build/generated/src/main/java/org/openapitools/configuration",
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn(/*gerritApi, */jenkinsApi)
    mustRunAfter(/*gerritApi, */jenkinsApi)
}

openApiValidate {
    inputSpec.set("$rootDir/specs/gerrit-event-stream.openapi.yaml")
}

java.sourceSets["main"].java {
    srcDir("build/generated/src/main/java")
}

jsonSchema2Pojo {
  targetPackage = "com.example"
  setSource(files("${rootDir}/specs/schema.json"))
}