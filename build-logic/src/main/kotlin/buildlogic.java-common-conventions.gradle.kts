plugins {
    java
    id("io.freefair.lombok")
}

repositories {
    mavenCentral()
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


val versionsTestcontainers = "1.17.6"

dependencies {
    constraints {
        implementation("org.springframework:spring-context:6.1.3")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
        implementation("org.springframework:spring-web:6.1.3")
    }

    testImplementation("org.testcontainers:junit-jupiter:$versionsTestcontainers")
}

allprojects {
    layout.buildDirectory = File(rootProject.projectDir, "build/${project.name}")
}