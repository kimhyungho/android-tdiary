package com.hardy.yongbyung.provider.permission

import kotlinx.coroutines.flow.Flow

interface PermissionProvider {
    fun checkPermissions(
        vararg permissions: String
    ): Flow<Boolean>
}