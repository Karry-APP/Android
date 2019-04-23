package myapp.com.karry.fragments.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import myapp.com.karry.activities.LoginActivity
import myapp.com.karry.R
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_profile, container, false)
        v.profileLogout.setOnClickListener { logoutUser() }

        v.profileFirstname.text = UserInfoManager(this.requireContext()).firstname
        v.profileLastname.text = UserInfoManager(this.requireContext()).lastname
        v.profileEmail.text = UserInfoManager(this.requireContext()).email

        Glide
            .with(v)
            .load(UserInfoManager(this.requireContext()).profilePicture)
            .circleCrop()
            .into(v.profilePicture)

        return v
    }

    private fun logoutUser() {
        profileLogout.visibility = View.INVISIBLE
        profileProgress.visibility = View.VISIBLE

        val token: String = TokenManager(this.requireContext()).deviceToken ?: ""

        UsersService.logout(token, {
            TokenManager(this.requireContext()).deviceToken = ""
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }, {
            activity?.runOnUiThread {
                profileLogout.visibility = View.VISIBLE
                profileProgress.visibility = View.INVISIBLE
            }
        })
    }
}


