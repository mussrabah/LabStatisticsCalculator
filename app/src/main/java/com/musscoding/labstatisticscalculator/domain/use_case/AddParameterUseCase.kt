package com.musscoding.labstatisticscalculator.domain.use_case

import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.repository.Repository

class AddParameterUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(parameter: Parameter) {
        return repository.insertParameter(parameter = parameter)
    }

    suspend fun insertForFirstTime(params: List<Parameter>) {
        repository.insertAllParameter(params)
    }
}