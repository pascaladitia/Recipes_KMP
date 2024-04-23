package com.pascal.recipes_kmp.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
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
import com.pascal.recipes_kmp.domain.model.CategoriesItem
import com.pascal.recipes_kmp.domain.model.CategoryResponse
import com.pascal.recipes_kmp.domain.model.MealsItem
import com.pascal.recipes_kmp.domain.usecases.UiState
import com.pascal.recipes_kmp.presentation.component.ErrorScreen
import com.pascal.recipes_kmp.presentation.component.IconCircleBorder
import com.pascal.recipes_kmp.presentation.component.NetworkImage
import com.pascal.recipes_kmp.presentation.component.Search
import com.pascal.recipes_kmp.presentation.component.ShimmerAnimation
import com.pascal.recipes_kmp.presentation.screen.category.CategoryScreen
import com.pascal.recipes_kmp.utils.generateRandomChar
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowRight
import compose.icons.feathericons.BookOpen
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import recipes_kmp.composeapp.generated.resources.Res
import recipes_kmp.composeapp.generated.resources.category
import recipes_kmp.composeapp.generated.resources.cookie
import recipes_kmp.composeapp.generated.resources.empty
import recipes_kmp.composeapp.generated.resources.food_recipes
import recipes_kmp.composeapp.generated.resources.just_for_you
import recipes_kmp.composeapp.generated.resources.no_tags

class HomeScreen() : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val coroutineScope = rememberCoroutineScope()
        val viewModel: HomeViewModel = koinInject<HomeViewModel>()
        var isContentVisible by remember { mutableStateOf(false) }

        val uiState by viewModel.recipes.collectAsState(initial = UiState.Loading)
        val category by produceState<CategoryResponse?>(initialValue = null) {
            value = viewModel.loadCategory()
        }

        val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
        val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
        BindEffect(controller)

        LaunchedEffect(key1 = true) {
            viewModel.loadListRecipes(generateRandomChar())
            controller.providePermission(Permission.CAMERA)
        }

        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(PaddingValues(vertical = 48.dp))
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    ShimmerAnimation()
                }
                is UiState.Error -> {
                    val message = (uiState as UiState.Error).message
                    ErrorScreen(message = message) {
                        coroutineScope.launch {
                            viewModel.loadListRecipes(generateRandomChar())
                        }
                    }
                }
                is UiState.Empty -> {
                    coroutineScope.launch {
                        delay(100)
                        isContentVisible = true
                    }

                    HomeContent(
                        isEmpty = true,
                        isContentVisible = isContentVisible,
                        listCategory = category?.categories,
                        listRecipe = emptyList(),
                        onCategoryClick = { query ->
                            navigator?.push(CategoryScreen(query))
                        },
                        onDetailClick = {},
                        onSearch = { query ->
                            coroutineScope.launch {
                                viewModel.loadSearchRecipes(query)
                            }
                        },
                        onRetry = {
                            coroutineScope.launch {
                                viewModel.loadListRecipes(generateRandomChar())
                            }
                        }
                    )
                }
                is UiState.Success -> {
                    coroutineScope.launch {
                        delay(100)
                        isContentVisible = true
                    }
                    val data = (uiState as UiState.Success).data
                    HomeContent(
                        isContentVisible = isContentVisible,
                        listCategory = category?.categories,
                        listRecipe = data?.meals,
                        onCategoryClick = { query ->
                            navigator?.push(CategoryScreen(query))
                        },
                        onDetailClick = { query ->
                            coroutineScope.launch {
                                isContentVisible = false
                                delay(500)
//                                onDetailClick(query)
                            }
                        },
                        onSearch = { query ->
                            coroutineScope.launch {
                                viewModel.loadSearchRecipes(query)
                            }
                        },
                        onRetry = {
                            coroutineScope.launch {
                                viewModel.loadListRecipes(generateRandomChar())
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    isEmpty: Boolean = false,
    isContentVisible: Boolean = true,
    listCategory: List<CategoriesItem?>?,
    listRecipe: List<MealsItem?>?,
    onCategoryClick: (String) -> Unit,
    onDetailClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
                exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
            ) {
                Text(text = stringResource(Res.string.cookie), style = MaterialTheme.typography.headlineLarge)
            }

            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
                exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
            ) {
                IconCircleBorder(imageVector = Icons.Outlined.Notifications) {}
            }
        }
        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            Search { query ->
                onSearch(query)
            }
        }
        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
        ) {
            SectionText(text = stringResource(Res.string.category))
        }

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp),
            ) {
                items(listCategory ?: emptyList()) { category ->
                    category?.let {
                        CategoryItem(
                            item = it,
                            onCategoryClick = { query ->
                                onCategoryClick(query)
                            }
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
        ) {
            SectionText(text = stringResource(Res.string.just_for_you))
        }

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            when(isEmpty) {
                true -> {
                    ErrorScreen(message = stringResource(Res.string.empty)) {
                        onRetry()
                    }
                }
                false -> {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                    ) {
                        items(listRecipe ?: emptyList()) { category ->
                            category?.let { item ->
                                RecipesItem(
                                    item = item,
                                    onDetailClick = { onDetailClick(it) }
                                )
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            when(isEmpty) {
                true -> {
                    ErrorScreen(message = stringResource(Res.string.empty)) {
                        onRetry()
                    }
                }
                false -> {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                    ) {
                        items(listRecipe?.sortedByDescending { it?.idMeal } ?: emptyList()) { category ->
                            category?.let { item ->
                                RecipesItem(
                                    item = item,
                                    onDetailClick = { onDetailClick(it) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    item: CategoriesItem,
    onCategoryClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(top = 16.dp, bottom = 24.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(80.dp)
            .clickable {
                onCategoryClick(item.strCategory ?: "")
            }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NetworkImage(
                url = item.strCategoryThumb ?: "",
                contentDescription = item.strCategory,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                    .padding(4.dp)
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = item.strCategory ?: stringResource(Res.string.category),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RecipesItem(
    modifier: Modifier = Modifier,
    item: MealsItem,
    onDetailClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(top = 16.dp, bottom = 24.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .width(240.dp)
            .clickable { onDetailClick(item.idMeal ?: "") }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            NetworkImage(
                url = item.strMealThumb ?: "",
                contentDescription = item.strCategory,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item.strMeal ?: stringResource(Res.string.food_recipes),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.strTags ?: stringResource(Res.string.no_tags),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = FeatherIcons.BookOpen, contentDescription = "")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = item.strCategory ?: "-", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconCircleBorder(imageVector = Icons.Outlined.PlayArrow) {
//                    intentActionView(context, item.strYoutube.toString())
                }
            }
        }
    }
}

@Composable
fun SectionText(modifier: Modifier = Modifier, text: String) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall
        )
        Icon(imageVector = FeatherIcons.ArrowRight, contentDescription = "")
    }
}
