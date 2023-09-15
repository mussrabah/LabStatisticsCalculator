package com.musscoding.labstatisticscalculator.presentation.statistics_menu

import com.musscoding.labstatisticscalculator.R
import com.musscoding.labstatisticscalculator.util.UiText

data class Settings(
    val text: UiText
)

val defaultSettings = listOf(
    Settings(UiText.StringResource(R.string.demarrer_calcul_des_statistiques_menstriel)),
    Settings(UiText.StringResource(R.string.demarrer_calcul_des_statistiques_trimestriel)),
    Settings(UiText.StringResource(R.string.gerer_les_parametres_disponible)),
    Settings(UiText.StringResource(R.string.voir_les_resultat_du_statistique))
)
