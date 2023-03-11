package com.violabs.chappy.models

import com.violabs.chappy.JSON_MAPPER
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class EditTests : Wesley() {

    @Test
    fun `Request serializes correctly`() = test {
        expect {
            """
                {
                    "input": "test",
                    "model": "code-davinci-edit-001",
                    "instruction": "fixit",
                    "max_tokens": 2,
                    "n": 1,
                    "temperature": 1.0,
                    "top_p": 1.0,
                    "suffix": "test2",
                    "stream": false
                }
            """.trimWhiteSpaces()
        }

        whenever {
            val request = Edit.Request(
                "test",
                CODE_DAVINCI_EDIT,
                "fixit",
                2,
                1,
                1.0,
                1.0,
                "test2",
                false
            )

            JSON_MAPPER.encodeToString(Edit.Request.serializer(), request)
        }
    }

    @Test
    fun `Request deserializes correctly`() = test {
        expect {
            Edit.Request(
                "test",
                CODE_DAVINCI_EDIT,
                "fixit",
                2,
                1,
                1.0,
                1.0,
                "test2",
                false
            )
        }

        whenever {
            val request = """
                {
                    "input": "test",
                    "model": "code-davinci-edit-001",
                    "instruction": "fixit",
                    "max_tokens": 2,
                    "n": 1,
                    "temperature": 1.0,
                    "top_p": 1.0,
                    "suffix": "test2",
                    "stream": false
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(Edit.Request.serializer(), request)
        }
    }

    @Test
    fun `Response serializes correctly`() = test {
        expect {
            """
                {
                    "id": "test",
                    "objectType": "edit",
                    "model": "code-davinci-edit-001",
                    "created": 1,
                    "choices": [
                        {
                            "text": "test",
                            "index": 1,
                            "logprobs": null,
                            "finishReason": "stop"
                        }
                    ],
                    "usage": {
                        "promptTokens": 1,
                        "completionTokens": 1,
                        "totalTokens": 1
                    }
                }
            """.trimWhiteSpaces()
        }

        whenever {
            val response = Edit.Response(
                "test",
                "edit",
                CODE_DAVINCI_EDIT,
                1,
                setOf(
                    Edit.Choice(
                        "test",
                        1,
                        null,
                        "stop"
                    )
                ),
                OpenAi.Usage(
                    1,
                    1,
                    1
                )
            )

            JSON_MAPPER.encodeToString(Edit.Response.serializer(), response)
        }
    }

    @Test
    fun `Response deserializes correctly`() = test {
        expect {
            Edit.Response(
                "test",
                "edit",
                CODE_DAVINCI_EDIT,
                1,
                setOf(
                    Edit.Choice(
                        "test",
                        1,
                        null,
                        "stop"
                    )
                ),
                OpenAi.Usage(
                    1,
                    1,
                    1
                )
            )
        }

        whenever {
            val response = """
                {
                    "id": "test",
                    "objectType": "edit",
                    "model": "code-davinci-edit-001",
                    "created": 1,
                    "choices": [
                        {
                            "text": "test",
                            "index": 1,
                            "logprobs": null,
                            "finishReason": "stop"
                        }
                    ],
                    "usage": {
                        "promptTokens": 1,
                        "completionTokens": 1,
                        "totalTokens": 1
                    }
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(Edit.Response.serializer(), response)
        }
    }

    @Test
    fun `Choice serializes correctly`() = test {
        expect {
            """
                {
                    "text": "test",
                    "index": 1,
                    "logprobs": null,
                    "finishReason": "stop"
                }
            """.trimWhiteSpaces()
        }

        whenever {
            val choice = Edit.Choice(
                "test",
                1,
                null,
                "stop"
            )

            JSON_MAPPER.encodeToString(Edit.Choice.serializer(), choice)
        }
    }

    @Test
    fun `Choice deserializes correctly`() = test {
        expect {
            Edit.Choice(
                "test",
                1,
                null,
                "stop"
            )
        }

        whenever {
            val choice = """
                {
                    "text": "test",
                    "index": 1,
                    "logprobs": null,
                    "finishReason": "stop"
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(Edit.Choice.serializer(), choice)
        }
    }
}