package com.musscoding.labstatisticscalculator.presentation.statistics_menu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musscoding.labstatisticscalculator.domain.model.defaultParameters
import com.musscoding.labstatisticscalculator.domain.preferences.Preferences
import com.musscoding.labstatisticscalculator.domain.use_case.UseCases
import com.musscoding.labstatisticscalculator.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class StatisticsMenuViewModel @Inject constructor(
    private val preferences: Preferences,
    private val useCases: UseCases
): ViewModel(){

    init {
        viewModelScope.launch {
            if (preferences.loadFirstTimeStartup()) {
                useCases.addParameterUseCase.insertForFirstTime(
                    defaultParameters
                )
                preferences.saveFirstTimeStartup(false)
            }
        }

    }
    var state by mutableStateOf(StatisticsMenuState())

    private var _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEnterSettingClick(index: Int) {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvents.Success(index)
            )
        }

    }
}