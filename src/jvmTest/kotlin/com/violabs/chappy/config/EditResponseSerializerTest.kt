package com.violabs.chappy.config

import com.violabs.chappy.models.Edit
import com.violabs.chappy.models.OpenAi
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class EditResponseSerializerTest : Wesley() {
    private val minimumResponse = Edit.Response(objectType = "edit")
    private val fullResponse = Edit.Response(
        id = "edit-1Y2Z3X4W5V6U7T8S9R0Q",
        objectType = "edit",
        model = "code-davinci-edit-001",
        created = 1620920000,
        choices = setOf(
            Edit.Choice(
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
            "id": "edit-1Y2Z3X4W5V6U7T8S9R0Q",
            "objectType": "edit",
            "model": "code-davinci-edit-001",
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
    fun `Edit Response deserializes minimum object`() = test {
        expect { minimumResponse }

        whenever {
            val response = """
            {
                "objectType": "edit"
            }
            """.trimIndent()

            TestJson.decodeFromString(OpenAiResponseSerializer(), response)
        }
    }

    @Test
    fun `Edit Response deserializes full object`() = test {
        expect { fullResponse }

        whenever {
            TestJson.decodeFromString(OpenAiResponseSerializer(), fullResponseString)
        }
    }

    @Test
    fun `Edit Response serializes minimum object`() = test {
        expect {
            """
            {
                "id": null,
                "objectType": "edit",
                "model": null,
                "created": null,
                "choices": null,
                "usage": null
            }
            """.trimWhiteSpaces()
        }

        whenever {
            TestJson.encodeToString(OpenAiResponseSerializer(), minimumResponse).trimWhiteSpaces()
        }
    }

    @Test
    fun `Edit Response serializes full object`() = test {
        expect { fullResponseString.trimWhiteSpaces() }

        whenever {
            TestJson.encodeToString(OpenAiResponseSerializer(), fullResponse).trimWhiteSpaces()
        }
    }
}