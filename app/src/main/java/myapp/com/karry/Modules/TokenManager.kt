package myapp.com.karry.Modules

import android.content.Context
import android.preference.PreferenceManager

class TokenManager(context: Context) {
    companion object {
        val DEVELOP_MODE = false
        private const val DEVICE_TOKEN = "data.source.prefs.DEVICE_TOKEN"
    }
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    var deviceToken = preferences.getString(DEVICE_TOKEN, "")
        set(value) = preferences.edit().putString(DEVICE_TOKEN, value).apply()
}