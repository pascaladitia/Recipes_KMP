package com.pascal.recipes_kmp.presentation.screen.detail.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pascal.recipes_kmp.domain.model.DetailRecipesMapping
import org.jetbrains.compose.resources.stringResource
import recipes_kmp.composeapp.generated.resources.Res
import recipes_kmp.composeapp.generated.resources.ingredients
import recipes_kmp.composeapp.generated.resources.step_cooking

@Composable
fun ContentDetailWithTabs(
    modifier: Modifier = Modifier,
    item: DetailRecipesMapping?,
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .border(1.dp, Color.Black, CircleShape)
                .clip(CircleShape),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TabRow(
                /* add indicator
               indicator = { tabPositions ->
                   TabRowDefaults.Indicator(
                       modifier = Modifier
                           .tabIndicatorOffset(tabPositions[selectedTabIndex])
                           .padding(4.dp)
                           .background(MaterialTheme.colorScheme.primary)
                           .border(1.dp, Color.Black, CircleShape)
                           .clip(CircleShape)
                   )
               },
               */
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    modifier = Modifier
                        .background(if (selectedTabIndex == 0) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .padding(8.dp)
                        .clip(CircleShape)
                ) {
                    Text(
                        text = stringResource(Res.string.ingredients),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedTabIndex == 0) Color.White else Color.Black
                    )
                }
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    modifier = Modifier
                        .background(if (selectedTabIndex == 1) MaterialTheme.colorScheme.primary else Color.Transparent)
                        .padding(8.dp)
                        .clip(CircleShape)
                ) {
                    Text(
                        text = stringResource(Res.string.step_cooking),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedTabIndex == 1) Color.White else Color.Black
                    )
                }
            }
        }

        when (selectedTabIndex) {
            0 -> IngredientsTab(item = item)
            1 -> StepCookingTab(item = item)
        }
    }
}