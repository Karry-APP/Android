package myapp.com.karry.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import myapp.com.karry.R
import myapp.com.karry.modules.TokenManager

class SplashscreenActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private val delay: Long = 2000

    private val runnable: Runnable = Runnable {
        if (!isFinishing) {
            val token = TokenManager(this).deviceToken
            if(token == "") {
                val intent1 = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent1)
                finish()
            } else {
                val intent1 = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent1)
                finish()
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
