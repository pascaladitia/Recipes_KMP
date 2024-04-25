package com.pascal.recipes_kmp.presentation.screen.detail.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pascal.recipes_kmp.domain.model.DetailRecipesMapping

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientsTab(modifier: Modifier = Modifier, item: DetailRecipesMapping?) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        item?.listIngredient?.forEachIndexed { index, data ->
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp, top = 8.dp)
                    .width(164.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(14.dp)
                        .fillMaxWidth()
                        .padding(end = 20.dp)
                ) {
                    Text(
                        text = data.strIngredient ?: "-",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .padding(vertical = 4.dp, horizontal = 12.dp),
                    ) {
                        Text(text = data.strMeasure ?: "-", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(paddingValues = PaddingValues(top = 6.dp, end = 8.dp))
                        .border(1.dp, Color.Black, CircleShape)
                        .clip(CircleShape)
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index.plus(1)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}