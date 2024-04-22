package com.pascal.recipes_kmp

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pascal.recipes_kmp.db.RecipesDatabase
import com.pascal.recipes_kmp.di.appModule
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@AndroidApp)
            androidLogger()
            modules(appModule)
        }
    }
}

class AppActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

internal actual fun openUrl(url: String?) {
    val uri = url?.let { Uri.parse(it) } ?: return
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AndroidApp.INSTANCE.startActivity(intent)
}

@Single
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory actual constructor() {
    private var context: Context = AndroidApp.INSTANCE.applicationContext
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RecipesDatabase.Schema, context, "RecipesDatabase.db")
    }
}

actual fun createSettings(): Settings {
    val context: Context = AndroidApp.INSTANCE.applicationContext
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    return SharedPreferencesSettings(preferences)
}