package com.violabs.chappy.config

import com.violabs.chappy.models.Edit
import com.violabs.chappy.models.OpenAi
import com.violabs.chappy.trimWhiteSpaces
import com.violabs.wesly.Wesley
import org.junit.jupiter.api.Test

class EditRequestSerializerTests : Wesley() {

    private val minimumRequest = Edit.Request(model = OpenAi.Models.CODE_DAVINCI_EDIT_001)

    private val fullRequest = Edit.Request(
        input = "import numpy as np\n\nx = np.array([1, 2, 3])\nprint(x)",
        model = OpenAi.Models.CODE_DAVINCI_EDIT_001,
        instruction = "Add a line to square the values in the array.",
        maxTokens = 10,
        numberOfChoices = 3,
        temperature = 0.5,
        topP = 0.8,
        suffix = "\nprint(x_squared)",
        stream = true
    )

    private val fullRequestString = """
        {
            "input": "import numpy as np\n\nx = np.array([1, 2, 3])\nprint(x)",
            "model": "${OpenAi.Models.CODE_DAVINCI_EDIT_001}",
            "instruction": "Add a line to square the values in the array.",
            "max_tokens": 10,
            "n": 3,
            "temperature": 0.5,
            "top_p": 0.8,
            "suffix": "\nprint(x_squared)",
            "stream": true
        }
    """.trimIndent()

    @Test
    fun `EditRequest deserializes minimum object`() = test {
        expect { minimumRequest }

        whenever {
            val request = """
            {
                "model": "${OpenAi.Models.CODE_DAVINCI_EDIT_001}"
            }
            """.trimIndent()

            TestJson.decodeFromString(OpenAiRequestSerializer(), request)
        }
    }

    @Test
    fun `EditRequest deserializes full object`() = test {
        expect { fullRequest }

        whenever {
            TestJson.decodeFromString(OpenAiRequestSerializer(), fullRequestString)
        }
    }

    @Test
    fun `EditRequest serializes minimum object`() = test {
        expect {
            """
            {
                "input": null,
                "model": "${OpenAi.Models.CODE_DAVINCI_EDIT_001}",
                "instruction": null,
                "max_tokens": 2,
                "n": null,
                "temperature": 1.0,
                "top_p": 1.0,
                "suffix": null,
                "stream": false
            }
            """.trimWhiteSpaces()
        }

        whenever {
            val request = Edit.Request(model = OpenAi.Models.CODE_DAVINCI_EDIT_001)

            TestJson.encodeToString(OpenAiRequestSerializer(), request)
        }
    }

    @Test
    fun `EditRequest serializes full object`() = test {
        expect {
            fullRequestString.trimWhiteSpaces()
        }

        whenever {
            TestJson.encodeToString(OpenAiRequestSerializer(), fullRequest).trimWhiteSpaces()
        }
    }
}
