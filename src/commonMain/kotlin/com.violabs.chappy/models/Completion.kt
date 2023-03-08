package com.violabs.chappy.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

const val CODE_DAVINCI = "code-davinci-002"

object Completion {
    //https://platform.openai.com/docs/api-reference/completions/create
    @Serializable
    data class Request @OptIn(ExperimentalSerializationApi::class) constructor(
        val prompt: String? = null,
        val model: String = CODE_DAVINCI,
        val temperature: Double = 0.0,
        @SerialName("max_tokens")
        @JsonNames("maxTokens")
        val maxTokens: Int = 2,
        @SerialName("top_p")
        @JsonNames("topP")
        val topP: Double = 1.0,
        @SerialName("n")
        @JsonNames("numberOfChoices")
        val numberOfChoices: Int? = null,
        val stream: Boolean = false,
        val logprobs: Int? = null,
        val stop: String? = null
    )


    @Serializable
    data class Response @OptIn(ExperimentalSerializationApi::class) constructor(
        val id: String? = null,
        @JsonNames("object")
        var objectType: String? = null,
        var model: String? = null,
        val created: Long? = null,
        val choices: Set<Choice>? = null,
        val usage: OpenAi.Usage? = null
    )


    @Serializable
    data class Choice @OptIn(ExperimentalSerializationApi::class) constructor(
        val text: String? = null,
        val index: Int? = null,
        val logprobs: Int? = null,
        @JsonNames("finish_reason")
        var finishReason: String? = null
    )
}