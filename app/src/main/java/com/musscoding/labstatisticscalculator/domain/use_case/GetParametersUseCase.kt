package com.musscoding.labstatisticscalculator.domain.use_case

import com.musscoding.labstatisticscalculator.domain.model.Parameter
import com.musscoding.labstatisticscalculator.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetParametersUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Parameter>> {
        return repository.getAllParameters()
    }
}