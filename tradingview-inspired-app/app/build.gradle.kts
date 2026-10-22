plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.example.tradinglite"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.tradinglite"
        minSdk = 24   // TradingView v3 minSdk 24 ku match
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-inspired"
    }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.10" }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}
dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.room:room-ktx:2.6.1") // like TradingView
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.dagger:hilt-android:2.48")
}
