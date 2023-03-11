package com.violabs.chappy.config

import com.violabs.chappy.models.Chat
import com.violabs.chappy.models.OpenAi
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class ChatResponseSerializerTests : Wesley() {
    private val minimumResponse = Chat.Response(objectType = "chat.completion")
    private val fullResponse = Chat.Response(
        id = "chatcmpl-1Y2Z3X4W5V6U7T8S9R0Q",
        objectType = "chat.completion",
        model = "gpt-3.5-turbo",
        created = 1620920000,
        choices = setOf(
            Chat.Choice(
                message = Chat.Message(
                    "Do I understand you right? Your body is a melody?",
                    Chat.Message.Role.ASSISTANT
                ),
                index = 0,
                finishReason = "length"
            )
        ),
        usage = OpenAi.Usage(
            promptTokens = 1,
            completionTokens = 1,
            totalTokens = 2
        )
    )

    val fullResponseString = """
        {
            "id": "chatcmpl-1Y2Z3X4W5V6U7T8S9R0Q",
            "objectType": "chat.completion",
            "model": "gpt-3.5-turbo",
            "created": 1620920000,
            "choices": [
                {
                    "index": 0,
                    "message": {
                        "content": "Do I understand you right? Your body is a melody?",
                        "role": "assistant"
                    },
                    "finishReason": "length"
                }
            ],
            "usage": {
                "promptTokens": 1,
                "completionTokens": 1,
                "totalTokens": 2
            }
        }
    """.trimIndent()

    @Test
    fun `Chat Response deserializes minimum object`() = test {
        expect { minimumResponse }

        whenever {
            val response = """
            {
                "objectType": "chat.completion"
            }
            """.trimIndent()

            TestJson.decodeFromString(OpenAiResponseSerializer, response)
        }
    }

    @Test
    fun `Chat Response deserializes full object`() = test {
        expect { fullResponse }

        whenever {
            TestJson.decodeFromString(OpenAiResponseSerializer, fullResponseString)
        }
    }

    @Test
    fun `Chat Response serializes minimum object`() = test {
        expect {
            """
            {
                "id": null,
                "objectType": "chat.completion",
                "model": null,
                "created": null,
                "choices": null,
                "usage": null
            }
            """.trimWhiteSpaces()
        }

        whenever {
            val response = Chat.Response(objectType = "chat.completion")

            TestJson.encodeToString(OpenAiResponseSerializer, response)
        }
    }

    @Test
    fun `Chat Response serializes full object`() = test {
        expect {
            fullResponseString.trimWhiteSpaces()
        }

        whenever {
            TestJson.encodeToString(OpenAiResponseSerializer, fullResponse).trimWhiteSpaces()
        }
    }
}