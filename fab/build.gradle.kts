plugins {
  id("com.android.library")
  kotlin("android")
  id("com.vanniktech.maven.publish")
}

android {
  compileSdk = 31
  defaultConfig {
    minSdk = 17
    targetSdk = 31
    vectorDrawables.useSupportLibrary = true
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
}

allprojects {
  plugins.withId("com.vanniktech.maven.publish") {
    mavenPublish {
      sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
    }
  }
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation("androidx.appcompat:appcompat:1.3.1")
  implementation("com.google.android.material:material:1.4.0")
}
