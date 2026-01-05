plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.madhukar.webcompiler"
    compileSdk = 34   // ⚠️ 36 abhi unstable hai, 34 use karo

    defaultConfig {
        applicationId = "com.madhukar.webcompiler"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    // XML based UI
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Core Android
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")

    // 🔥 REQUIRED for Android 12 SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // AppCompat (needed for AppCompatActivity)
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Material Components (Material3 compatible)
    implementation("com.google.android.material:material:1.11.0")

    // Activity
    implementation("androidx.activity:activity-ktx:1.9.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
