package com.violabs.chappy.config

import com.violabs.chappy.models.Chat
import com.violabs.chappy.models.Completion
import com.violabs.chappy.models.OpenAi
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.expect

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

    class CompletionResponseSerializerTests : Wesley () {
        private val minimumResponse = Completion.Response(objectType = "text_completion")
        private val fullResponse = Completion.Response(
            id = "cmpl-1Y2Z3X4W5V6U7T8S9R0Q",
            objectType = "text_completion",
            model = "code-davinci-02",
            created = 1620920000,
            choices = setOf(
                Completion.Choice(
                    text = "Do I understand you right? Your body is a melody?",
                    index = 0,
                    finishReason = "length",
                    logprobs = 1
                )
            ),
            usage = OpenAi.Usage(
                promptTokens = 1,
                completionTokens = 1,
                totalTokens = 2
            )
        )

        private val fullResponseString = """
            {
                "id": "cmpl-1Y2Z3X4W5V6U7T8S9R0Q",
                "objectType": "text_completion",
                "model": "code-davinci-02",
                "created": 1620920000,
                "choices": [
                    {
                        "text": "Do I understand you right? Your body is a melody?",
                        "index": 0,
                        "logprobs": 1,
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
        fun `Completion Response deserializes minimum object`() = test {
            expect { minimumResponse }

            whenever {
                val response = """
                {
                    "objectType": "text_completion"
                }
                """.trimIndent()

                TestJson.decodeFromString(OpenAiResponseSerializer, response)
            }
        }

        @Test
        fun `Completion Response deserializes full object`() = test {
            expect { fullResponse }

            whenever {
                TestJson.decodeFromString(OpenAiResponseSerializer, fullResponseString)
            }
        }

        @Test
        fun `Completion Response serializes minimum object`() = test {
            expect {
                """
                {
                    "id": null,
                    "objectType": "text_completion",
                    "model": null,
                    "created": null,
                    "choices": null,
                    "usage": null
                }
                """.trimWhiteSpaces()
            }

            whenever {
                val response = Completion.Response(objectType = "text_completion")

                TestJson.encodeToString(OpenAiResponseSerializer, response)
            }
        }

        @Test
        fun `Completion Response serializes full object`() = test {
            expect {
                fullResponseString.trimWhiteSpaces()
            }

            whenever {
                TestJson.encodeToString(OpenAiResponseSerializer, fullResponse).trimWhiteSpaces()
            }
        }
    }
}