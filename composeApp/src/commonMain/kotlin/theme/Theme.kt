package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    background = Navy200,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun getTypography(): Typography {
    val varela_round = FontFamily(
        font(
            name = "Varela Round",
            res = "varela_round",
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        )
    )

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
            shapes = AppShapes,
            content = {
                Surface(content = content)
            }
        )
    }
}

@Composable
expect fun font(
    name: String,
    res: String,
    weight: FontWeight,
    style: FontStyle
): Font

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
