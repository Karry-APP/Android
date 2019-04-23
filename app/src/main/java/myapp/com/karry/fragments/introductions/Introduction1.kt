package myapp.com.karry.fragments.introductions


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_introduction1.view.*

import myapp.com.karry.R
import myapp.com.karry.activities.LoginActivity

class Introduction1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View =  inflater.inflate(R.layout.fragment_introduction1, container, false)
        v.passLabel.setOnClickListener {
            startLoginActivity()
        }
        return v
    }

    fun startLoginActivity() {
        this.startActivity(Intent(this.context, LoginActivity::class.java))
        activity?.finish()
    }
}
