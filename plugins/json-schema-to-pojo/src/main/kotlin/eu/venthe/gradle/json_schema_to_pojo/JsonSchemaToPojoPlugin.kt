package eu.venthe.gradle.json_schema_to_pojo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import java.io.File

class JsonSchemaToPojoPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("generatePojo", GeneratePojoTakExtensions::class.java, project)
        project.tasks.register("generatePojo", GeneratePojoTask::class.java) {
            it.group = "jsonSchemaToPojo"
            it.dependsOn("cleanPojo")
        }
        project.tasks.register("cleanPojo") {
            it.group = "jsonSchemaToPojo"
            it.doFirst {
                val options = project.extensions.getByType(GeneratePojoTakExtensions::class.java)
                if (!options.targetDirectory.path.contains("generated")) {
                    throw UnsupportedOperationException()
                }
                println("Removing ${options.targetDirectory}")
                options.targetDirectory.deleteRecursively()
            }
        }

        project.afterEvaluate {
            val sourceSets = project.extensions.getByType(SourceSetContainer::class.java)
            val options = project.extensions.getByType(GeneratePojoTakExtensions::class.java)
            val createdSet = sourceSets.create("generatedPojo")
            createdSet.java.srcDirs(File(options.targetDirectory, "src/main/java"))
        }
    }
}
