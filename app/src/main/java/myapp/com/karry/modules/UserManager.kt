package myapp.com.karry.modules

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import myapp.com.karry.entity.User

class UserManager(context: Context) {
    companion object {
        private const val USER_ID= "data.source.prefs.USER_ID"
        private const val USER_FIRSTNAME = "data.source.prefs.USER_FIRSTNAME"
        private const val USER_LASTNAME= "data.source.prefs.USER_LASTNAME"
        private const val USER_EMAIL= "data.source.prefs.USER_EMAIL"
        private const val USER_PHONE= "data.source.prefs.USER_PHONE"
        private const val USER_PROFILE= "data.sourve.prefs.USER_PROFILE"
        private const val USER_TOKEN = "data.source.prefs.USER_TOKEN"
        private const val USER_GOOGLE_TOK= "data.source.prefs.USER_GOOGLE_TOK"
        private const val USER_FACEBOOK_TOK= "data.sourve.prefs.USER_FACEBOOK_TOK"
    }

    fun syncUser(user: User, token: String, success: (user: User, token: String) -> Unit, failure: () -> Unit) {
        preferences.edit().putString(USER_ID, user._id).apply()
        preferences.edit().putString(USER_FIRSTNAME, user.firstname).apply()
        preferences.edit().putString(USER_LASTNAME, user.lastname).apply()
        preferences.edit().putString(USER_EMAIL, user.email).apply()
        preferences.edit().putString(USER_PHONE, user.phone).apply()
        preferences.edit().putString(USER_PROFILE, user.profilePicture).apply()
        preferences.edit().putString(USER_TOKEN, token).apply()
        success(user, token)
    }

    fun clear(success: () -> Unit) {
        preferences.edit().putString(USER_ID, null).apply()
        preferences.edit().putString(USER_FIRSTNAME, null).apply()
        preferences.edit().putString(USER_LASTNAME, null).apply()
        preferences.edit().putString(USER_EMAIL, null).apply()
        preferences.edit().putString(USER_PHONE, null).apply()
        preferences.edit().putString(USER_PROFILE, null).apply()
        preferences.edit().putString(USER_TOKEN, null).apply()
        success()
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var id: String?
        get() = preferences.getString(USER_ID, null)
        set(value) = preferences.edit().putString(USER_ID, value).apply()

    var firstname: String?
        get() = preferences.getString(USER_FIRSTNAME, null)
        set(value) = preferences.edit().putString(USER_FIRSTNAME, value).apply()

    var lastname: String?
        get() = preferences.getString(USER_LASTNAME, null)
        set(value) = preferences.edit().putString(USER_LASTNAME, value).apply()

    var email: String?
        get() = preferences.getString(USER_EMAIL, null)
        set(value) = preferences.edit().putString(USER_EMAIL, value).apply()

    var phone: String?
        get() = preferences.getString(USER_PHONE, null)
        set(value) = preferences.edit().putString(USER_PHONE, value).apply()

    var profilePicture: String?
        get() = preferences.getString(USER_PROFILE, null)
        set(value) = preferences.edit().putString(USER_PROFILE, value).apply()

    var token: String?
        get() = preferences.getString(USER_TOKEN, null)
        set(value) = preferences.edit().putString(USER_TOKEN, value).apply()

    var googleToken: String?
        get() = preferences.getString(USER_GOOGLE_TOK, null)
        set(value) = preferences.edit().putString(USER_GOOGLE_TOK, value).apply()

    var facebookToken: String?
        get() = preferences.getString(USER_FACEBOOK_TOK, null)
        set(value) = preferences.edit().putString(USER_FACEBOOK_TOK, value).apply()


}