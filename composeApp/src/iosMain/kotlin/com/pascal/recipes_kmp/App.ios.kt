package com.pascal.recipes_kmp

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.pascal.recipes_kmp.db.RecipesDatabase
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual fun openUrl(url: String?) {
    val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}

actual class DriverFactory actual constructor(){
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RecipesDatabase.Schema,"RecipesDatabase.db")
    }
}