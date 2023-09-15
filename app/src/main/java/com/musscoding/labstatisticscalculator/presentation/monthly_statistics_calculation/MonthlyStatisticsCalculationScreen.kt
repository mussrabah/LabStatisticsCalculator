package com.musscoding.labstatisticscalculator.presentation.monthly_statistics_calculation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musscoding.labstatisticscalculator.R
import com.musscoding.labstatisticscalculator.presentation.components.ParameterTap
import com.musscoding.labstatisticscalculator.util.UiEvents

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MonthlyStatisticsCalculation(
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    background: Color = MaterialTheme.colorScheme.inversePrimary,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    textSize: TextUnit = 16.sp,
    padding: Dp = 10.dp,
    viewModel: MonthlyStatisticsCalculationViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navigateUp: () -> Unit
) {
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvents.NavigateUp -> {
                    navigateUp()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                       Text(text = stringResource(R.string.save))
                },
                icon = {
                       Icon(
                           imageVector = Icons.Default.Done,
                           contentDescription = stringResource(R.string.done)
                       )
                },
                onClick = {
                    viewModel.onEvent(MonthlyStatisticsCalculationEvents.OnSaveParamStat)
                })
        }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding + it.calculateTopPadding()),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 10.dp
        ) {

            items(state.parameters) { parameter ->
                ParameterTap(
                    parameter = parameter,
                    onItemClick = {
                        viewModel.onEvent(
                            MonthlyStatisticsCalculationEvents.OnParameterTap(
                                parameter
                            )
                        )
                    },
                    paramStats = state.parameterWithStats[parameter] ?: 0,
                    onItemLongClick = {
                        viewModel.onEvent(
                            MonthlyStatisticsCalculationEvents.OnParameterLongTap(
                                parameter
                            )
                        )
                    }
                )
            }
        }
    }
}