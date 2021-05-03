plugins {
    id("java-library")
    id("kotlin")
    kotlin("plugin.serialization") version "1.5.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    implementation(project(mapOf("path" to ":commons")))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")

    // Koin for Kotlin Multiplatform
    implementation("io.insert-koin:koin-core:3.0.1")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.0")

// Koin Test for Kotlin Multiplatform
//    testImplementation("io.insert-koin:koin-test:$koin_version")
}