package com.musscoding.labstatisticscalculator.presentation.manage_paramaters

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musscoding.labstatisticscalculator.R
import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.repository.Repository
import com.musscoding.labstatisticscalculator.domain.use_case.UseCases
import com.musscoding.labstatisticscalculator.util.DialogTypes
import com.musscoding.labstatisticscalculator.util.UiEvents
import com.musscoding.labstatisticscalculator.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageParameterViewModel @Inject constructor(
    private val repository: Repository,
    private val useCases: UseCases,
    private val context: Application
): ViewModel() {


    var state by mutableStateOf(ManageParameterState())
    init {
        getParams()
    }

    private var _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ManageParameterEvents) {
        when(event) {
            is ManageParameterEvents.OnDeleteParameter -> {
                deleteParameter(event.parameter)
            }
            is ManageParameterEvents.OnShowEditParameterDialog -> {
                state = state.copy(
                    clickedOnParameter = event.parameter
                )
                viewModelScope.launch {
                    _uiEvent.send(UiEvents.ShowDialog(
                        DialogTypes.EditParameterDialog(event.parameter))
                    )
                }
            }
            is ManageParameterEvents.OnShowAddParameterDialog -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvents.ShowDialog(DialogTypes.AddParameterDialog))
                }
            }
            is ManageParameterEvents.AddParameter -> {
                if (event.parameterName.isNotBlank()) {
                    viewModelScope.launch {
                        useCases.addParameterUseCase(
                            Parameter(
                                id = null,
                                name = event.parameterName
                            )
                        )
                    }
                } else {
                    viewModelScope.launch {
                        delay(500L)
                        _uiEvent.send(
                            UiEvents.ShowSnackbar(
                                UiText.DynamicString(context
                                    .getString(R.string.please_enter_a_valid_name))
                            )
                        )
                    }
                }
            }

            is ManageParameterEvents.OnUpdateParameter -> {
                viewModelScope.launch {
                    repository.updateParameter(
                        Parameter(
                            event.parameter.id,
                            name = event.newName
                        )
                    )
                }
            }
        }
    }
    private fun getParams() {
        viewModelScope.launch {
            useCases.getParametersUseCase()
                .collect {
                    state = state.copy(
                        parameters = it,
                    )
                }
        }

    }

    private fun deleteParameter(parameter: Parameter) {
        viewModelScope.launch {
            parameter.id?.let { repository.deleteStatsResultByParamId(it) }
            repository.deleteParameter(parameter)
        }
    }
}