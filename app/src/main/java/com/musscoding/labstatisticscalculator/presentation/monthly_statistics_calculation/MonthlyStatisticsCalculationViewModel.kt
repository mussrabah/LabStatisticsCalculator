package com.musscoding.labstatisticscalculator.presentation.monthly_statistics_calculation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.model.StatsResults
import com.musscoding.labstatisticscalculator.domain.preferences.Preferences
import com.musscoding.labstatisticscalculator.domain.repository.Repository
import com.musscoding.labstatisticscalculator.domain.use_case.UseCases
import com.musscoding.labstatisticscalculator.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MonthlyStatisticsCalculationViewModel @Inject constructor(
    private val repository: Repository,
    private val preferences: Preferences,
    private val useCases: UseCases
) : ViewModel() {

    var state by mutableStateOf(
        MonthlyStatisticsCalculationState())
    init {
        viewModelScope.launch {
            getParams()
            getAllStats()
        }
    }

    private var _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: MonthlyStatisticsCalculationEvents) {
        when(event) {
            is MonthlyStatisticsCalculationEvents.OnParameterTap -> {
                updateParamStats(event.param)
            }
            is MonthlyStatisticsCalculationEvents.OnSaveParamStat -> {
                saveStats()
            }

            is MonthlyStatisticsCalculationEvents.OnParameterLongTap -> {
                resetParamStats(event.parameter)
            }
        }
    }

    private fun resetParamStats(param: Parameter) {
        viewModelScope.launch {
            state = state.copy(
                parameterWithStats = state.parameterWithStats
                    .plus(param to (state.statResults.find {
                        it.parameter.id == param.id
                    }?.total ?: 0
                    ))
            )
        }
    }

    private fun saveStats() {
        viewModelScope.launch {
            state = state.copy(
                statResults = state.parameterWithStats
                    .filter {
                        it.value != 0
                    }.map {
                        StatsResults(
                            id = null,
                            parameter = it.key,
                            date = LocalDate.now(),
                            total = it.value
                        )
                    }
            )
            repository.insertStatsResult(state.statResults)
            _uiEvent.send(UiEvents.NavigateUp)
        }
    }

    private fun updateParamStats(param: Parameter) {
        viewModelScope.launch {
            state = state.copy(
                parameterWithStats = state.parameterWithStats
                    .plus(param to (state.parameterWithStats[param]?.plus(1) ?: 0))
            )
        }
    }

    private fun updateValuesOfStats() {
        /*
        if (state.statResults.isNotEmpty()) {
            state.parameterWithStats.forEach {paramWithStat ->
                var acc = 0
                state.statResults.forEach {statsResults ->
                    if (paramWithStat.key.id == statsResults.parameter.id) {
                        acc += statsResults.total
                        state = state.copy(
                            parameterWithStats = state.parameterWithStats.plus(
                                paramWithStat.key to
                                        paramWithStat.value.plus(acc)
                            )
                        )
                    }
                }
            }
        }

         */

        if (state.statResults.isNotEmpty()) {
            val updatedParameterWithStats = state.parameterWithStats.toMutableMap()

            for (statsResult in state.statResults) {
                val paramWithStat = state.parameterWithStats.keys
                    .find {
                        it.id == statsResult.parameter.id
                    }
                paramWithStat?.let {
                    updatedParameterWithStats[paramWithStat] =
                        updatedParameterWithStats[paramWithStat]!! + statsResult.total
                }
            }

            state = state.copy(parameterWithStats = updatedParameterWithStats)
        }


    }

    private fun getAllStats() {
        viewModelScope.launch {
            repository.getAllStatsByDate(LocalDate.now())
                .onEach {
                    val stats = mutableListOf<StatsResults>()
                    stats.addAll(it.map {statResults ->
                        StatsResults(
                            id = statResults.id,
                            parameter = Parameter(
                                id = statResults.parameter.id,
                                name = state.parameters.find {param ->
                                    statResults.parameter.id == param.id
                                }?.name ?: ""
                            ),
                            date = statResults.date,
                            total = statResults.total
                        )
                    })
                    state = state.copy(
                        statResults =  stats
                    )
                }.collect{
                    updateValuesOfStats()
                }
        }
    }

    private fun getParams() {
        viewModelScope.launch {
            repository.getAllParameters()
                .collect {
                    state = state.copy(
                        parameters = it,
                    )
                    state = state.copy(
                        parameterWithStats = state.parameters.associateWith {
                            0
                        }
                    )
                }
        }
    }
}