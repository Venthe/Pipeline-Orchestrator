plugins {
    id("buildlogic.java-library-conventions")

    `java-library`
}

val dependencyVersions = mapOf(
    "dockerJava" to "3.3.4"
)

dependencies {
    implementation(project(":plugin-api"))

    implementation("org.springframework:spring-context")
    implementation("com.github.docker-java:docker-java:${dependencyVersions["dockerJava"]}")
    implementation("com.github.docker-java:docker-java-transport-zerodep:${dependencyVersions["dockerJava"]}")
}