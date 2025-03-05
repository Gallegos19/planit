package com.example.planit.core.data

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREFS_NAME = "app_prefs"
    private const val TOKEN_KEY = "TOKEN"
    private const val USER_ID_KEY = "USER_ID"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Guardar token
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    // Obtener token
    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    // Guardar ID del usuario
    fun saveUserId(userId: Int) {
        sharedPreferences.edit().putInt(USER_ID_KEY, userId).apply()
    }

    // Obtener ID del usuario
    fun getUserId(): Int {
        return sharedPreferences.getInt(USER_ID_KEY, -1) // Retorna -1 si no existe
    }

    // Borrar sesión completa (Token + ID)
    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}
