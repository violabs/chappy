package com.violabs.chappy

import kotlinx.serialization.json.Json

val JSON_MAPPER = Json {
    encodeDefaults = true
}

fun String.trimWhiteSpaces(): String =
    this.replace("[\\n\\s\\r\\t]+".toRegex(), "")