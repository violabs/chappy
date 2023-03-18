package com.violabs.chappy.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

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
        override val model: String = OpenAi.Models.GPT_3_5_TURBO,
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
        override val stream: Boolean = false
    ) : OpenAi.Request<List<Message>?>() {
        override fun content(): List<Message>? = messages
    }

    @Serializable
    data class Response @OptIn(ExperimentalSerializationApi::class) constructor(
        override val id: String? = null,
        @JsonNames("object")
        override var objectType: String? = null,
        override var model: String? = null,
        override val created: Long? = null,
        override val choices: List<Choice>? = null,
        override val usage: OpenAi.Usage? = null
    ) : OpenAi.Response<Message?, Choice>()

    @Serializable
    data class Choice @OptIn(ExperimentalSerializationApi::class) constructor(
        override val index: Int? = null,
        val message: Message? = null,
        @JsonNames("finish_reason")
        override var finishReason: String? = null
    ) : OpenAi.Choice<Message?>() {
        override fun content(): Message? = message
    }
}