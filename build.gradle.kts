plugins {
    `java-library`
    `maven-publish`
    id ("com.github.johnrengelman.shadow") version "8.1.0"
}

group = "breitman"
version = "0.2"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    compileOnlyApi("io.netty:netty-all:4.1.87.Final")

    compileOnlyApi("net.kyori:adventure-api:4.12.0")
    compileOnlyApi("net.kyori:adventure-text-serializer-gson:4.12.0")

    compileOnlyApi("org.jetbrains:annotations:24.0.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")
    dependencies {
        compileOnly(rootProject)
    }
}