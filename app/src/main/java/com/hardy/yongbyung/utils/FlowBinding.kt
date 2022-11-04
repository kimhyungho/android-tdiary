package com.hardy.yongbyung.utils

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        this.trySend(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

fun <T> Flow<T>.throttleFirst(duration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > duration) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}

fun <T> Flow<T>.bind(coroutineScope: CoroutineScope, onBind: () -> Unit) {
    this.onEach { onBind.invoke() }
        .launchIn(coroutineScope)
}