package myapp.com.karry.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import myapp.com.karry.R
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserManager

class SplashscreenActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private val delay: Long = 2000

    private val runnable: Runnable = Runnable {
        if (!isFinishing) {

            val isFirstOpen: Boolean = PreferenceManager.getDefaultSharedPreferences(this.baseContext)
                .getBoolean("data.source.prefs.FIRST_TIME", true)
            if (isFirstOpen) {
                PreferenceManager.getDefaultSharedPreferences(this.baseContext).edit()
                    .putBoolean("data.source.prefs.FIRST_TIME", false).apply()
                startActivity(Intent(applicationContext, IntroductionActivity::class.java))
                finish()
            } else {

                val token = UserManager(this).token ?: ""
                val id = UserManager(this).id ?: ""
                Log.d("USER_SYNC", "$token $id")

                if (token.isEmpty() || id.isEmpty()) {
                    UserManager(this).clear { startActivity(Intent(this, LoginActivity::class.java)) }
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        delayHandler = Handler()
        delayHandler!!.postDelayed(runnable, delay)
    }

    public override fun onDestroy() {
        if (delayHandler != null) {
            delayHandler!!.removeCallbacks(runnable)
        }
        super.onDestroy()
    }
}
