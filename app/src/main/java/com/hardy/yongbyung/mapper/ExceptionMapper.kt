package com.hardy.yongbyung.mapper

import com.hardy.domain.exceptions.YongByungException

internal object ExceptionMapper : Mapper<Throwable?, String> {
    override fun mapToView(from: Throwable?): String {
        from?.printStackTrace()
        return when (from) {
            is YongByungException -> {
                when (from.code) {
                    YongByungException.CANNOT_FIND_CREDENTIAL -> "기기에 구글 계정이 등록되어 있지 않습니다. 등록 후 이용해주세요."
                    else -> "알수없는 에러가 발생했습니다."
                }
            }

            else -> "알수없는 에러가 발생했습니다."
        }
    }
}