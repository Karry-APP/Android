package myapp.com.karry.fragments.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_complete_profil.*
import kotlinx.android.synthetic.main.fragment_complete_profil.view.*
import myapp.com.karry.R
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class CompleteProfilFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_complete_profil, container, false)

        v.button_complete_profile.setOnClickListener { registerUser() }
        return v
    }

    private fun launchFragment(fragment: Fragment) {

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.cityPickerContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    private fun registerUser() {

        val firstname = editFirstnameProfil.text.toString()
        val lastname = editLastnameProfil.text.toString()
        val phone = editTextPhoneProfil.text.toString()

        activity?.intent!!.putExtra("userFirstName", firstname)
        activity?.intent!!.putExtra("userLastName", lastname)
        activity?.intent!!.putExtra("userPhone", phone)

        button_complete_profile.visibility = View.INVISIBLE
        completeRegisterProgress.visibility = View.VISIBLE

        // Registering User in db
        UsersService.register(userInfoAsJson(), { user, token ->

            val context: Context = this.requireContext()

            TokenManager(context).deviceToken = token
            UserInfoManager(context).id = user._id
            UserInfoManager(context).firstname = user.firstname
            UserInfoManager(context).lastname = user.lastname
            UserInfoManager(context).phone = user.phone
            UserInfoManager(context).email = user.email
            UserInfoManager(context).profilePicture = user.profilePicture

            activity?.runOnUiThread {

                launchFragment(AddPhotoProfilFragment())
            }
        }, {

            activity?.runOnUiThread {
                completeRegisterError.text = getString(R.string.LoginActivity_loginError_text)
                button_complete_profile.visibility = View.VISIBLE
                completeRegisterProgress.visibility = View.INVISIBLE
            }

        })
    }

    private fun userInfoAsJson(): String {
        val userObject = JSONObject()
        userObject.put("firstname", activity?.intent!!.getStringExtra("userFirstName"))
        userObject.put("lastname", activity?.intent!!.getStringExtra("userLastName"))
        userObject.put("email", activity?.intent!!.getStringExtra("userEmail"))
        userObject.put("password", activity?.intent!!.getStringExtra("userPassword"))
        userObject.put("phone", activity?.intent!!.getStringExtra("userPhone"))
        return userObject.toString()
    }
}