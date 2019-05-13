package myapp.com.karry.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_trip.*
import myapp.com.karry.R

class UserTripActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_trip)

        commanditaires.setOnClickListener { loadBackers(this) }
    }

    private fun loadBackers(c: Context) {
        val intent = Intent(c, UserTripBackersActivity::class.java)
        c.startActivity(intent)
    }
}
