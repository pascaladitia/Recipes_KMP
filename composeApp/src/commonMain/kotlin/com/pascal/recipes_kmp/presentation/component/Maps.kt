package com.pascal.recipes_kmp.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pascal.recipes_kmp.domain.model.location.DeviceLocation
import com.pascal.recipes_kmp.domain.usecases.Cinema
import com.pascal.recipes_kmp.domain.usecases.MapUiState

@Composable
expect fun Maps(modifier: Modifier, uiState: MapUiState, onMarkerClick : (Cinema?) -> Unit, onPositionChange: (DeviceLocation) -> Unit)

@Composable
fun MapsMarkerDialog(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    onClick : () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 88.dp
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        TextItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = Int.MAX_VALUE
        )
        TextItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 5.dp),
            text = subTitle,
            fontSize = 15.sp,
            fontWeight = FontWeight.Light,
            maxLines = Int.MAX_VALUE
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            thickness = 1.dp
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(11.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                onClick.invoke()
            }) {
            Icon(
                modifier = Modifier.padding(2.dp),
                imageVector = Icons.Outlined.KeyboardArrowRight,
                tint = Color.White,
                contentDescription = null
            )
            TextItem(
                modifier = Modifier.wrapContentSize(),
                fontSize = 10.sp,
                textColor = MaterialTheme.colorScheme.primaryContainer,
                text = "Go",
            )
        }
    }
}

@Composable
fun TextItem(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.secondary,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Clip,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = fontSize,
        fontWeight = fontWeight,
        maxLines = maxLines,
        overflow = overflow,
        lineHeight = lineHeight
    )
}
