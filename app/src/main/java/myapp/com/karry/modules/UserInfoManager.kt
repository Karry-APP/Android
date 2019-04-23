package myapp.com.karry.modules

import android.content.Context
import android.preference.PreferenceManager

class UserInfoManager(context: Context) {
    companion object {
        private const val USER_ID = "data.source.prefs.USER_ID"
        private const val USER_FIRSTNAME = "data.source.prefs.USER_FIRSTNAME"
        private const val USER_LASTNAME = "data.source.prefs.USER_LASTNAME"
        private const val USER_PHONE= "data.source.prefs.USER_PHONE"
        private const val USER_EMAIL = "data.source.prefs.USER_EMAIL"
        private const val USER_PROFILE= "data.sourve.prefs.USER_PROFILE"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var id: String?
        get() = preferences.getString(USER_ID, "")
        set(value) = preferences.edit().putString(USER_ID, value).apply()

    var firstname: String?
        get() = preferences.getString(USER_FIRSTNAME, "")
        set(value) = preferences.edit().putString(USER_FIRSTNAME, value).apply()

    var lastname: String?
        get() = preferences.getString(USER_LASTNAME, "")
        set(value) = preferences.edit().putString(USER_LASTNAME, value).apply()

    var phone: String?
        get() = preferences.getString(USER_PHONE, "")
        set(value) = preferences.edit().putString(USER_PHONE, value).apply()

    var email: String?
        get() = preferences.getString(USER_EMAIL, "")
        set(value) = preferences.edit().putString(USER_EMAIL, value).apply()

    var profilePicture: String?
        get() = preferences.getString(USER_PROFILE, "")
        set(value) = preferences.edit().putString(USER_PROFILE, value).apply()
}