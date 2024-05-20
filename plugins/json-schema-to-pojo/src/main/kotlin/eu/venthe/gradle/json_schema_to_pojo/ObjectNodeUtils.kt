package eu.venthe.gradle.json_schema_to_pojo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode

fun objectNodeToMap(objectNode: ObjectNode): Map<String, Any?> {
    val resultMap = mutableMapOf<String, Any?>()
    objectNode.fields().forEach { (key, value) ->
        resultMap[key] = convertJsonNode(value)
    }
    return resultMap
}

fun convertJsonNode(jsonNode: JsonNode): Any? {
    return when {
        jsonNode.isObject -> objectNodeToMap(jsonNode as ObjectNode)
        jsonNode.isArray -> jsonNode.map { convertJsonNode(it) }.toList()
        jsonNode.isTextual -> jsonNode.asText()
        jsonNode.isBoolean -> jsonNode.asBoolean()
        jsonNode.isDouble -> jsonNode.asDouble()
        jsonNode.isFloat -> jsonNode.asDouble()
        jsonNode.isInt -> jsonNode.asInt()
        jsonNode.isLong -> jsonNode.asLong()
        jsonNode.isNull -> null
        else -> jsonNode.toString()
    }
}
