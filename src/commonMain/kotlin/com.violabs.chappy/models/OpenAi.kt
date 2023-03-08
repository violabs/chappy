package com.violabs.chappy.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

object OpenAi {

    @Serializable
    data class Usage @OptIn(ExperimentalSerializationApi::class) constructor(
        @JsonNames("prompt_tokens")
        var promptTokens: Int? = null,
        @JsonNames("completion_tokens")
        var completionTokens: Int? = null,
        @JsonNames("total_tokens")
        var totalTokens: Int? = null
    )
}