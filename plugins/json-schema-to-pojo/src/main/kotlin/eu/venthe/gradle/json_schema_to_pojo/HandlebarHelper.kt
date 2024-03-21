package eu.venthe.gradle.json_schema_to_pojo

import com.github.jknack.handlebars.Helper

val StringEqualsHelper = Helper<Any?> { context, options ->
    val value = context?.toString()
    val expectedValue = options.param<Any>(0)?.toString()
    value == expectedValue
}

val MergeContextsHelper = Helper<Map<*, *>> { context, options ->
    context + options.hash
}

val CapitalizeFirstLetter = Helper<String?> { context, options ->
    context?.toString()?.replaceFirstChar { it.uppercaseChar() }
}
