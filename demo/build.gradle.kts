plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdkVersion (31)
    defaultConfig {
        applicationId = "com.robertlevonyan.views.customfloatingactionbuttonsample"
        minSdkVersion (25)
        targetSdkVersion (31)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility =JavaVersion.VERSION_1_8
        targetCompatibility =JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation ("androidx.appcompat:appcompat:1.3.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("androidx.core:core-ktx:1.6.0")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("com.robertlevonyan.view:CustomFloatingActionButton:3.1.1")
}
