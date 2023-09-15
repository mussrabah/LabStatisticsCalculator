package com.musscoding.labstatisticscalculator.presentation.see_results

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musscoding.labstatisticscalculator.R
import com.musscoding.labstatisticscalculator.presentation.components.ParameterTap
import com.musscoding.labstatisticscalculator.presentation.see_results.components.MonthPicker
import com.musscoding.labstatisticscalculator.presentation.see_results.components.ShowParameterStatsDialog
import com.musscoding.labstatisticscalculator.util.DialogTypes
import com.musscoding.labstatisticscalculator.util.UiEvents

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SeeResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: SeeResultsViewModel = hiltViewModel(),
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    background: Color = MaterialTheme.colorScheme.inversePrimary,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    textSize: TextUnit = 16.sp,
    padding: Dp = 10.dp,
    snackbarHostState: SnackbarHostState
) {
    val state = viewModel.state
    var shouldShowDialog by remember {
        mutableStateOf(false)
    }
    val density = LocalDensity.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvents.ShowDialog -> {
                    shouldShowDialog = true
                    when(event.dialogType) {
                        is DialogTypes.ShowParameterStatsDialog -> {
                        }
                        else -> {}
                    }
                }
                else -> {}
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(SeeResultsEvents.OnDeleteAllStats)
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete)
                )
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding + it.calculateTopPadding())
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.les_resultats_du_mois),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = padding)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MonthPicker(
                    month = state.date.month,
                    onBackClicked = {
                        viewModel.onEvent(SeeResultsEvents.OnPreviousMonthClick)
                    },
                    onForwardClicked = {
                        viewModel.onEvent(SeeResultsEvents.OnNextMonthClick)
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(200.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalItemSpacing = 10.dp
            ) {
                items(state.globalParameterList) {
                    ParameterTap(
                        parameter = it,
                        paramStats = state.monthlyParamWithStat[it] ?: 0,
                        onItemClick = {
                            viewModel.onEvent(SeeResultsEvents.OnParamClick(it))
                        },
                        onItemLongClick = {}
                    )
                }
            }
        }
    }


    ShowParameterStatsDialog(
        visible = shouldShowDialog,
        onDismissRequest = {
                           shouldShowDialog = false
        },
        density = density,
        text = state.clickedOnParam?.name ?: "",
        statsPercentage = state.percentage
    )
}