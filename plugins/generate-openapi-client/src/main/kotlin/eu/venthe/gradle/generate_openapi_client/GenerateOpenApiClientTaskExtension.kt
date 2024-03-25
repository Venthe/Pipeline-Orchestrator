package eu.venthe.gradle.generate_openapi_client

import org.gradle.api.Project
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

open class GenerateOpenApiClientTaskExtension(project: Project): OpenApiGeneratorGenerateExtension(project) {
    lateinit var targetPackage: String
}
