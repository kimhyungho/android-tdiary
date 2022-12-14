package com.hardy.domain.interactors

import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface SuspendFlowUseCase<Params, T> {
    suspend operator fun invoke(params: Params? = null): Flow<Response<T>>
}