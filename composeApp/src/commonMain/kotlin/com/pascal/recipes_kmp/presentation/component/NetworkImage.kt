package com.pascal.recipes_kmp.presentation.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import recipes_kmp.composeapp.generated.resources.Res
import recipes_kmp.composeapp.generated.resources.no_profile

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NetworkImage(
    modifier: Modifier,
    url: String,
    contentDescription: String?,
    contentScale: ContentScale
) {
    val image: Resource<Painter> = asyncPainterResource(data = url)
    KamelImage(
        resource = image,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        onLoading = {
            LoadingScreen()
        },
        onFailure = {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(Res.drawable.no_profile),
                contentDescription = null,
                alignment = Alignment.CenterEnd
            )
        },
        animationSpec = tween(),
    )
}