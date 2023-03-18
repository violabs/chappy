package com.violabs.chappy.models

import com.violabs.chappy.JSON_MAPPER
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class ChatTests : Wesley() {

    private val roles = Chat.Message.Role.values().toList()

    @Test
    fun `Message#Role serializes correctly`() = test {
        expect {
            roles.map { "\"${it.toString().lowercase()}\"" }
        }

        whenever {
            roles.map { JSON_MAPPER.encodeToString(Chat.Message.Role.serializer(), it) }
        }
    }

    @Test
    fun `Message#Role deserializes correctly for uppercase`() = test {
        expect {
            roles
        }

        whenever {
            roles.decodeRoles(String::uppercase)
        }
    }

    @Test
    fun `Message#Role deserializes correctly for lowercase`() = test {
        expect {
            roles
        }

        whenever {
            roles.decodeRoles(String::lowercase)
        }
    }

    private fun List<Chat.Message.Role>.decodeRoles(stringTransform: (String) -> String): List<Chat.Message.Role> =
        this
            .asSequence()
            .map(Chat.Message.Role::toString)
            .map(stringTransform)
            .map { "\"$it\"" }
            .map { JSON_MAPPER.decodeFromString(Chat.Message.Role.serializer(), it) }
            .toList()


    @Test
    fun `Message serializes correctly`() = test {
        expect {
            """
                {
                    "content": "Hello",
                    "role": "user"
                }
            """.trimWhiteSpaces()
        }

        whenever {
            val message = Chat.Message("Hello")

            JSON_MAPPER.encodeToString(Chat.Message.serializer(), message)
        }
    }

    @Test
    fun `Message parses in correctly`() = test {
        expect {
            Chat.Message("Hello")
        }

        whenever {
            val message = """
                {
                    "content": "Hello",
                    "role": "user"
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(Chat.Message.serializer(), message)
        }
    }

    //
    @Test
    fun `Request deserializes correctly`() = test {
        expect {
            Chat.Request(
                messages = listOf(
                    Chat.Message("Hello")
                )
            )
        }

        whenever {
            val request = """
                {
                    "messages": [
                        {"content": "Hello", "role": "user"}
                    ],
                    "model": "gpt-3.5-turbo",
                    "temperature": 0.0,
                    "maxTokens": 2,
                    "topP": 1.0,
                    "numberOfChoices": null,
                    "stream": false
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(Chat.Request.serializer(), request)
        }
    }

    @Test
    fun `Request serializes correctly`() = test {
        expect {
            """
                {
                    "messages": [
                        {"content": "Hello", "role": "user"}
                    ],
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
            val request = Chat.Request(
                messages = listOf(
                    Chat.Message("Hello")
                )
            )

            JSON_MAPPER.encodeToString(Chat.Request.serializer(), request)
        }
    }

    @Test
    fun `Response deserializes correctly`() = test {
        expect {
            Chat.Response(
                "1",
                "testing",
                OpenAi.Models.CODE_DAVINCI_002,
                123455678,
                choices = listOf(
                    Chat.Choice(
                        1,
                        Chat.Message("Hello"),
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
                            "message": {
                                "content": "Hello",
                                "role": "user"
                            },
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

            JSON_MAPPER.decodeFromString(Chat.Response.serializer(), response)
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
                            "index": 1,
                            "message": {
                                "content": "Hello",
                                "role": "user"
                            },
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
            val response = Chat.Response(
                "1",
                "testing",
                OpenAi.Models.CODE_DAVINCI_002,
                123455678,
                choices = listOf(
                    Chat.Choice(
                        1,
                        Chat.Message("Hello"),
                        "testing"
                    )
                ),
                usage = OpenAi.Usage(1, 2, 3)
            )

            JSON_MAPPER.encodeToString(Chat.Response.serializer(), response)
        }
    }

    @Test
    fun `Choice deserializes correctly`() = test {
        expect {
            Chat.Choice(
                1,
                Chat.Message("Hello"),
                "testing"
            )
        }

        whenever {
            val choice = """
                {
                    "index": 1,
                    "message": {
                        "content": "Hello",
                        "role": "user"
                    },
                    "finish_reason": "testing"
                }
            """.trimWhiteSpaces()

            JSON_MAPPER.decodeFromString(Chat.Choice.serializer(), choice)
        }
    }

    @Test
    fun `Choice serializes correctly`() = test {
        expect {
            """
                {
                    "index": 1,
                    "message": {
                        "content": "Hello",
                        "role": "user"
                    },
                    "finishReason": "testing"
                }
            """.trimWhiteSpaces()
        }

        whenever {
            val choice = Chat.Choice(
                1,
                Chat.Message("Hello"),
                "testing"
            )

            JSON_MAPPER.encodeToString(Chat.Choice.serializer(), choice)
        }
    }
}