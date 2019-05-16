package myapp.com.karry

import android.app.Application
import android.content.Context
import android.util.Log
import com.facebook.FacebookSdk
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class MainApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = MainApplication.applicationContext()
        FirebaseApp.initializeApp(context)
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                val firebaseId = task.result?.token
                val userObject = JSONObject()
                if(UserManager(this).id != null && TokenManager(this).deviceToken != null) {

                    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1Y2E1ZThjNGE5NjI2NDAwMTdjZjk3YmUiLCJhY2Nlc3MiOiJhdXRoIiwiaWF0IjoxNTU0Mzc2OTAwfQ.pDpkarXCW1ghSIFBJG34mEfewXRy5vnvyFgMBzEQZNY"
                    val userId = "5ca5e8c4a962640017cf97be"
                    userObject.put("firebaseId", firebaseId)

                    UsersService.updateUser(token, userId, userObject.toString(), { user ->
                        Log.d("USER", "User succesfully updated")
                    }, {
                        Log.d("USER", "User error updated")

                    })
                }
            })
    }
}