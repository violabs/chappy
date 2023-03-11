package com.violabs.chappy.config

import com.violabs.wesly.Wesley
import kotlinx.serialization.SerializationException
import org.junit.jupiter.api.Test

class OpenAiResponseSerializer2Test : Wesley() {

    @Test
    fun `does not deserialize item missing objectType`() = testThrows<SerializationException> {
        val response = """
            {
                "notObjectType": "chat.completion"
            }
        """.trimIndent()

        TestJson.decodeFromString(OpenAiResponseSerializer, response)
    }

    @Test
    fun `does not deserialize item with unknown objectType`() = testThrows<SerializationException> {
        val response = """
            {
                "objectType": "unknown"
            }
        """.trimIndent()

        TestJson.decodeFromString(OpenAiResponseSerializer, response)
    }

}