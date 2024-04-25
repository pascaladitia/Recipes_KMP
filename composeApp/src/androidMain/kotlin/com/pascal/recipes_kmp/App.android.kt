package com.pascal.recipes_kmp

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
import com.pascal.recipes_kmp.di.initKoin
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.Single
import org.koin.core.logger.Level

class AppActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ContextUtils.setContext(context = this)
        initKoin {
            androidLogger(level = Level.NONE)
            androidContext(androidContext = this@AppActivity)
        }
        setContent { App() }
    }
}

@Single
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory actual constructor() {
    private var context: Context = ContextUtils.context
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RecipesDatabase.Schema, context, "RecipesDatabase.db")
    }
}

actual fun createSettings(): Settings {
    val context: Context = ContextUtils.context
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    return SharedPreferencesSettings(preferences)
}

internal actual fun openUrl(url: String?) {
    val uri = url?.let { Uri.parse(it) } ?: return
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    ContextUtils.context.startActivity(intent)
}