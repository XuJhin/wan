import java.io.FileInputStream
import java.util.*

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.plugin)
}

// 从 gradle.properties 文件中读取签名数据
val props = Properties()
FileInputStream("gradle.properties").use { props.load(it) }

//版本号
val appVersionCode: String by project
val appVersionName: String by project //

android {
    namespace = "com.dingstock.wancompose"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.dingstock.wancompose"
        minSdk = 24
        targetSdk = 34
        versionCode = appVersionCode.toInt()
        versionName = appVersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file(props.getProperty("key.store.file"))
            storePassword = props.getProperty("key.store.password")
            keyAlias = props.getProperty("key.alias")
            keyPassword = props.getProperty("key.password")
        }
        getByName("debug") {
            storeFile = file(props.getProperty("key.store.file"))
            storePassword = props.getProperty("key.store.password")
            keyAlias = props.getProperty("key.alias")
            keyPassword = props.getProperty("key.password")
        }
    }
    buildTypes {
        release {
            isDebuggable = false
            signingConfig = signingConfigs["release"]
        }
        debug {
            isDebuggable = true
            signingConfig = signingConfigs["debug"]
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    composeCompiler {
        enableStrongSkippingMode = true
    }
    hilt {
        enableExperimentalClasspathAggregation = true
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(project(":core-net"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.foundation:foundation:1.6.8")
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation("io.coil-kt:coil-compose:2.6.0")
    val paging_version = "3.3.0"
    implementation("androidx.paging:paging-runtime:$paging_version")

    // alternatively - without Android dependencies for tests
    testImplementation("androidx.paging:paging-common:$paging_version")

    // optional - RxJava2 support
    implementation("androidx.paging:paging-rxjava2:$paging_version")

    // optional - RxJava3 support
    implementation("androidx.paging:paging-rxjava3:$paging_version")

    // optional - Guava ListenableFuture support
    implementation("androidx.paging:paging-guava:$paging_version")

    // optional - Jetpack Compose integration
    implementation("androidx.paging:paging-compose:3.2.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
}