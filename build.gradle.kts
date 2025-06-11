plugins {
    java
}

allprojects {

    apply {
        plugin("java")
    }

    group = "me.fortibrine"
    version = "1.0"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")

        maven("https://repo.panda-lang.org/releases")
    }

    dependencies {
        val libs = rootProject.libs

        compileOnly(libs.lombok)
        annotationProcessor(libs.lombok)
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"

            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
        }

        processResources {
            filesMatching("plugin.yml") {
                expand(
                    "version" to rootProject.version,
                )
            }
        }
    }

}
