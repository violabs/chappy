# Chappy

Chappy is a small Kotlin library that provides simple conversion objects for Open AI API while utilizing the [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) library. 
It allows for snake case to come in from Open AI API and go out to Open AI, but does not affect any other use.

Please note that this library is in constant development and should be used with caution.

## Features
- Simple conversion objects for Open AI API
- Uses kotlinx.serialization for serialization and deserialization
- Handles snake case API responses
- Easy to use and integrate with Kotlin projects

## Requirements
- Kotlin 1.8.0 or higher

## Installation

You can install Chappy via Gradle or Maven by adding the following dependency:

Gradle:

```kotlin
repositories {
  mavenCentral()
  maven(url = "https://jitpack.io")
}

implementation('com.github.violabs:chappy:<version>')
```

## Usage

You can utilize the `Request` to send to Open AI and you may use the `Response ` to encapsulate it.

### Completion

#### Request

```kotlin
Completion.Request(
    prompt = "test",
    model = "text-davinci-003",
    temperature = 0.0,
    maxTokens = 7,
    topP = 1.0,
    numberOfChoices = 1,
    stream = false,
    stop = "\n"
)
```

#### Response

```kotlin
Completion.Response(
    id = "1",
    objectType = "testing",
    model = "text-davinci-003",
    created = 123455678,
    choices = setOf(
        Completion.Choice(
            text = "Hello",
            index = 1,
            logprobs = null,
            finishResponse = "limit"
        )
    ),
    usage = OpenAi.Usage(
        promptTokens = 1, 
        completionTokens = 2, 
        totalTokens = 3
    )
)
```

### Chat

#### Request

```kotlin
Chat.Request(
    messages = listOf(
        Chat.Message("Hello")
    )
)
```

#### Response

```kotlin
Chat.Response(
    id = "1",
    objectType = "testing",
    model = "gpt-3.5-turbo",
    created = 123455678,
    choices = setOf(
        Chat.Choice(
            index = 1,
            message = Chat.Message("Hello", Chat.Role.ASSISTANT),
            finishResponse = "limit"
        )
    ),
    usage = usage = OpenAi.Usage(
        promptTokens = 1,
        completionTokens = 2,
        totalTokens = 3
    )
)
```

## Development
Chappy is currently in early development and is subject to change. 
We welcome contributions from the community and encourage you to open
an issue or pull request on our GitHub repository if you encounter any 
issues or have any feedback.

## License
Chappy is released under the MIT License.