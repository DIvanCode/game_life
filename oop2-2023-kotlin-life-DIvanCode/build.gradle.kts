
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"
val coroutines_version = "1.6.4"
val retrofit_version = "2.9.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("org.jetbrains.kotlin:kotlin-reflect")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutines_version")
                implementation("ch.qos.logback:logback-classic:1.4.5")
                implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
                implementation("com.squareup.retrofit2:retrofit-mock:$retrofit_version")
                implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
                implementation("com.squareup.okhttp3:okhttp:4.10.0")
                implementation("io.reactivex.rxjava2:rxjava:2.2.21")
                implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
                implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofit_version")
                implementation("junit:junit:4.13.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "oop2-2023-kotlin-life-DIvanCode"
            packageVersion = "1.0.0"
        }
    }
}