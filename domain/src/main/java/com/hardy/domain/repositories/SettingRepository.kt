package com.hardy.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getIsPermissionChecked(): Flow<Boolean>

    suspend fun setIsPermissionChecked(isPermissionChecked: Boolean)
}