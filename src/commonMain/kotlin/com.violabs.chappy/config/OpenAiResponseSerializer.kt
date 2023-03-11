package com.violabs.chappy.config

import com.violabs.chappy.models.Chat
import com.violabs.chappy.models.Completion
import com.violabs.chappy.models.Edit
import com.violabs.chappy.models.OpenAi
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object OpenAiResponseSerializer : JsonContentPolymorphicSerializer<OpenAi.Response<*, *>>(OpenAi.Response::class) {
        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<OpenAi.Response<*, *>> {
            val objectDetails: String =
                element
                    .jsonObject["objectType"]
                    ?.jsonPrimitive
                    ?.content ?: throw SerializationException("Missing 'objectType' property from OpenAI.Response")

            return when {
                "chat" in objectDetails -> Chat.Response.serializer()
                "text" in objectDetails -> Completion.Response.serializer()
                "edit" in objectDetails -> Edit.Response.serializer()
                else -> throw SerializationException(
                    "Unknown OpenAI.Response type: ${element.jsonObject["object"]?.jsonPrimitive?.content}"
                )
            }
        }
    }