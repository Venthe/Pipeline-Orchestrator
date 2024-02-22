plugins {
    id("buildlogic.java-library-conventions")

    `java-library`
}

dependencies {
    implementation(project(":plugin-api"))

    implementation("org.springframework:spring-context")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.springframework:spring-web")

    implementation("org.eclipse.jgit:org.eclipse.jgit:6.8.0.202311291450-r")
}
