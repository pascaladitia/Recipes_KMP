package com.pascal.recipes_kmp.data.preferences

import com.pascal.recipes_kmp.createSettings
import com.pascal.recipes_kmp.domain.model.dashboard.ResponseDashboard
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PrefLogin {
    private const val IS_ADMIN = "is_admin"
    private const val RESPONSE_LOGIN = "response_login"

    private fun Settings.setLoginResponse(value: ResponseDashboard?) {
        if (value != null) {
            val jsonString = Json.encodeToString(value)
            putString(RESPONSE_LOGIN, jsonString)
        } else {
            remove(RESPONSE_LOGIN)
        }
    }

    private fun Settings.getLoginResponse(): ResponseDashboard? {
        val jsonString = getString(RESPONSE_LOGIN, "")
        return jsonString.let { Json.decodeFromString(it) }
    }

    fun setLoginResponse(value: ResponseDashboard?) {
        createSettings().setLoginResponse(value)
    }

    fun getLoginResponse(): ResponseDashboard? {
        return createSettings().getLoginResponse()
    }

    fun setIsAdmin(value: Boolean) {
        createSettings()[IS_ADMIN] = value
    }

    fun getIsAdmin(): Boolean {
        return createSettings()[IS_ADMIN, false]
    }

    fun clear() {
        createSettings().clear()
    }
}