package com.violabs.chappy.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

object Completion {
    //https://platform.openai.com/docs/api-reference/completions/create
    @Serializable
    data class Request @OptIn(ExperimentalSerializationApi::class) constructor(
        val prompt: String? = null,
        override val model: String = OpenAi.Models.CODE_DAVINCI_002,
        override val temperature: Double = 0.0,
        @SerialName("max_tokens")
        @JsonNames("maxTokens")
        override val maxTokens: Int = 2,
        @SerialName("top_p")
        @JsonNames("topP")
        override val topP: Double = 1.0,
        @SerialName("n")
        @JsonNames("numberOfChoices")
        override val numberOfChoices: Int? = null,
        override val stream: Boolean = false,
        val logprobs: Int? = null,
        val stop: String? = null
    ) : OpenAi.Request<String?> () {
        override fun content(): String? = prompt
    }


    @Serializable
    data class Response @OptIn(ExperimentalSerializationApi::class) constructor(
        override val id: String? = null,
        @JsonNames("object")
        override val objectType: String? = null,
        override val model: String? = null,
        override val created: Long? = null,
        override val choices: Set<Choice>? = null,
        override val usage: OpenAi.Usage? = null
    ) : OpenAi.Response<String?, Choice>()


    @Serializable
    data class Choice @OptIn(ExperimentalSerializationApi::class) constructor(
        val text: String? = null,
        override val index: Int? = null,
        val logprobs: Int? = null,
        @JsonNames("finish_reason")
        override var finishReason: String? = null
    ) : OpenAi.Choice<String?>() {
        override fun content(): String? = text
    }
}