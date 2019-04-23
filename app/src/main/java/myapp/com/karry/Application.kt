package myapp.com.karry

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

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

        val TAG = "NotificationMessaging"

        // Register a new token for notifications
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result?.token

                Log.d(TAG, token)
                Toast.makeText(this.applicationContext, token, Toast.LENGTH_SHORT).show()
            })
    }
}