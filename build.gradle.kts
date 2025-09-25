plugins {
    kotlin("jvm") version "2.1.21" // ✅ match Quarkus supported version
    id("io.quarkus") version "3.15.1"
}

group = "org.sse.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

/*java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}*/

dependencies {
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:3.15.1"))
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-kotlin")
}

kotlin {
    jvmToolchain(21)
}
