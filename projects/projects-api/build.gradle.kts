import dev.castocolina.gradle.plugin.YamlToJson
import java.io.FileWriter

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
    includeJsonTypeInfoAnnotation = true
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

tasks.register("applyCommonInterfaceForEvents") {
    group = "build"
    description = ""

    doFirst {
        val files = fileTree(file("${layout.buildDirectory.get()}/generated/jsonschema/src"))
                .filter { it.name.endsWith(".java", true) }
                .filter { it.name.matches(".*Event.*".toRegex()) }
                .filter { !it.name.startsWith("Abstract", true) }
        files.files.forEach {
            println("Adjusting common ancestor in file ${it.name}")
            val content = it.readText()
            val modifiedContent = content.replace("^(public class) ([\\w]*) (\\{)$".toRegex(RegexOption.MULTILINE), "\$1 \$2 extends AbstractEvent \$3")
                    .replace("(.*property = \")@class(\".*)".toRegex(RegexOption.MULTILINE), "\$1type\$2")
            it.writeText(modifiedContent)
        }
    }

    doLast {
        val file = File("${layout.buildDirectory.get()}/generated/jsonschema/src/eu/venthe/pipeline/orchestrator/projects/api/Event.java")
        val writer = FileWriter(file)
        writer.write("""
            package eu.venthe.pipeline.orchestrator.projects.api;

            import java.util.UUID;

            public interface Event {
                String getType();

                UUID getId();
                
                default Integer getVersion() {
                  return 1;
                }
            }
        """.trimIndent())
        writer.close()

        val files = fileTree(file("${layout.buildDirectory.get()}/generated/jsonschema/src"))
                .filter { it.name.endsWith(".java", true) }
                .filter { it.name.matches(".*Event.*".toRegex()) }
                .filter { it.name.startsWith("Abstract", true) }
        files.files.forEach {
            println("Adjusting common interface in file ${it.name}")
            val content = it.readText()
            val modifiedContent = content.replace("^(public) (class [\\w]*) (\\{)$".toRegex(RegexOption.MULTILINE), "\$1 abstract \$2 implements Event \$3")
            it.writeText(modifiedContent)
        }
    }

    dependsOn("generateJsonSchema2Pojo")
}

tasks["build"].dependsOn("applyCommonInterfaceForEvents")

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
