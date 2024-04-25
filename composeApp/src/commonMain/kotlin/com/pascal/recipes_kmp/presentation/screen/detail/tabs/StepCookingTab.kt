package com.pascal.recipes_kmp.presentation.screen.detail.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pascal.recipes_kmp.domain.model.DetailRecipesMapping

@Composable
fun StepCookingTab(modifier: Modifier = Modifier, item: DetailRecipesMapping?) {
    val recipeString = item?.strInstructions
    val recipeList = recipeString?.split("\r\n")

    recipeList?.forEachIndexed { index, item ->
        if(item.isNotEmpty()  && item.isNotBlank() && item != "") {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (index != 0) {
                    Divider(color = Color.Black, modifier = Modifier
                        .height(14.dp)
                        .width(1.dp))
                }
                Box(
                    modifier = Modifier
                        .background(Color.Black, RoundedCornerShape(16.dp))
                        .clip(CircleShape)
                        .size(24.dp)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index.plus(1)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
                Divider(color = Color.Black, modifier = Modifier
                    .height(14.dp)
                    .width(1.dp))
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .padding(12.dp),
                ) {
                    Text(text = item ?: "-", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}