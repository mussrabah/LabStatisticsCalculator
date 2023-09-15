package com.musscoding.labstatisticscalculator.domain.model

data class Parameter(
    val id: Int?,
    val name: String
)

val defaultParameters = listOf(
    Parameter(
        id = null,
        name = "Gly"
    ),Parameter(
        id = null,
        name = "Chol"
    ),Parameter(
        id = null,
        name = "TG"
    ),Parameter(
        id = null,
        name = "Uree"
    ),Parameter(
        id = null,
        name = "Creat"
    ),Parameter(
        id = null,
        name = "HDL"
    ),Parameter(
        id = null,
        name = "LDL"
    ),Parameter(
        id = null,
        name = "VS"
    ),Parameter(
        id = null,
        name = "Crp"
    ),Parameter(
        id = null,
        name = "FNS"
    ),Parameter(
        id = null,
        name = "Gr + Rh"
    ),
)