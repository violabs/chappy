package com.violabs.chappy.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

const val CODE_DAVINCI_EDIT = "code-davinci-edit-001"

object Edit {
    @Serializable
    data class Request @OptIn(ExperimentalSerializationApi::class) constructor(
        val input: String? = null,
        override val model: String = CODE_DAVINCI_EDIT,
        val instruction: String? = null,
        @SerialName("max_tokens")
        @JsonNames("maxTokens")
        override val maxTokens: Int = 2,
        @SerialName("n")
        @JsonNames("numberOfChoices")
        override val numberOfChoices: Int? = null,
        override val temperature: Double = 1.0,
        @SerialName("top_p")
        @JsonNames("topP")
        override val topP: Double = 1.0,
        val suffix: String? = null,
        override val stream: Boolean = false
    ) : OpenAi.Request<Request.Details>() {
        override fun content(): Details = Details(input, instruction, suffix)

        @Serializable
        data class Details(
            val input: String? = null,
            val instruction: String? = null,
            val suffix: String? = null
        )
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
    ) : OpenAi.Response<String, Choice>()

    @Serializable
    data class Choice @OptIn(ExperimentalSerializationApi::class) constructor(
        val text: String? = null,
        override val index: Int? = null,
        val logprobs: Int? = null,
        @JsonNames("finish_reason")
        override val finishReason: String? = null
    ) : OpenAi.Choice<String>() {
        override fun content(): String = text ?: ""
    }
}
