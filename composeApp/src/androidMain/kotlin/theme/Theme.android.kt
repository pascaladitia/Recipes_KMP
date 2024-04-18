package theme

import android.app.Activity
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat

@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    val view = LocalView.current
    val systemBarColor = Color.TRANSPARENT
    LaunchedEffect(isDark) {
        val window = (view.context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = systemBarColor
        window.navigationBarColor = systemBarColor
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = isDark
            isAppearanceLightNavigationBars = isDark
        }
    }
}

@Composable
actual fun font(
    name: String,
    res: String,
    weight: FontWeight,
    style: FontStyle
): Font {
    val context = LocalContext.current
    val id = context.resources.getIdentifier(res, "font", context.packageName)
    return Font(id, weight, style)
}