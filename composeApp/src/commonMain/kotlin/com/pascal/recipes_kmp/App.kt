package com.pascal.recipes_kmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.cash.sqldelight.db.SqlDriver
import cafe.adriel.voyager.core.lifecycle.ScreenDisposable
import cafe.adriel.voyager.core.lifecycle.ScreenLifecycleStore
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.pascal.recipes_kmp.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.annotation.Single
import com.pascal.recipes_kmp.presentation.screen.splash.SplashScreen

@Composable
@Preview
fun App() = AppTheme {
    Navigator(SplashScreen()) { navigator ->
        remember(navigator.lastItem) {
            ScreenLifecycleStore.get(navigator.lastItem) {
                MyScreenLifecycleOwner()
            }
        }
        CurrentScreen()
    }
}

class MyScreenLifecycleOwner : ScreenDisposable {
    override fun onDispose(screen: Screen) {
        println("My ${screen.key} is being disposed")
    }
}

//@Single
//@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
//expect class DriverFactory() {
//    fun createDriver(): SqlDriver
//}

internal expect fun openUrl(url: String?)