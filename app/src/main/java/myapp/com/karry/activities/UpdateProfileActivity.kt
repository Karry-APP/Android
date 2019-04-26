package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_update_profile.*
import myapp.com.karry.R
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class UpdateProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        fillFieldsInput()

        updateProfileFormButton.setOnClickListener {
            updateProfile()
        }
        closeProfileButton.setOnClickListener {
            launchFragment()
        }
    }

    private fun fillFieldsInput() {
        userFirstname.setText(UserInfoManager(baseContext).firstname)
        userLastname.setText(UserInfoManager(baseContext).lastname)
        userEmail.setText(UserInfoManager(baseContext).email)
        userPhone.setText(UserInfoManager(baseContext).phone)
    }

    private fun formatFieldsValuesToJSON(): JSONObject {
        val userObject= JSONObject()
        userObject.put("firstname",userFirstname.text)
        userObject.put("lastname",userLastname.text)
        userObject.put("email",userEmail.text)
        userObject.put("phone",userPhone.text)
        userObject.put("userID",UserInfoManager(baseContext).id)

        return userObject
    }

    private fun updateProfile() {
        val updateProfilePayload = formatFieldsValuesToJSON()
        val token = TokenManager(baseContext).deviceToken.toString()
        updateProfileFormButton.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        UsersService.patchProfile(updateProfilePayload, token, {
            Log.d("PAYLOAD", it.firstname)
            UserInfoManager(baseContext).firstname = it.firstname
            UserInfoManager(baseContext).lastname = it.lastname
            UserInfoManager(baseContext).email = it.email
            UserInfoManager(baseContext).phone = it.phone
            // TODO: GET password field form server to display it.
            runOnUiThread {
                updateProfileFormButton.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
            }
            finish()
        }, {
            Log.d("failure", "Cannot patch profile")
        })
    }

    private fun launchFragment() {
        onBackPressed()
    }
}

