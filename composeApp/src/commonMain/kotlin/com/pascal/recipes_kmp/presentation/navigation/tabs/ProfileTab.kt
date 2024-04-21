package com.pascal.recipes_kmp.presentation.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.pascal.recipes_kmp.presentation.screen.profile.ProfileScreen
import kotlin.jvm.Transient

class ProfileTab(
    @Transient
    val onNavigator : (isRoot : Boolean) -> Unit,
) : Tab {
    @Composable
    override fun Content() {
        Navigator(ProfileScreen()) { navigator ->
            LaunchedEffect(navigator.lastItem){
                onNavigator(navigator.lastItem is ProfileScreen)
            }
            SlideTransition(navigator = navigator)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Outlined.Person)
            val title = "Profile"
            val index: UShort = 0u

            return TabOptions(
                index, title, icon
            )
        }
}