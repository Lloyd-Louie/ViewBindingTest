package com.example.multilanguage.utils

import android.content.Context
import android.content.SharedPreferences
import com.securepreferences.SecurePreferences

object UserSessionManager {
    private lateinit var sharedPref: SecurePreferences
    private const val KEY_LOCALE = "UserSessionManager.KEY_LOCALE"
    fun init(context: Context) {
        sharedPref = SecurePreferences(context)
    }
    var currentLanguage: String
        get() = sharedPref.getString(KEY_LOCALE, "") ?: ""
        set(value) {
            sharedPref.edit {
                it.putString(KEY_LOCALE, value)
            }
        }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }
}