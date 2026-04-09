import com.modrinth.minotaur.dependencies.DependencyType
import com.modrinth.minotaur.dependencies.ModDependency

plugins {
    java
    alias(libs.plugins.minotaur)
}

group = "io.github.lumine1909"
version = "1.2.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    compileOnly(libs.spigot.api)
    compileOnly(libs.packetevents)
    compileOnly(libs.luckperms)
}

modrinth {
    token.set(project.findProperty("modrinthKey") as? String ?: "")
    projectId.set("f3f4perms")
    versionNumber.set(version as String)
    versionName.set("F3F4Perms $version")
    versionType.set("release")
    uploadFile.set(tasks.jar)
    loaders.addAll("bukkit", "spigot", "paper", "purpur", "folia")
    dependencies.addAll(
        ModDependency("packetevents", DependencyType.REQUIRED),
        ModDependency("luckperms", DependencyType.REQUIRED),
    )

    gameVersions.addAll(generateVersions("1.16", 0, 5))
    gameVersions.addAll(generateVersions("1.17", 0, 1))
    gameVersions.addAll(generateVersions("1.18", 0, 2))
    gameVersions.addAll(generateVersions("1.19", 0, 4))
    gameVersions.addAll(generateVersions("1.20", 0, 6))
    gameVersions.addAll(generateVersions("1.21", 0, 11))
    gameVersions.addAll(generateVersions("26.1", 0, 1))
}

fun generateVersions(mm: String, start: Int, end: Int): List<String> = (start..end).map { if (it == 0) mm else "$mm.$it" }


java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8