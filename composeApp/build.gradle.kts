import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.buildConfig)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.android)
            implementation(libs.androidx.preference.ktx)
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.1"))
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-android")
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqlDelight.driver.native)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.io.ktor.ktor.client.serialization)
            implementation(libs.kermit)
            implementation(libs.moko.mvvm)
            implementation(libs.moko.permission)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tab.navigator)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kamel.image)
            implementation(libs.sqlDelight.extension)
            implementation(libs.paging.compose.common)
            implementation(libs.paging.common)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.serialization)
            implementation(libs.peekaboo.ui)
            implementation(libs.peekaboo.image.picker)
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.4"))
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-compose")
            implementation("io.insert-koin:koin-annotations:1.3.1")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(kotlin("test-annotations-common"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.kotest.property)
            implementation(libs.ktor.client.mock)
        }
    }
}

android {
    namespace = "com.pascal.recipes_kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].res.srcDirs("src/commonMain/resources", "src/androidMain/res")

    defaultConfig {
        applicationId = "com.pascal.recipes_kmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
buildConfig {
    val propertiesFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propertiesFile))
    buildConfigField("String", "BASE_URL", "\"" + "https://www.themealdb.com/api/json/v1/1" + "\"")
}
sqldelight {
    databases {
        create("RecipesDatabase") {
            packageName.set("com.recipes.db")
        }
    }
}


