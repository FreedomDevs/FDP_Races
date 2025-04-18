plugins {
    java
    `maven-publish`
    kotlin("jvm")
}

group = "dev.fdp.races"
version = "0.5"

repositories {
    maven {
        name = "papermc"
         url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib-jdk8"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(mapOf("version" to version))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = "races-plugin"
            version = project.version.toString()
        }
    }
}
