plugins {
    `java-library`
    `maven-publish`
}

group = "breitman"
version = "0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    compileOnly("io.netty:netty-all:4.1.87.Final")

    compileOnly("net.kyori:adventure-api:4.12.0")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.12.0")

    compileOnly("org.jetbrains:annotations:24.0.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}