import dev.castocolina.gradle.plugin.YamlToJson

plugins {
    id("buildlogic.java-library-conventions")
    id("org.jsonschema2pojo") version "1.2.1"
    id("com.ascert.open.json2yaml") version "1.1.1"
}

group = "eu.venthe.pipeline"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":shared-kernel"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.google.guava:guava")
}

jsonSchema2Pojo {
    setSource(files("${layout.buildDirectory.get()}/generated/jsonschema/json"))

    targetDirectory = file("${layout.buildDirectory.get()}/generated/jsonschema/src")

    targetPackage = "eu.venthe.pipeline.orchestrator.projects.api"
    useOptionalForGetters = true
    generateBuilders = true
    includeDynamicBuilders = true
}

tasks.generateJsonSchema2Pojo {
    dependsOn("replaceYamlReferencesWithJson")
}

open class YamlToJson2: YamlToJson() {
    @InputDirectory
    lateinit var baseDirectory: File

    override fun initConversionMap(extFrom: String, extTo: String) {
        if (sourceFiles is FileTree) {
            (sourceFiles as FileTree).visit {
                if (!this.file.isFile) {
                    return@visit
                }
                (convertMap as MutableMap<File, String>)[this.file] = swapExtension(this.file.relativeTo(baseDirectory).toString(), extFrom, extTo)
            }
        } else {
            super.initConversionMap(extFrom, extTo)
        }
    }
}

tasks.register<YamlToJson2>("prepareJsonschema") {
    group = "build"
    description = ""
    outDir = file("${layout.buildDirectory.get()}/generated/jsonschema/json")
    baseDirectory = file("$projectDir/src/main/resources/schemas")
    val files: FileTree = fileTree(baseDirectory)
            .filter {
                it.name.endsWith(".yaml", true)
                        || it.name.endsWith(".yml", true)
                        || it.name.endsWith(".json", true)
            }.asFileTree
    sourceFiles = files
    yamlExt = ".yaml"
    jsonExt = ".json"

    dependsOn("cleanJsonschema")
}

tasks.register("cleanJsonschema") {
    group = "build"
    description = ""

    doFirst {
        delete(file("${layout.buildDirectory.get()}/generated/jsonschema"))
    }
}

tasks.register("replaceYamlReferencesWithJson") {
    group = "build"
    description = ""

    doFirst {
        val files = fileTree(file("${layout.buildDirectory.get()}/generated/jsonschema/json"))
                .filter { it.name.endsWith(".json", true) }
        files.files.forEach {
            println("Replacing content in file ${it.name}")
            val content = it.readText()
            val modifiedContent = content.replace("(\\\"\\\$ref\\\":\\\"[\\w/\\.-]+)(\\.ya?ml)(#)".toRegex(), "\$1.json\$3")
            it.writeText(modifiedContent)
        }
    }

    dependsOn("prepareJsonschema")
}
