dependencies {
    compileOnly(libs.spigot)

    implementation(libs.litecommands.bukkit)
    implementation(libs.sqlite)
    implementation(libs.ormlite)
}

tasks {
    jar {
        archiveBaseName.set(rootProject.name)

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from (
            configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
        )
    }

}
