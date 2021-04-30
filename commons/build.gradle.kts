plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
//    val koinVersion = "3.0.1"
//
//    // Koin for Kotlin Multiplatform
//    api("io.insert-koin:koin-core:$koinVersion")

// Koin Test for Kotlin Multiplatform
//    testImplementation "io.insert-koin:koin-test:$koin_version"
}