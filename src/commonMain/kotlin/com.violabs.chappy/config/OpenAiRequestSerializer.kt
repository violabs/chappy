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

class OpenAiRequestSerializer(
    val chatOptions: MutableSet<String> = mutableSetOf(OpenAi.Models.GPT_3_5_TURBO),
    val completionOptions: MutableSet<String> = mutableSetOf(OpenAi.Models.CODE_DAVINCI_002),
    val editOptions: MutableSet<String> = mutableSetOf(OpenAi.Models.CODE_DAVINCI_EDIT_001)
) : JsonContentPolymorphicSerializer<OpenAi.Request<*>>(OpenAi.Request::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<OpenAi.Request<*>> {
        val model: String =
            element
                .jsonObject["model"]
                ?.jsonPrimitive
                ?.content ?: throw SerializationException("Missing 'model' property from OpenAI.Request")

        return when(model) {
            in chatOptions -> Chat.Request.serializer()
            in completionOptions -> Completion.Request.serializer()
            in editOptions -> Edit.Request.serializer()
            else -> throw SerializationException(
                "Unknown OpenAI.Request type: ${element.jsonObject["model"]?.jsonPrimitive?.content}"
            )
        }
    }

    companion object {
        /** Here so you can override the serializer model options. */
        fun buildCustom(reference: OpenAiResponseSerializer.() -> Unit) = OpenAiResponseSerializer().apply(reference)
    }
}