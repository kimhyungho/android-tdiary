package com.hardy.domain.exceptions

import com.google.firebase.FirebaseException

class YongByungException(val code: String, cause: Throwable) : RuntimeException(cause) {
    companion object Code {
        const val CANNOT_FIND_CREDENTIAL = "16: Cannot find a matching credential."
        const val UNKNOWN_ERROR = "UNKNOWN_ERROR"

        fun parseFrom(e: Exception): YongByungException {
            return when (e) {
                is FirebaseException -> {
                    YongByungException(e.message ?: UNKNOWN_ERROR, e)
                }

                else -> {
                    YongByungException(e.message ?: UNKNOWN_ERROR, e)
                }
            }
        }
    }
}