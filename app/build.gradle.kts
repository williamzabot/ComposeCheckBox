plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.composenavkoin"
    compileSdk = COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.example.composenavkoin"
        minSdk = 24
        targetSdk = COMPILE_SDK_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.compose.ui:ui:${Versions.COMPOSE_VERSION}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_VERSION}")
    implementation("androidx.navigation:navigation-compose:${Versions.NAVIGATION_COMPOSE_VERSION}")
    implementation("androidx.compose.material:material:1.4.0")
    implementation("io.insert-koin:koin-androidx-compose:${Versions.KOIN_COMPOSE_VERSION}")
    implementation("io.insert-koin:koin-core:${Versions.KOIN_VERSION}")
    implementation("io.insert-koin:koin-android:${Versions.KOIN_VERSION}")
    implementation("com.google.code.gson:gson:${Versions.GSON}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE_VERSION}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.COMPOSE_VERSION}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE_VERSION}")

}