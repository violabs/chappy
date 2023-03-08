package com.violabs.chappy.models

import com.violabs.chappy.JSON_MAPPER
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class OpenAiTests : Wesley() {
    @Test
    fun `Usage deserializes correctly`() = test {
        expect {
            OpenAi.Usage(1, 2, 3)
        }

        whenever {
            val usage = """
                {
                    "prompt_tokens": 1,
                    "completion_tokens": 2,
                    "total_tokens": 3
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(OpenAi.Usage.serializer(), usage)
        }
    }

    @Test
    fun `Usage serializes correctly`() = test {
        expect {
            """
            {
                "promptTokens": 1,
                "completionTokens": 2,
                "totalTokens": 3
            }
        """.trimWhiteSpaces()
        }

        whenever {
            val usage = OpenAi.Usage(1, 2, 3)

            JSON_MAPPER.encodeToString(OpenAi.Usage.serializer(), usage)
        }
    }
}