package com.violabs.chappy.config

import com.violabs.chappy.models.Chat
import com.violabs.chappy.models.Completion
import com.violabs.chappy.models.OpenAi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun ChappyJson(builder: JsonBuilder.() -> Unit): Json = Json {
    builder(this)

    serializersModule = SerializersModule {
        contextual(OpenAi.Response::class, OpenAiResponseSerializer)
        polymorphic(OpenAi.Response::class) {
            subclass(Chat.Response::class, Chat.Response.serializer())
            subclass(Completion.Response::class, Completion.Response.serializer())
        }
        polymorphic(OpenAi.Request::class) {
            subclass(Chat.Request::class, Chat.Request.serializer())
            subclass(Completion.Request::class, Completion.Request.serializer())
        }
    }
}

val TestJson = ChappyJson {
    prettyPrint = false
    useArrayPolymorphism = false
    allowStructuredMapKeys = true
    encodeDefaults = true
    ignoreUnknownKeys = false
    isLenient = false
    allowSpecialFloatingPointValues = false
    useAlternativeNames = true
    classDiscriminator = "#class"
}