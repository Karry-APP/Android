package myapp.com.karry.fragments.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_photo_profil.view.*
import kotlinx.android.synthetic.main.fragment_complete_profil.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.MainActivity

class AddPhotoProfilFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_add_photo_profil, container, false)

        v.link_ignore_photo.setOnClickListener { startMainActivity() }

        return v
    }

    private fun startMainActivity() {
        startActivity(Intent(this.context, MainActivity::class.java))
        activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}