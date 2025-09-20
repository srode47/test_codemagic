import java.util.Properties
import java.nio.charset.StandardCharsets
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.reader(StandardCharsets.UTF_8).use { reader ->
            load(reader)
        }
    }
}

val flutterVersionCode = localProperties.getProperty("flutter.versionCode") ?: "1"
val flutterVersionName = localProperties.getProperty("flutter.versionName") ?: "1.0"

val keystorePropertiesFile = rootProject.file("key.properties")
val keystoreProperties = Properties().apply {
    if (keystorePropertiesFile.exists()) {
        FileInputStream(keystorePropertiesFile).use { stream ->
            load(stream)
        }
    }
}

val env = Properties().apply {
    val envFile = rootProject.file(".env")
    if (envFile.exists()) {
        envFile.inputStream().use { stream ->
            load(stream)
        }
    }
}

kotlin {
    jvmToolchain(21)
}

android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.lumziorbit.codemagic"

    flavorDimensions.add("flavor-type")

    productFlavors {
        create("prod") {
            dimension = "flavor-type"
            applicationId = "com.lumziorbit.codemagic"
            resValue("string", "app_name", "Codemagic")
        }
        create("dev") {
            dimension = "flavor-type"
            applicationId = "com.lumziorbit.codemagic"
            resValue("string", "app_name", "Codemagic Dev")
        }
    }

    compileSdk = 36

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
    }

    lint {
        disable.add("InvalidPackage")
        checkReleaseBuilds = false
    }

    defaultConfig {
        applicationId = "com.lumziorbit.codemagic"
        minSdk = 24
        targetSdk = 35
        versionCode = flutterVersionCode.toInt()
        versionName = flutterVersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    signingConfigs {
        create("release") {
            if (System.getenv("CI") == "true") {
                storeFile = file(System.getenv("CM_KEYSTORE_PATH") ?: "")
                storePassword = System.getenv("CM_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("CM_KEY_ALIAS")
                keyPassword = System.getenv("CM_KEY_PASSWORD")
            } else {
                keyAlias = keystoreProperties["keyAlias"]?.toString()
                keyPassword = keystoreProperties["keyPassword"]?.toString()
                storeFile = keystoreProperties["storeFile"]?.let { file(it) }
                storePassword = keystoreProperties["storePassword"]?.toString()
            }
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs["release"]
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isMinifyEnabled = true
            isShrinkResources = true
        }
        getByName("debug") {
            signingConfig = signingConfigs["debug"]
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isMinifyEnabled = false
        }
    }

    ndkVersion = System.getenv("NDK_VERSION") ?: "28.2.13676358"
}

flutter {
    source = "../.."
}

dependencies {
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-alpha3")
}
