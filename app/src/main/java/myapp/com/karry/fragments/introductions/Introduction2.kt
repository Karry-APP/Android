package myapp.com.karry.fragments.introductions


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_introduction2.view.*

import myapp.com.karry.R
import myapp.com.karry.activities.LoginActivity

class Introduction2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_introduction2, container, false)
        v.startButton.setOnClickListener {
            startLoginActivity()
        }
        return v
    }

    fun startLoginActivity() {
        this.startActivity(Intent(this.context, LoginActivity::class.java))
        activity?.finish()
    }


}
