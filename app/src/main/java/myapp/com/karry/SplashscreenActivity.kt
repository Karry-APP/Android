package myapp.com.karry

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import myapp.com.karry.Modules.TokenManager

class SplashscreenActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val preferencesHelper = TokenManager(this)
            val token = preferencesHelper.deviceToken
            if(token == "") {
                Log.d("KARRY-DEBUG", "Launching LoginActivity because no Token found")
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            } else {
                Log.d("KARRY-DEBUG", "Launching HOME because a Token was found")
                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
