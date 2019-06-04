package myapp.com.karry.fragments.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_register_profil.*
import kotlinx.android.synthetic.main.fragment_register_profil.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.LoginActivity

class RegisterProfilFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_register_profil, container, false)

        v.registerHaveAccount.setOnClickListener { startLoginActivity() }
        v.registerButton.setOnClickListener { launchFragment(CompleteProfilFragment()) }

        return v
    }

    private fun launchFragment(fragment: Fragment) {
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()

        activity?.intent!!.putExtra("userEmail", email)
        activity?.intent!!.putExtra("userPassword", password)

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    private fun startLoginActivity() {
        startActivity(Intent(this.context, LoginActivity::class.java))
        activity?.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

    }
}