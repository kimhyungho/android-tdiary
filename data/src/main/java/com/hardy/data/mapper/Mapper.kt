package com.hardy.data.mapper

interface Mapper<Data, Domain> {
    fun mapToDomain(from: Data): Domain
}