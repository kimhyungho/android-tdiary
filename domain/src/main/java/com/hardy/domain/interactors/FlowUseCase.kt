package com.hardy.domain.interactors

import com.hardy.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<Params, T> {
    operator fun invoke(params: Params? = null): Flow<Response<T>>
}