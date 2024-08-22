package eu.venthe.gradle.generate_openapi_client

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGeneratorsExtension
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorMetaExtension
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorValidateExtension
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import org.openapitools.generator.gradle.plugin.tasks.GeneratorsTask
import org.openapitools.generator.gradle.plugin.tasks.MetaTask
import org.openapitools.generator.gradle.plugin.tasks.ValidateTask

class GenerateOpenApiClientPlugin : Plugin<Project> {
    companion object {
        const val pluginGroup = "CustomOpenAPITools"
    }

    override fun apply(project: Project) {
        project.extensions.create("openApiClientGenerator", GenerateOpenApiClientTaskExtension::class.java)

        val validate = project.extensions.create(
                "openApiValidate",
                OpenApiGeneratorValidateExtension::class.java,
                project
        )

        val generators = project.extensions.create(
                "openApiGenerators",
                OpenApiGeneratorGeneratorsExtension::class.java,
                project
        )

        val meta = project.extensions.create(
                "openApiMeta",
                OpenApiGeneratorMetaExtension::class.java,
                project
        )

        val generate = project.extensions.create(
                "openApiGenerate",
                GenerateOpenApiClientTaskExtension::class.java,
                project
        )

        project.tasks.register("openApiGenerators", GeneratorsTask::class.java).configure {
            it.group = pluginGroup
            it.description = "Lists generators available via Open API Generators."

            it.include.set(generators.include)
        }

        project.tasks.register("openApiMeta", MetaTask::class.java).configure {
            it.group = pluginGroup
            it.description = "Generates a new generator to be consumed via Open API Generator."

            it.generatorName.set(meta.generatorName)
            it.packageName.set(meta.packageName)
            it.outputFolder.set(meta.outputFolder)
        }

        project.tasks.register("openApiValidate", ValidateTask::class.java).configure {
            it.group = pluginGroup
            it.description = "Validates an Open API 2.0 or 3.x specification document."

            it.inputSpec.set(validate.inputSpec)
            it.recommend.set(validate.recommend)
        }


        val generateClient = project.tasks.register("generateClient", GenerateTask::class.java) {
            it.group = pluginGroup
            it.description = ""

            it.verbose.set(generate.verbose)
            it.validateSpec.set(generate.validateSpec)
            it.outputDir.set(generate.outputDir)
            it.inputSpec.set(generate.inputSpec)
            it.inputSpecRootDirectory.set(generate.inputSpecRootDirectory)
            it.remoteInputSpec.set(generate.remoteInputSpec)
            it.templateDir.set(generate.templateDir)
            it.auth.set(generate.auth)
            it.globalProperties.set(generate.globalProperties)
            it.configFile.set(generate.configFile)
            it.skipOverwrite.set(generate.skipOverwrite)
            it.packageName.set(generate.packageName)
            it.apiPackage.set(generate.apiPackage)
            it.modelPackage.set(generate.modelPackage)
            it.modelNamePrefix.set(generate.modelNamePrefix)
            it.modelNameSuffix.set(generate.modelNameSuffix)
            it.apiNameSuffix.set(generate.apiNameSuffix)
            it.instantiationTypes.set(generate.instantiationTypes)
            it.typeMappings.set(generate.typeMappings)
            it.additionalProperties.set(generate.additionalProperties)
            it.serverVariables.set(generate.serverVariables)
            it.languageSpecificPrimitives.set(generate.languageSpecificPrimitives)
            it.importMappings.set(generate.importMappings)
            it.schemaMappings.set(generate.schemaMappings)
            it.inlineSchemaNameMappings.set(generate.inlineSchemaNameMappings)
            it.inlineSchemaOptions.set(generate.inlineSchemaOptions)
            it.nameMappings.set(generate.nameMappings)
            it.modelNameMappings.set(generate.modelNameMappings)
            it.parameterNameMappings.set(generate.parameterNameMappings)
            it.openapiNormalizer.set(generate.openapiNormalizer)
            it.invokerPackage.set(generate.invokerPackage)
            it.groupId.set(generate.groupId)
            it.id.set(generate.id)
            it.version.set(generate.version)
            it.library.set(generate.library)
            it.gitHost.set(generate.gitHost)
            it.gitUserId.set(generate.gitUserId)
            it.gitRepoId.set(generate.gitRepoId)
            it.releaseNote.set(generate.releaseNote)
            it.httpUserAgent.set(generate.httpUserAgent)
            it.reservedWordsMappings.set(generate.reservedWordsMappings)
            it.ignoreFileOverride.set(generate.ignoreFileOverride)
            it.removeOperationIdPrefix.set(generate.removeOperationIdPrefix)
            it.skipOperationExample.set(generate.skipOperationExample)
            it.apiFilesConstrainedTo.set(generate.apiFilesConstrainedTo)
            it.modelFilesConstrainedTo.set(generate.modelFilesConstrainedTo)
            it.supportingFilesConstrainedTo.set(generate.supportingFilesConstrainedTo)
            it.generateModelTests.set(generate.generateModelTests)
            it.generateModelDocumentation.set(generate.generateModelDocumentation)
            it.generateApiTests.set(generate.generateApiTests)
            it.generateApiDocumentation.set(generate.generateApiDocumentation)
            it.logToStderr.set(generate.logToStderr)
            it.enablePostProcessFile.set(generate.enablePostProcessFile)
            it.skipValidateSpec.set(generate.skipValidateSpec)
            it.generateAliasAsModel.set(generate.generateAliasAsModel)
            it.engine.set(generate.engine)
            it.cleanupOutput.set(generate.cleanupOutput)
            it.dryRun.set(generate.dryRun)
            it.generatorName.set(generate.generatorName)

            it.apiPackage.set("${generate.targetPackage}.api")
            it.invokerPackage.set("${generate.targetPackage}.invoker")
            it.modelPackage.set("${generate.targetPackage}.model")

            it.configOptions.set(
                generate.configOptions.get() + mapOf(
                    "licenseName" to "MIT",
                    "licenseUrl" to "https://opensource.org/licenses/MIT"
                )
            )

            it.doLast {
                it.project.delete(it.project.fileTree(generate.outputDir.get()) { u ->
                    u.exclude("src")
                })
            }
        }

        val clean = project.tasks.register("cleanGeneratedClient") {
            it.group = pluginGroup
            it.description = ""

            it.doLast {
                it.project.delete(generate.outputDir)
            }
        }

        project.tasks.withType(JavaCompile::class.java).configureEach {
            it.dependsOn(generateClient)
            it.mustRunAfter(generateClient)
        }

        project.tasks.getByName("clean") {
            it.dependsOn(clean)
        }
    }
}
