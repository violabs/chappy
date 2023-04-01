package com.violabs.chappy.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

object Embedding {
    @Serializable
    data class Request(val input: String? = null, val model: String = OpenAi.Models.TEXT_EMBEDDING_ADA_002)

    @Serializable
    data class Response @OptIn(ExperimentalSerializationApi::class) constructor(
        @JsonNames("object")
        var objectType: String? = null,
        var model: String? = null,
        var data: List<Datum>? = null,
        val usage: OpenAi.Usage? = null,
    ) {
        @Serializable
        data class Datum @OptIn(ExperimentalSerializationApi::class) constructor(
            val embedding: List<Double>? = null,
            val index: Int? = null,
            @JsonNames("object")
            var objectType: String? = null
        )
    }
}