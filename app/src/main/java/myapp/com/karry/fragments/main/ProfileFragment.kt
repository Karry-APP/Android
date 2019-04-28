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
import myapp.com.karry.activities.UpdateProfileActivity
import myapp.com.karry.activities.UserTripsActivity
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_profile, container, false)
        v.logoutButton.setOnClickListener { logoutUser() }
        v.myProposals.setOnClickListener { redirectToMyRequestedTrips() }
        v.updateProfileLink.setOnClickListener { redirectToMyUpdateMyProfile() }
        v.userName.text =
            "${UserInfoManager(this.requireContext()).firstname} ${UserInfoManager(this.requireContext()).lastname}"

        Glide
            .with(v)
            .load(UserInfoManager(this.requireContext()).profilePicture)
            .circleCrop()
            .into(v.profilePicture)

        return v
    }

    override fun onStart() {
        super.onStart()
        userName.text = "${UserInfoManager(this.requireContext()).firstname} ${UserInfoManager(this.requireContext()).lastname}"
    }

    private fun redirectToMyUpdateMyProfile() {
        val intent = Intent(context, UpdateProfileActivity::class.java)
        startActivity(intent)
    }

    private fun redirectToMyRequestedTrips() {
        val intent = Intent(context, UserTripsActivity::class.java)
        startActivity(intent)
    }

    private fun logoutUser() {
        TokenManager(this.requireContext()).deviceToken = ""
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }
}


