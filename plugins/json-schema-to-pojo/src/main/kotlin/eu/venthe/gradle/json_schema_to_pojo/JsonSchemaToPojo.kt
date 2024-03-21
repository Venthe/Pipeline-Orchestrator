package eu.venthe.gradle.json_schema_to_pojo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Helper
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import java.io.StringWriter

class JsonSchemaToPojo() {
    private val handlebars: Handlebars
    private val objectMapper: ObjectMapper

    init {
        objectMapper = ObjectMapper(YAMLFactory())
        objectMapper.registerModules(JavaTimeModule())
        handlebars = Handlebars(ClassPathTemplateLoader("/templates"))
        handlebars.registerHelper("stringEquals", StringEqualsHelper)
        handlebars.registerHelper("mergeContexts", MergeContextsHelper)
        handlebars.registerHelper("capitalizeFirstLetter", CapitalizeFirstLetter)
        handlebars.setInfiniteLoops(true)
    }

    fun templateFile(fileId: String,
                     targetPackage: String?,
                     convertedFiles: Map<String, Unit>,
                     allFiles: Map<String, MappedFile>,
                     action: ((String) -> Unit)?) {


        val mappedFile = allFiles[fileId] ?: throw IllegalArgumentException()
        val root = objectMapper.readTree(mappedFile.sourceFile.readText())
        val className = mapClassName(root, mappedFile)
        val fileName = "${className}.java"

        println("Transpiling ${className}")
        if (root["type"]?.isTextual == true) {
            val data = objectNodeToMap(root as ObjectNode) + mapOf(
                    "package" to targetPackage,
                    "className" to className
            )
            val template = handlebars.compile("root")

            val output = StringWriter()
            template.apply(data, output)

            action?.let { it(output.toString()) }
        }
    }

    private fun mapClassName(root: JsonNode?, mappedFile: MappedFile): String {
        return (root?.get("id")?.asText() ?: mappedFile.relativeFile.nameWithoutExtension)
                .replace("-(\\w)".toRegex(RegexOption.MULTILINE), transform = { result ->
                    result.groups[1]?.value?.uppercase() ?: ""
                })
                .replaceFirstChar { it.uppercaseChar() }
    }
}
