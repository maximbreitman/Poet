plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.1" apply true
}

group = "breitman"
version = "0.2"

repositories {
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

tasks {
    assemble {
        dependsOn(named("reobfJar"))
    }
}


dependencies {
    implementation(project(":poet-api"))
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    paperweight.paperDevBundle("1.19.3-R0.1-SNAPSHOT")
}