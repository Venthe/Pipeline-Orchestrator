import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	id("org.springframework.boot") version "3.0.0-RC1"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.20"
	kotlin("plugin.spring") version "1.7.20"
	// BECAUSE: Spring Native with GraalVM
	id("org.graalvm.buildtools.native") version "0.9.16"
}

group = "eu.venthe.gerrit"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

extra["testcontainersVersion"] = "1.17.4"

dependencies {
	webApiDependencies()
	kafkaDependencies()
	kotlinDependencies()
	observabilityDependencies()
	testContainersDependencies()

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

// BECAUSE: JUnit 5
tasks.withType<Test> {
	useJUnitPlatform()
}

// BECAUSE: Spring Native with GraalVM
tasks.withType<BootBuildImage> {
	buildpacks.set(listOf("gcr.io/paketo-buildpacks/bellsoft-liberica:9.9.0-ea", "gcr.io/paketo-buildpacks/java-native-image"))
}

fun DependencyHandlerScope.testContainersDependencies() {
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:kafka")
}

fun DependencyHandlerScope.kafkaDependencies() {
	implementation("org.springframework.kafka:spring-kafka")
	// FIXME: Disabled due to error:
	//  Caused by: java.lang.NoClassDefFoundError: org/junit/platform/launcher/TestExecutionListener
	// testImplementation("org.springframework.kafka:spring-kafka-test")
}

fun DependencyHandlerScope.webApiDependencies() {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	testImplementation("io.projectreactor:reactor-test")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-hateoas")
	// FIXME: Upgrade as soon as possible
	//  It should not affect things as it is compile only
	compileOnly("org.openapitools:openapi-generator-gradle-plugin:6.2.0") {
		// Disabled due to clash with Spring's default Logback implementation
		exclude("org.slf4j:slf4j-ext", "org.slf4j:slf4j-simple")
	}
}

fun DependencyHandlerScope.kotlinDependencies() {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

fun DependencyHandlerScope.observabilityDependencies() {
	runtimeOnly("io.micrometer:micrometer-registry-prometheus") // To enable metrics
	implementation("org.springframework.boot:spring-boot-starter-actuator")
}
