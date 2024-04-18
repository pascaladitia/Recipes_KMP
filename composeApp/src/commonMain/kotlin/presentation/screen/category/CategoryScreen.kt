package presentation.screen.category

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import domain.model.FilterCategoryItem
import domain.usecases.UiState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import presentation.component.ErrorScreen
import presentation.component.IconCircleBorder
import presentation.component.LoadingScreen
import presentation.component.NetworkImage
import recipes_kmp.composeapp.generated.resources.Res
import recipes_kmp.composeapp.generated.resources.empty
import recipes_kmp.composeapp.generated.resources.food_recipes

class CategoryScreen(val query: String) : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val coroutineScope = rememberCoroutineScope()
        val viewModel: CategoryViewModel = koinInject<CategoryViewModel>()

        LaunchedEffect(key1 = true) {
            viewModel.loadFilterCategory(query)
        }

        val uiState by viewModel.filterCategory.collectAsState()

        Surface(
            modifier = Modifier.padding(PaddingValues(top = 24.dp, bottom = 48.dp)),
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
                    ErrorScreen(message = stringResource(Res.string.empty)) {}
                }
                is UiState.Success -> {
                    val data = (uiState as UiState.Success).data
                    CategoryContent(
                        query = query,
                        listCategory = data?.meals,
                        onDetailClick = {
//                            onDetailClick(it)
                        },
                        onNavBack = {
                            navigator?.pop()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CategoryContent(
    modifier: Modifier = Modifier,
    query: String,
    listCategory: List<FilterCategoryItem?>?,
    onDetailClick: (String) -> Unit,
    onNavBack: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconCircleBorder(
                size = 42.dp,
                padding = 6.dp,
                imageVector = Icons.Outlined.ArrowBack
            ) {
                onNavBack()
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp),
                text = query.ifEmpty { stringResource(Res.string.food_recipes) },
                style = MaterialTheme.typography.headlineLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(130.dp),
            state = rememberLazyGridState(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            items(listCategory!!.size) { index ->
                listCategory[index]?.let { item ->
                    CategoryItemFilter(
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
fun CategoryItemFilter(
    modifier: Modifier = Modifier,
    item: FilterCategoryItem,
    onDetailClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { onDetailClick(item.idMeal ?: "") }
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NetworkImage(
                url = item.strMealThumb ?: "",
                contentDescription = item.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.strMeal ?: "-",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}