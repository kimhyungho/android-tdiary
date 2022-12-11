package com.hardy.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.hardy.domain.repositories.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingRepository {
    override fun getIsPermissionChecked(): Flow<Boolean> =
        dataStore.data.map { it[IS_PERMISSION_CHECKED_KEY] ?: false }

    override suspend fun setIsPermissionChecked(isPermissionChecked: Boolean) {
        dataStore.updateData { pref ->
            pref.toMutablePreferences().apply {
                set(IS_PERMISSION_CHECKED_KEY, isPermissionChecked)
            }
        }
    }

    companion object {
        val IS_PERMISSION_CHECKED_KEY = booleanPreferencesKey("isPermissionChecked")
    }
}