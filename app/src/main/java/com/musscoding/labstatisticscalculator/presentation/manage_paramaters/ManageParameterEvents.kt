package com.musscoding.labstatisticscalculator.presentation.manage_paramaters

import com.musscoding.labstatisticscalculator.domain.model.Parameter

sealed class ManageParameterEvents {
    data class OnDeleteParameter(val parameter: Parameter): ManageParameterEvents()
    data class OnShowEditParameterDialog(val parameter: Parameter): ManageParameterEvents()
    object OnShowAddParameterDialog: ManageParameterEvents()
    data class AddParameter(val parameterName: String): ManageParameterEvents()
    data class OnUpdateParameter(val parameter: Parameter,
                                 val newName: String): ManageParameterEvents()
}
