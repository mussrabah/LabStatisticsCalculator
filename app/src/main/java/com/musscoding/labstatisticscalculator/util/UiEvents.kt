package com.musscoding.labstatisticscalculator.util

sealed class UiEvents {

        data class Success(val index: Int): UiEvents()
        object NavigateUp: UiEvents()
        data class ShowDialog(val dialogType: DialogTypes): UiEvents()
        data class ShowSnackbar(val message: UiText): UiEvents()

}
