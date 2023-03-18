package com.violabs.chappy.models

import com.violabs.chappy.JSON_MAPPER
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class CompletionTests : Wesley() {

    @Test
    fun `Request serializes correctly`() = test {
        expect {
            """
            {
              "prompt": "test",
              "model": "code-davinci-002",
              "temperature": 0.0,
              "max_tokens": 7,
              "top_p": 1.0,
              "n": 1,
              "stream": false,
              "logprobs": null,
              "stop": "\n"
            }
        """.trimWhiteSpaces()
        }

        whenever {
            val request = Completion.Request(
                "test",
                OpenAi.Models.CODE_DAVINCI_002,
                0.0,
                7,
                1.0,
                1,
                false,
                stop = "\n"
            )

            JSON_MAPPER.encodeToString(Completion.Request.serializer(), request)
        }
    }

    @Test
    fun `Request deserializes correctly`() = test {
        expect {
            Completion.Request(
                "test",
                OpenAi.Models.CODE_DAVINCI_002,
                0.0,
                7,
                1.0,
                1,
                false,
                stop = "\n"
            )
        }

        whenever {
            val request = """
                {
                  "prompt": "test",
                  "model": "code-davinci-002",
                  "temperature": 0.0,
                  "maxTokens": 7,
                  "topP": 1.0,
                  "numberOfChoices": 1,
                  "stream": false,
                  "logprobs": null,
                  "stop": "\n"
                }
            """.trimWhiteSpaces()



            JSON_MAPPER.decodeFromString(Completion.Request.serializer(), request)
        }
    }

    @Test
    fun `Response deserializes correctly`() = test {
        expect {
            Completion.Response(
                "1",
                "testing",
                OpenAi.Models.CODE_DAVINCI_002,
                123455678,
                choices = listOf(
                    Completion.Choice(
                        "Hello",
                        1,
                        null,
                        "testing"
                    )
                ),
                usage = OpenAi.Usage(1, 2, 3)
            )
        }

        whenever {
            val response = """
                {
                    "id": "1",
                    "object": "testing",
                    "created": 123455678,
                    "model": "${OpenAi.Models.CODE_DAVINCI_002}"
                    "choices": [
                        {
                            "index": 1,
                            "text": "Hello",
                            "logprobs": null,
                            "finish_reason": "testing"
                        }
                    ],
                    "usage": {
                        "prompt_tokens": 1,
                        "completion_tokens": 2,
                        "total_tokens": 3
                    }
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(Completion.Response.serializer(), response)
        }
    }

    @Test
    fun `Response serializes correctly`() = test {
        expect {
            """
                {
                    "id": "1",
                    "objectType": "testing",
                    "model": "${OpenAi.Models.CODE_DAVINCI_002}",
                    "created": 123455678,
                    "choices": [
                        {
                            "text": "Hello",
                            "index": 1,
                            "logprobs": null,
                            "finishReason": "testing"
                        }
                    ],
                    "usage": {
                        "promptTokens": 1,
                        "completionTokens": 2,
                        "totalTokens": 3
                    }
                }
            """.trimWhiteSpaces()
        }

        whenever {
            val response = Completion.Response(
                "1",
                "testing",
                OpenAi.Models.CODE_DAVINCI_002,
                123455678,
                choices = listOf(
                    Completion.Choice(
                        "Hello",
                        1,
                        null,
                        "testing"
                    )
                ),
                usage = OpenAi.Usage(1, 2, 3)
            )

            JSON_MAPPER.encodeToString(Completion.Response.serializer(), response)
        }
    }

    @Test
    fun `Choice deserializes correctly`() = test {
        expect {
            Completion.Choice(
                "Hello",
                1,
                null,
                "testing"
            )
        }

        whenever {
            val choice = """
                {
                    "text": "Hello"
                    "index": 1,
                    "logprobs": null,
                    "finish_reason": "testing"
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(Completion.Choice.serializer(), choice)
        }
    }

    @Test
    fun `Choice serializes correctly`() = test {
        expect {
            """
                {
                    "text": "Hello",
                    "index": 1,
                    "logprobs": null,
                    "finishReason": "testing"
                }
            """.trimWhiteSpaces()
        }

        whenever {
            val choice = Completion.Choice(
                "Hello",
                1,
                null,
                "testing"
            )

            JSON_MAPPER.encodeToString(Completion.Choice.serializer(), choice)
        }
    }
}