package eu.venthe.gradle.json_schema_to_pojo

import org.gradle.api.Project
import java.io.File

open class GeneratePojoTakExtensions(private val project: Project) {
    var targetDirectory: File = project.file("${project.layout.buildDirectory.get()}/generated")
    var targetPackage: String? = null
    lateinit var source: File
}
