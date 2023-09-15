package com.musscoding.labstatisticscalculator.presentation.manage_paramaters.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.presentation.components.MenuCard

@Composable
fun ManageParameterCore(
    modifier: Modifier = Modifier,
    parameters: List<Parameter>,
    padding: PaddingValues,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    background: Color = MaterialTheme.colorScheme.secondaryContainer,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    textSize: TextUnit = 24.sp,
    onClickParameter: (Parameter) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(parameters) {
            MenuCard(
                title = it.name,
                textColor = textColor,
                textSize = textSize,
                textStyle = textStyle,
                background = background,
                onNavigate = {
                    onClickParameter(it)
                }
            )
        }
    }
}