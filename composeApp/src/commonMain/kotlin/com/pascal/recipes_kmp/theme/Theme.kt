package com.pascal.recipes_kmp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import recipes_app_kmp_.composeapp.generated.resources.IndieFlower_Regular
import recipes_app_kmp_.composeapp.generated.resources.Res
import recipes_app_kmp_.composeapp.generated.resources.varela_round

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    secondaryContainer = Navy200,
    onSecondaryContainer = Navy500,
    onSurface = Navy200,
    onSurfaceVariant = Navy200,
)

private val LightColorScheme = lightColorScheme(
    primary = Navy700,
    secondary = PurpleGrey40,
    tertiary = Navy500,
    secondaryContainer = Navy700,
    onSecondaryContainer = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Black,
    background = Navy200
)

@Composable
fun getTypography(): Typography {
    val varela_round = FontFamily(Font(Res.font.varela_round))

    return  Typography(
        headlineLarge = TextStyle(
            fontFamily = varela_round,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = varela_round,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = varela_round,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = varela_round,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = varela_round,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        bodySmall = TextStyle(
            fontFamily = varela_round,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    )
}

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal fun AppTheme(
    content: @Composable() () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        MaterialTheme(
            colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
            typography = getTypography(),
            content = { Surface(content = content) }
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
