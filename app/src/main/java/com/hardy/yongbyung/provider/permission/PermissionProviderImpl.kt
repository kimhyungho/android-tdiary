package com.hardy.yongbyung.provider.permission

import com.gun0912.tedpermission.coroutine.TedPermission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PermissionProviderImpl @Inject constructor() : PermissionProvider {
    override fun checkPermissions(vararg permissions: String): Flow<Boolean> = flow {
        val permissionResult = TedPermission.create()
            .setPermissions(*permissions)
            .check()

        emit(permissionResult.isGranted)
    }
}