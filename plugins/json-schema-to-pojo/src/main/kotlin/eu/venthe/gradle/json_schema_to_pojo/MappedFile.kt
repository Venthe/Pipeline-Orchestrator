package eu.venthe.gradle.json_schema_to_pojo

import java.io.File

class MappedFile(
        val relativeFile: File,
        val sourceFile: File,
        val targetDirectory: File,
        val targetPackage: String?
) {
    fun getId(): String {
        return relativeFile.toString()
    }
}
