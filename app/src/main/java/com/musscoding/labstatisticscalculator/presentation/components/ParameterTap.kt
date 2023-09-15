package com.musscoding.labstatisticscalculator.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musscoding.labstatisticscalculator.R
import com.musscoding.labstatisticscalculator.domain.model.Parameter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ParameterTap(
    modifier: Modifier = Modifier,
    parameter: Parameter,
    paramStats: Int,
    textSize: TextUnit = 24.sp,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    background: Color = MaterialTheme.colorScheme.primaryContainer,
    padding: Dp = 8.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onItemClick: () -> Unit,
    onItemLongClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(
                onClick = {
                          onItemClick()
                },
                onLongClick = {
                    onItemLongClick()
                }
            )
            .border(width = 2.dp, color = MaterialTheme.colorScheme.onBackground)
            .background(background)
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(
                text = parameter.name,
                fontSize = textSize,
                color = textColor,
                style = textStyle,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = true
            )
        }
        Row {
            Text(
                text = stringResource(R.string.count) +": $paramStats",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }

    }
}