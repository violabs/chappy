plugins {
    kotlin("multiplatform") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.10"
    id("maven-publish")
}

group = "com.violabs"
version = "1.1.8"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("com.github.violabs:wesley:1.1.2")
            }
        }
        val jsMain by getting
        val jsTest by getting
    }
}
