package com.pascal.recipes_kmp.presentation.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.pascal.recipes_kmp.domain.usecases.UiState
import com.pascal.recipes_kmp.openUrl
import com.pascal.recipes_kmp.presentation.component.ErrorScreen
import com.pascal.recipes_kmp.presentation.component.IconCircleBorder
import com.pascal.recipes_kmp.presentation.component.LoadingScreen
import com.pascal.recipes_kmp.presentation.component.NetworkImage
import com.pascal.recipes_kmp.presentation.screen.detail.DetailScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import recipes_kmp.composeapp.generated.resources.Res
import recipes_kmp.composeapp.generated.resources.favorite_empty
import recipes_kmp.composeapp.generated.resources.food_recipes
import recipes_kmp.composeapp.generated.resources.no_tags
import sqldelight.db.FavoriteEntity

class FavoriteScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val coroutineScope = rememberCoroutineScope()
        val viewModel: FavoriteViewModel = koinInject<FavoriteViewModel>()
        var isContentVisible by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = true) {
            viewModel.loadListFavorite()
        }

        val uiState by viewModel.favorite.collectAsState()

        Surface(
            modifier = Modifier.padding(PaddingValues(vertical = 48.dp)),
            color = MaterialTheme.colorScheme.background
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    LoadingScreen()
                }
                is UiState.Error -> {
                    val message = (uiState as UiState.Error).message
                    ErrorScreen(message = message) {}
                }
                is UiState.Empty -> {
                    ErrorScreen(message = stringResource(Res.string.favorite_empty)) {}
                }
                is UiState.Success -> {
                    val data = (uiState as UiState.Success).data
                    FavoriteContent(
                        listFavorite = data,
                        onDetailClick = {
                            navigator?.push(DetailScreen(it.toString()))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    listFavorite: List<FavoriteEntity?>?,
    onDetailClick: (Long?) -> Unit
) {
    Column(
        modifier = modifier.padding(top = 32.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 24.dp, bottom = 16.dp),
            text = "Your Favorite",
            style = MaterialTheme.typography.headlineLarge
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(130.dp),
            state = rememberLazyGridState(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            items(listFavorite!!.size) { index ->
                listFavorite[index]?.let { item ->
                    FavoriteItem(
                        item = item,
                        onDetailClick = {
                            onDetailClick(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    item: FavoriteEntity?,
    onDetailClick: (Long?) -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(150.dp)
            .clickable { onDetailClick(item?.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            NetworkImage(
                url = item?.imagePath ?: "",
                contentDescription = item?.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item?.name ?: stringResource(Res.string.food_recipes),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item?.tags ?: stringResource(Res.string.no_tags),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .widthIn(max = 90.dp)
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                ) {
                    Row(
                        modifier = Modifier.height(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item?.category ?: "-",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconCircleBorder(imageVector = Icons.Outlined.PlayArrow) {
                    openUrl(item?.youtube_url ?: "")
                }
            }
        }
    }
}