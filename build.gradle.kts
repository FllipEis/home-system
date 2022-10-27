plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

allprojects {
    group = "de.fllip"
    version = "1.0-SNAPSHOT"

    apply {
        plugin("java")
        plugin("com.github.johnrengelman.shadow")
    }

    repositories {
        mavenCentral()
    }

    tasks {
        named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
            archiveBaseName.set(project.name)
            mergeServiceFiles()
            minimize()
        }

        test { useJUnitPlatform() }

        build { dependsOn(shadowJar) }

    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}

subprojects {
    dependencies {
        implementation("com.google.code.gson:gson:2.10")
    }

    tasks {
        named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
            //Relocate because you need gson version 2.10 to serialize records
            relocate("com.google.gson", "de.fllip.gson")
        }
    }
}