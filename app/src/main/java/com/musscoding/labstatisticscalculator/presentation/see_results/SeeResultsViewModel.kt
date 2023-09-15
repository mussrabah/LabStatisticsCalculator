package com.musscoding.labstatisticscalculator.presentation.see_results

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.model.StatsResults
import com.musscoding.labstatisticscalculator.domain.repository.Repository
import com.musscoding.labstatisticscalculator.util.DialogTypes
import com.musscoding.labstatisticscalculator.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SeeResultsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var state by mutableStateOf(SeeResultsState())

    init {
        getAllParameters()
    }

    private var _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SeeResultsEvents) {
        when(event) {
            is SeeResultsEvents.OnParamClick -> {
                showParamStatDialog(event.param)
            }

            is SeeResultsEvents.OnNextMonthClick -> {
                state = state.copy(
                    date = state.date.plusMonths(1)
                )
                filterStatsForSpecificMonth(state.date.month)
            }
            is SeeResultsEvents.OnPreviousMonthClick -> {
                state = state.copy(
                    date = state.date.minusMonths(1)
                )
                filterStatsForSpecificMonth(state.date.month)
            }

            SeeResultsEvents.OnDeleteAllStats -> {
                deleteAllStatsOfSpecificMonth(state.date.month)
            }
        }
    }

    private fun deleteAllStatsOfSpecificMonth(month: Month?) {
        viewModelScope.launch {
            (1..month?.length(LocalDate.now().isLeapYear)!!)
                .forEach {
                    repository.deleteStatsResultByDate(LocalDate.of(
                        LocalDate.now().year,
                        month,
                        it
                    ))
                }
        }
    }

    private fun showParamStatDialog(param: Parameter) {
        viewModelScope.launch {
            state = state.copy(
                clickedOnParam = param
            )
            calcPercentage(param)
            _uiEvent.send(UiEvents.ShowDialog(DialogTypes.ShowParameterStatsDialog(param)))
        }
    }

    private fun calcPercentage(param: Parameter) {
        //calc occurrences of last month
        val lastMonthOcc = state.globalStatsList
            .filter {
                it.date.month == LocalDate.now().month.minus(1)
            }
            .filter {
                it.parameter.id == param.id
            }
            .sumOf {
            it.total
            }

        val thisMonthOcc = state.globalStatsList
            .filter {
                it.date.month == LocalDate.now().month
            }
            .filter {
                it.parameter.id == param.id
            }
            .sumOf {
                it.total
            }


        val percentage = if (lastMonthOcc == 0) 0f
                        else if (thisMonthOcc == 0) -lastMonthOcc.toFloat()
                        else if (lastMonthOcc < thisMonthOcc) (100 / (thisMonthOcc / lastMonthOcc)).toFloat()
                        else -(100 / (lastMonthOcc / thisMonthOcc)).toFloat()
        state = state.copy(
            percentage = percentage
        )
    }

    private fun associateEachParamWithStat() {
        viewModelScope.launch {
            state = state.copy(
                monthlyParamWithStat = state.globalParameterList.associateWith { param ->
                    state.monthlyStatsList.find { statResult ->
                        statResult.parameter == param
                    }?.total ?: 0
                }
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterStatsForSpecificMonth(month: Month) {
        viewModelScope.launch {
            state = state.copy(
                monthlyStatsList = state.globalStatsList.filter {
                    it.date.month == month
                }
            )
            Log.d(
                "SeeResultsViewModel",
                "filtered monthly list: ${state.monthlyStatsList}"
            )
            associateEachParamWithStat()
        }
    }

    private fun getAllStats() {
        viewModelScope.launch {
            repository.getAllStats()
                .onEach {
                    val stats = mutableListOf<StatsResults>()
                    stats.addAll(it.map { statResults ->
                        StatsResults(
                            id = statResults.id,
                            parameter = Parameter(
                                id = statResults.parameter.id,
                                name = state.globalParameterList.find { param ->
                                    statResults.parameter.id == param.id
                                }?.name ?: ""
                            ),
                            date = statResults.date,
                            total = statResults.total
                        )
                    })
                    state = state.copy(
                        globalStatsList = stats
                    )
                }.collect {
                    Log.d(
                        "SeeResultsViewModel",
                        "all stats list: ${state.globalStatsList}"
                    )
                    filterStatsForSpecificMonth(LocalDate.now().month)
                }
        }
    }

        private fun getAllParameters() {
            viewModelScope.launch {
                repository.getAllParameters()
                    .collect {
                        state = state.copy(
                            globalParameterList = it
                        )
                        getAllStats()
                    }
            }
        }

}