package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_asking_contact.*
import myapp.com.karry.R

class AskingContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asking_contact)
        acceptButton.setOnClickListener { onAccept() }
        denyButton.setOnClickListener { onDeny() }
    }

    private fun onAccept() {

    }

    private fun onDeny() {

    }
}
