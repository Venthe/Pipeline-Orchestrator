plugins {
    id("buildlogic.java-library-conventions")
    id("buildlogic.java-jackson")
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":utilities"))

    implementation("com.google.guava:guava")
    testImplementation("javax.json:javax.json-api:1.1.4")
    testImplementation("org.glassfish:javax.json:1.1.4")
}
