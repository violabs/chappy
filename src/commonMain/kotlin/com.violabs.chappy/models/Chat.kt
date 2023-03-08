package com.violabs.chappy.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

const val GPT_3_5_TURBO = "gpt-3.5-turbo"

object Chat {
    @Serializable
    data class Message(val content: String? = null, val role: Role = Role.USER) {
        @Serializable
        enum class Role {
            @SerialName("system")
            @JsonNames("SYSTEM")
            @OptIn(ExperimentalSerializationApi::class)
            SYSTEM,

            @SerialName("user")
            @JsonNames("USER")
            @OptIn(ExperimentalSerializationApi::class)
            USER,

            @SerialName("assistant")
            @JsonNames("ASSISTANT")
            @OptIn(ExperimentalSerializationApi::class)
            ASSISTANT;

            override fun toString(): String = name.lowercase()

            companion object {
                val DEFAULT = USER
            }
        }
    }

    //https://platform.openai.com/docs/api-reference/chat/create
    @Serializable
    data class Request @OptIn(ExperimentalSerializationApi::class) constructor(
        val messages: List<Message>? = null,
        val model: String = GPT_3_5_TURBO,
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
        val index: Int? = null,
        val message: Message? = null,
        @JsonNames("finish_reason")
        var finishReason: String? = null
    )
}