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


val versionsTestcontainers = "1.17.6";
val versionsJackson = "2.14.2";

dependencies {
    testImplementation("org.testcontainers:junit-jupiter:$versionsTestcontainers")
}

allprojects {
    layout.buildDirectory = File(rootProject.projectDir, "build/${project.name}")
}