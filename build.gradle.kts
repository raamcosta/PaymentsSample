buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha15")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")

        val navVersion = "2.3.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}