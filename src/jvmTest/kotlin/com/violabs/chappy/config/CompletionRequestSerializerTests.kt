package com.violabs.chappy.config

import com.violabs.chappy.models.Completion
import com.violabs.chappy.models.OpenAi
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class CompletionRequestSerializerTests : Wesley() {

    private val minimumRequest = Completion.Request(model = OpenAi.Models.CODE_DAVINCI_002)

    private val fullRequest = Completion.Request(
        prompt = "for i in range(10):",
        model = OpenAi.Models.CODE_DAVINCI_002,
        temperature = 0.5,
        maxTokens = 10,
        topP = 0.8,
        numberOfChoices = 3,
        stream = true,
        logprobs = 2,
        stop = "\n"
    )

    val fullRequestString = """
        {
            "prompt": "for i in range(10):",
            "model": "${OpenAi.Models.CODE_DAVINCI_002}",
            "temperature": 0.5,
            "max_tokens": 10,
            "top_p": 0.8,
            "n": 3,
            "stream": true,
            "logprobs": 2,
            "stop": "\n"
        }
    """.trimIndent()

    @Test
    fun `CompletionRequest deserializes minimum object`() = test {
        expect { minimumRequest }

        whenever {
            val request = """
            {
                "model": "${OpenAi.Models.CODE_DAVINCI_002}"
            }
            """.trimIndent()

            TestJson.decodeFromString(OpenAiRequestSerializer(), request)
        }
    }

    @Test
    fun `ChatRequest deserializes full object`() = test {
        expect { fullRequest }

        whenever {
            TestJson.decodeFromString(OpenAiRequestSerializer(), fullRequestString)
        }
    }

    @Test
    fun `ChatRequest serializes minimum object`() = test {
        expect {
            """
            {
                "prompt": null,
                "model": "${OpenAi.Models.CODE_DAVINCI_002}",
                "temperature": 0.0,
                "max_tokens": 2,
                "top_p": 1.0,
                "n": null,
                "stream": false,
                "logprobs": null,
                "stop": null
            }
            """.trimWhiteSpaces()
        }

        whenever {
            val request = Completion.Request(model = OpenAi.Models.CODE_DAVINCI_002)

            TestJson.encodeToString(OpenAiRequestSerializer(), request)
        }
    }

    @Test
    fun `ChatRequest serializes full object`() = test {
        expect {
            fullRequestString.trimWhiteSpaces()
        }

        whenever {
            TestJson.encodeToString(OpenAiRequestSerializer(), fullRequest).trimWhiteSpaces()
        }
    }
}
