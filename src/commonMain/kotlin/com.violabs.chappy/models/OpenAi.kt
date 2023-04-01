package com.violabs.chappy.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
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


    /**
     * T represents the content type such as a group of messages or a prompt
     */
    @Serializable
    sealed class Request<T> {
        abstract val model: String
        abstract val temperature: Double
        @OptIn(ExperimentalSerializationApi::class)
        @SerialName("max_tokens")
        @JsonNames("maxTokens")
        abstract val maxTokens: Int
        @OptIn(ExperimentalSerializationApi::class)
        @SerialName("top_p")
        @JsonNames("topP")
        abstract val topP: Double
        @OptIn(ExperimentalSerializationApi::class)
        @SerialName("n")
        @JsonNames("numberOfChoices")
        abstract val numberOfChoices: Int?
        abstract val stream: Boolean

        abstract fun content(): T
    }

    /**
     * U represents the type of response choice (String or Message)
     * T represents the content type such as a choice or a group of choices
     */
    @Serializable
    sealed class Response<U, T : Choice<U>> {
        abstract val id: String?
        abstract val objectType: String?
        abstract val model: String?
        abstract val created: Long?
        abstract val choices: List<T>?
        abstract val usage: Usage?
    }

    @Serializable
    abstract class Choice<T> {
        abstract val index: Int?
        abstract val finishReason: String?

        abstract fun content(): T?
    }

    object Models {
        const val GPT_3_5_TURBO = "gpt-3.5-turbo"
        const val CODE_DAVINCI_002 = "code-davinci-002"
        const val CODE_DAVINCI_EDIT_001 = "code-davinci-edit-001"
        const val TEXT_EMBEDDING_ADA_002 = "text-embedding-ada-002"
    }
}