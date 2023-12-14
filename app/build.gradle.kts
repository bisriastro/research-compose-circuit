@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.anvil)
    alias(libs.plugins.kotlin.kapt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.bisri.id.research.compose.circuit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bisri.id.research.compose.circuit"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.version.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.slack.circuit:circuitx-android:0.17.1")
    implementation("com.slack.circuit:circuit-foundation:0.17.1")
    implementation("com.slack.circuit:circuit-backstack:0.17.1")
    implementation("com.slack.circuit:circuit-runtime:0.17.1")
    implementation("com.slack.circuit:circuit-runtime-presenter:0.17.1")
    implementation("com.slack.circuit:circuit-runtime-ui:0.17.1")
    implementation("com.slack.circuit:circuit-test:0.17.1")
    implementation("com.slack.circuit:circuit-overlay:0.17.1")
    implementation("com.slack.circuit:circuit-retained:0.17.1")

    api("com.slack.circuit:circuit-codegen-annotations:0.17.1")
    ksp("com.slack.circuit:circuit-codegen:0.17.1")

    kapt(libs.dagger.compiler)
    implementation(libs.dagger)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}