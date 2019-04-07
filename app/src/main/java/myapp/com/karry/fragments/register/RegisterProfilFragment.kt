package myapp.com.karry.fragments.register

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_register_profil.*
import kotlinx.android.synthetic.main.fragment_register_profil.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.LoginActivity
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

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

    //private lateinit var userEmail: String
    //private lateinit var userPassword: String

    private fun validateForm(): Boolean {
        //val firstname = registerFirstname.text.toString()
        //val lastname = registerLastname.text.toString()
        //val phone = registerPhone.text.toString()
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()
        //val confirmPassword = registerConfirmPassword.text.toString()

        // TODO: Check is firstname is valid
        //userFirstname = firstname

        // TODO: Check is lastname is valid
        //userLastname = lastname

        // TODO: Check is phone is valid
        //userPhone = phone

        // TODO: Check is email is valid
        //userEmail = email

        // TODO: Check is password is valid
        //userPassword = password

        return true // or false
    }

    //private fun userInfoAsJson(): String {
    //    val userObject = JSONObject()
    //    userObject.put("firstname", 'e')
    //    userObject.put("lastname", 'e')
    //    userObject.put("email", userEmail)
    //    userObject.put("password", userPassword)
    //    userObject.put("phone", "0000000000")
    //    return userObject.toString()
    //}
//
    //private fun registerUser() {
    //    if (validateForm()) {
//
    //        registerButton.visibility = View.INVISIBLE
    //        registerProgress.visibility = View.VISIBLE
//
    //        activity?.intent!!.putExtra("userEmail", userEmail)
    //        activity?.intent!!.putExtra("userPassword", userPassword)
    //        launchFragment(CompleteProfilFragment())
//
    //        // Registering User in db
    //        UsersService.register(userInfoAsJson(), { user, token ->
//
    //            val context: Context = this.requireContext()
//
//
    //            TokenManager(context).deviceToken = token
    //            UserInfoManager(context).id = user._id
    //            UserInfoManager(context).firstname = user.firstname
    //            UserInfoManager(context).lastname = user.lastname
    //            UserInfoManager(context).phone = user.phone
    //            UserInfoManager(context).email = user.email
    //            UserInfoManager(context).profilePicture = user.profilePicture
//
    //        }, {
    //            activity?.runOnUiThread {
    //                registerError.text = getString(R.string.LoginActivity_loginError_text)
    //                registerButton.visibility = View.VISIBLE
    //                registerProgress.visibility = View.INVISIBLE
//
    //                activity?.intent!!.putExtra("userEmail", userEmail)
    //                activity?.intent!!.putExtra("userPassword", userPassword)
    //                launchFragment(CompleteProfilFragment())
    //            }
    //        })
    //    }
    //}
}