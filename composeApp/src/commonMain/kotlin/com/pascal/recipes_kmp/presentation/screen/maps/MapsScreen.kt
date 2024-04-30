package com.pascal.recipes_kmp.presentation.screen.maps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.pascal.recipes_kmp.presentation.component.Maps
import com.pascal.recipes_kmp.presentation.component.MapsMarkerDialog
import org.koin.compose.koinInject

class MapsScreen() : Screen {
    @Composable
    override fun Content() {
        MapsContent()
    }
}

@Composable
fun MapsContent(
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.current
    val viewModel: MapsViewModel = koinInject<MapsViewModel>()

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Maps(
            modifier = Modifier,
            uiState = uiState,
            onMarkerClick = {},
            onPositionChange = {}
        )

        AnimatedVisibility(
            visible = uiState.selectedCinema != null,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            MapsMarkerDialog(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 18.dp)
                    .align(Alignment.TopCenter),
                title = uiState.selectedCinema?.name ?: "",
                subTitle = uiState.selectedCinema?.description ?: ""
            ) {
//                navigateToMap(
//                    context = platformContext,
//                    deviceLocation = uiState.selectedCinema?.location,
//                    destinationName = uiState.selectedCinema?.name ?: ""
//                )
            }
        }
    }
}