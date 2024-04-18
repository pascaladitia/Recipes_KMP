package presentation.screen.bottom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.contentColorFor
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import presentation.navigation.tabs.FavoriteTab
import presentation.navigation.tabs.HomeTab
import presentation.navigation.tabs.ProfileTab
import theme.LocalThemeIsDark

class BottomScreen() : Screen {
    @Composable
    override fun Content() {
        BottomNav()
    }
}

@Composable
fun BottomNav() {
    var isVisible by remember { mutableStateOf(true) }
    val homeTab = remember { HomeTab { isVisible = it } }
    val favoriteTab = remember { FavoriteTab { isVisible = it } }
    val profileTab = remember { ProfileTab { isVisible = it } }

    TabNavigator(
        homeTab,
    ) {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically { height -> height },
                    exit = slideOutVertically { height -> height }
                ) {
                    BottomNavigation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .windowInsetsPadding(WindowInsets.ime),
                        backgroundColor = Color.White,
                        contentColor = contentColorFor(MaterialTheme.colorScheme.primary),
                        elevation = 8.dp
                    ) {
                        TabItem(homeTab)
                        TabItem(favoriteTab)
                        TabItem(profileTab)
                    }
                }
            }
        ) {
            CurrentTab()
        }
    }
}

@Composable
fun RowScope.TabItem(tab: Tab) {

    val isDark by LocalThemeIsDark.current
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            .height(58.dp).clip(RoundedCornerShape(16.dp)),
        selected = tabNavigator.current == tab,
        onClick = {
            tabNavigator.current = tab
        },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(
                    painter,
                    contentDescription = tab.options.title,
                    tint = if (tabNavigator.current == tab) MaterialTheme.colorScheme.primary else if (isDark) Color.White else Color.Gray
                )
            }
        },
        label = {
            tab.options.title.let { title ->
                Text(
                    title,
                    fontSize = 12.sp,
                    color = if (tabNavigator.current == tab) MaterialTheme.colorScheme.primary else if (isDark) Color.White else Color.Gray
                )
            }
        },
        interactionSource = MutableInteractionSource()
    )
}