package com.violabs.chappy.config

import com.violabs.chappy.models.Chat
import com.violabs.chappy.models.OpenAi
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class ChatChatRequestSerializerTests : Wesley() {

    private val minimumChatRequest = Chat.Request(model = OpenAi.Models.GPT_3_5_TURBO)

    private val fullChatRequest = Chat.Request(
        messages = listOf(
            Chat.Message("Hello", Chat.Message.Role.USER)
        ),
        model = "gpt-3.5-turbo",
        temperature = 0.5,
        maxTokens = 10,
        topP = 0.8,
        numberOfChoices = 3,
        stream = true
    )

    private val fullChatRequestString = """
        {
            "messages": [
                {
                    "content": "Hello",
                    "role": "user"
                }
            ],
            "model": "gpt-3.5-turbo",
            "temperature": 0.5,
            "max_tokens": 10,
            "top_p": 0.8,
            "n": 3,
            "stream": true
        }
    """.trimIndent()

    @Test
    fun `ChatRequest deserializes minimum object`() = test {
        expect { minimumChatRequest }

        whenever {
            val request = """
            {
                "model": "gpt-3.5-turbo"
            }
            """.trimIndent()

            TestJson.decodeFromString(OpenAiRequestSerializer(), request)
        }
    }

    @Test
    fun `ChatRequest deserializes full object`() = test {
        expect { fullChatRequest }

        whenever {
            TestJson.decodeFromString(OpenAiRequestSerializer(), fullChatRequestString)
        }
    }

    @Test
    fun `ChatRequest serializes minimum object`() = test {
        expect {
            """
            {
                "messages": null,
                "model": "gpt-3.5-turbo",
                "temperature": 0.0,
                "max_tokens": 2,
                "top_p": 1.0,
                "n": null,
                "stream": false
            }
            """.trimWhiteSpaces()
        }

        whenever {
            val request = Chat.Request()

            TestJson.encodeToString(OpenAiRequestSerializer(), request)
        }
    }

    @Test
    fun `ChatRequest serializes full object`() = test {
        expect {
            fullChatRequestString.trimWhiteSpaces()
        }

        whenever {
            TestJson.encodeToString(OpenAiRequestSerializer(), fullChatRequest).trimWhiteSpaces()
        }
    }
}
