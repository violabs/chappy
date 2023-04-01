package com.violabs.chappy.models

import com.violabs.chappy.config.TestJson
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class EmbeddingTests : Wesley() {
    private val minimumRequest = Embedding.Request(model = OpenAi.Models.TEXT_EMBEDDING_ADA_002)

    private val fullRequest = Embedding.Request(
        input = "test input",
        model = OpenAi.Models.TEXT_EMBEDDING_ADA_002
    )

    private val fullRequestString = """
        {
            "input": "test input",
            "model": "${OpenAi.Models.TEXT_EMBEDDING_ADA_002}"
        }
    """.trimIndent()

    private val fullResponse = Embedding.Response(
        objectType = "list",
        model = OpenAi.Models.TEXT_EMBEDDING_ADA_002,
        data = listOf(
            Embedding.Response.Datum(
                embedding = listOf(
                    -0.006929283495992422,
                    -0.005336422007530928,
                    -4.547132266452536e-05,
                    -0.024047505110502243
                ),
                index = 0,
                objectType = "embedding"
            )
        ),
        usage = OpenAi.Usage(
            promptTokens = 5,
            totalTokens = 5
        )
    )

    private val fullResponseString = """
            {
                "object": "list",
                "model": "${OpenAi.Models.TEXT_EMBEDDING_ADA_002}",
                "data": [
                    {
                        "embedding": [
                            -0.006929283495992422,
                            -0.005336422007530928,
                            -4.547132266452536e-05,
                            -0.024047505110502243
                        ],
                        "index": 0,
                        "object": "embedding"
                    }
                ],
                "usage": {
                    "prompt_tokens": 5,
                    "total_tokens": 5
                }
            }
            """.trimIndent()

    @Test
    fun `EmbeddingRequest deserializes minimum object`() = test {
        expect { minimumRequest }

        whenever {
            val request = """
            {
                "model": "${OpenAi.Models.TEXT_EMBEDDING_ADA_002}"
            }
            """.trimIndent()

            TestJson.decodeFromString(Embedding.Request.serializer(), request)
        }
    }

    @Test
    fun `EmbeddingRequest deserializes full object`() = test {
        expect { fullRequest }

        whenever {
            TestJson.decodeFromString(Embedding.Request.serializer(), fullRequestString)
        }
    }

    @Test
    fun `EmbeddingRequest serializes full object`() = test {
       expect { fullRequestString.trimWhiteSpaces() }

        whenever {
            TestJson.encodeToString(Embedding.Request.serializer(), fullRequest).trimWhiteSpaces()
        }
    }

    @Test
    fun `EmbeddingResponse deserializes minimum object`() = test {
        expect { Embedding.Response() }

        whenever {
            val response = """
            {
                "object": null,
                "model": null,
                "data": null
            }
            """.trimIndent()

            TestJson.decodeFromString(Embedding.Response.serializer(), response)
        }
    }

    @Test
    fun `EmbeddingResponse deserializes full object`() = test {
        expect { fullResponse }

        whenever {
            TestJson.decodeFromString(Embedding.Response.serializer(), fullResponseString)
        }
    }

    @Test
    fun `EmbeddingResponse serializes full object`() = test {
        expect {
            """
            {
                "objectType": "list",
                "model": "${OpenAi.Models.TEXT_EMBEDDING_ADA_002}",
                "data": [
                    {
                        "embedding": [
                            -0.006929283495992422,
                            -0.005336422007530928,
                            -4.547132266452536E-5,
                            -0.024047505110502243
                        ],
                        "index": 0,
                        "objectType": "embedding"
                    }
                ],
                "usage": {
                    "promptTokens": 5,
                    "completionTokens": null,
                    "totalTokens": 5
                }
            }
            """.trimWhiteSpaces() }

        whenever {
            TestJson.encodeToString(Embedding.Response.serializer(), fullResponse).trimWhiteSpaces()
        }
    }
}