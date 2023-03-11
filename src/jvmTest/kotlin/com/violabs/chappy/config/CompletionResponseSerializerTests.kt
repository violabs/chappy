package com.violabs.chappy.config

import com.violabs.chappy.models.Completion
import com.violabs.chappy.models.OpenAi
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class CompletionResponseSerializerTests : Wesley() {
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