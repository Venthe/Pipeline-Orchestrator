package eu.venthe.gradle.json_schema_to_pojo

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GeneratePojoTask : DefaultTask() {
    //@get:OutputDirectory
    //private var outDir: File = project.file("${project.buildDir}/generated")
//
    //@get:InputFiles
    //@get:SkipWhenEmpty
    //private lateinit var sourceFiles: FileCollection
//
    //@get:Input
    //private var yamlExt: String = ".yaml"    // configurable default extension for YAML files
//
    //@get:Input
    //private var jsonExt: String = ".json"    // configurable default extension for JSON files
//
    //// @Internal
    //// def convertMap = [:]

    @get:Internal
    private lateinit var options: GeneratePojoTakExtensions

    @TaskAction
    fun perform() {
        options = project.extensions.getByType(GeneratePojoTakExtensions::class.java)

        val root = options.source
        val fileTree = root.walk().toList()
        val packageDirectory = options.targetPackage?.replace(".", "/") ?: "."

        val converter = JsonSchemaToPojo()

        val convertedFiles: Map<String, Unit> = mapOf()
        val allFiles = fileTree.filter { it.isFile }
                .map {
                    MappedFile(it.relativeTo(root), it, File(options.targetDirectory, "src/main/java/${packageDirectory}"), options.targetPackage)
                }
                .associateBy { it.relativeFile.toString() }

//        allFiles.keys.forEach {
//            converter.templateFile(
//                    fileId = it,
//                    allFiles = allFiles,
//                    targetPackage = options.targetPackage,
//                    convertedFiles = convertedFiles
//            ) {
//                println(it)
//            }
//        }

//                    Triple(it, File(options.targetDirectory, "src/main/java/${packageDirectory}/${it.relativeTo(root)}"), it.relativeTo(root)) }
//                .forEach {
//                    val template = jsonSchemaToPojo.template(it.first, options.targetPackage)
//                    println(template)
//                    val newFile = File(it.second.parent, it.second.nameWithoutExtension + ".java")
//                    File(newFile.parent).mkdirs()
//                    newFile.writeText(template)
//                }
    }
}


//jsonSchema2Pojo {
//    setSource(files("${layout.buildDirectory.get()}/generated/jsonschema/json"))
//
//    targetDirectory = file("${layout.buildDirectory.get()}/generated/jsonschema/src")
//
//    targetPackage = "eu.venthe.pipeline.orchestrator.projects.api"
//    useOptionalForGetters = true
//}
//
//tasks.generateJsonSchema2Pojo {
//    dependsOn("replaceYamlReferencesWithJson")
//}

//open class YamlToJson2: YamlToJson() {
//    @InputDirectory
//    lateinit var baseDirectory: File
//
//    override fun initConversionMap(extFrom: String, extTo: String) {
//        if (sourceFiles is FileTree) {
//            (sourceFiles as FileTree).visit {
//                if (!this.file.isFile) {
//                    return@visit
//                }
//                (convertMap as MutableMap<File, String>)[this.file] = swapExtension(this.file.relativeTo(baseDirectory).toString(), extFrom, extTo)
//            }
//        } else {
//            super.initConversionMap(extFrom, extTo)
//        }
//    }
//}

//tasks.register("applyCommonInterfaceForEvents") {
//    group = "build"
//    description = ""
//
//    doFirst {
//        val files = fileTree(file("${layout.buildDirectory.get()}/generated/jsonschema/src"))
//                .filter { it.name.endsWith(".java", true) }
//                .filter { it.name.matches(".*Event.*".toRegex()) }
//                .filter { !it.name.startsWith("Abstract", true) }
//        files.files.forEach {
//            println("Adjusting common ancestor in file ${it.name}")
//            val content = it.readText()
//            val modifiedContent = content.replace("^(public class) ([\\w]*) (\\{)$".toRegex(RegexOption.MULTILINE), "\$1 \$2 extends AbstractEvent \$3")
//            it.writeText(modifiedContent)
//        }
//    }
//
//    doLast {
//        val file = File("${layout.buildDirectory.get()}/generated/jsonschema/src/eu/venthe/pipeline/orchestrator/projects/api/Event.java")
//        val writer = FileWriter(file)
//        writer.write("""
//            package eu.venthe.pipeline.orchestrator.projects.api;
//
//            import java.util.UUID;
//
//            public interface Event {
//                String getType();
//
//                UUID getId();
//
//                default Integer getVersion() {
//                  return 1;
//                }
//            }
//        """.trimIndent())
//        writer.close()
//
//        val files = fileTree(file("${layout.buildDirectory.get()}/generated/jsonschema/src"))
//                .filter { it.name.endsWith(".java", true) }
//                .filter { it.name.matches(".*Event.*".toRegex()) }
//                .filter { it.name.startsWith("Abstract", true) }
//        files.files.forEach {
//            println("Adjusting common interface in file ${it.name}")
//            val content = it.readText()
//            val modifiedContent = content.replace("^(public) (class [\\w]*) (\\{)$".toRegex(RegexOption.MULTILINE), "\$1 abstract \$2 implements Event \$3")
//            it.writeText(modifiedContent)
//        }
//    }
//
//    dependsOn("generateJsonSchema2Pojo")
//}
//
//tasks["compileJava"].dependsOn("applyCommonInterfaceForEvents")
//
//tasks.register<YamlToJson2>("prepareJsonschema") {
//    group = "build"
//    description = ""
//    outDir = file("${layout.buildDirectory.get()}/generated/jsonschema/json")
//    baseDirectory = file("$projectDir/src/main/resources/schemas")
//    val files: FileTree = fileTree(baseDirectory)
//            .filter {
//                it.name.endsWith(".yaml", true)
//                        || it.name.endsWith(".yml", true)
//                        || it.name.endsWith(".json", true)
//            }.asFileTree
//    sourceFiles = files
//    yamlExt = ".yaml"
//    jsonExt = ".json"
//
//    dependsOn("cleanJsonschema")
//}
//
//tasks.register("cleanJsonschema") {
//    group = "build"
//    description = ""
//
//    doFirst {
//        delete(file("${layout.buildDirectory.get()}/generated/jsonschema"))
//    }
//}
//
//tasks.register("replaceYamlReferencesWithJson") {
//    group = "build"
//    description = ""
//
//    doFirst {
//        val files = fileTree(file("${layout.buildDirectory.get()}/generated/jsonschema/json"))
//                .filter { it.name.endsWith(".json", true) }
//        files.files.forEach {
//            println("Replacing content in file ${it.name}")
//            val content = it.readText()
//            val modifiedContent = content.replace("(\\\"\\\$ref\\\":\\\"[\\w/\\.-]+)(\\.ya?ml)(#)".toRegex(), "\$1.json\$3")
//            it.writeText(modifiedContent)
//        }
//    }
//
//    dependsOn("prepareJsonschema")
//}
