plugins {
    id("java-library")
    id("kotlin")
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
// Koin Test for Kotlin Multiplatform
//    testImplementation("io.insert-koin:koin-test:$koin_version")
}