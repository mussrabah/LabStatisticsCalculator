package com.musscoding.labstatisticscalculator.presentation.statistics_menu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musscoding.labstatisticscalculator.R
import com.musscoding.labstatisticscalculator.presentation.components.MenuCard
import com.musscoding.labstatisticscalculator.util.UiEvents

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsMenuScreen(
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    background: Color = MaterialTheme.colorScheme.inversePrimary,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    textSize: TextUnit = 24.sp,
    padding: Dp = 8.dp,
    viewModel: StatisticsMenuViewModel = hiltViewModel(),
    onNextClick: (Int) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvents.Success -> onNextClick(event.index)
                else -> Unit
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .padding(padding)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.bienvenue),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
        itemsIndexed(viewModel.state.settings) { index, setting ->
            MenuCard(
                title = setting.text.asString(context),
                textColor = textColor,
                textSize = textSize,
                textStyle = textStyle,
                background = background,
                onNavigate = {
                    viewModel.onEnterSettingClick(index)
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}