val implementation: Unit = Unit

plugins {
    id("com.android.application")
}

android {
    namespace = "com.s23010167.tailorease"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.s23010167.tailorease"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.android.gms:play-services-maps:19.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Duplicate maps dependency, can be removed (18.2.0 version)
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // Google Play Services location APIs for accessing device location
    implementation("com.google.android.gms:play-services-location:21.0.1")
    // Optional Google Maps HTTP client (for backend requests to Maps API)
    implementation("com.google.maps:google-maps-services:0.18.0")

    // Biometric authentication library for fingerprint/face login
    implementation("androidx.biometric:biometric:1.1.0")


}