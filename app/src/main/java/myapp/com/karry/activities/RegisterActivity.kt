package myapp.com.karry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import myapp.com.karry.R
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var userFirstname: String
    private lateinit var userLastname: String
    private lateinit var userEmail: String
    private lateinit var userPassword: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        registerHaveAccount.setOnClickListener { startLoginActivity() }
        registerButton.setOnClickListener { registerUser() }
    }

    private fun validateForm(): Boolean {
        val firstname = registerFirstname.text.toString()
        val lastname = registerLastname.text.toString()
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()
        val confirmPassword = registerConfirmPassword.text.toString()

        // TODO: Check is firstname is valid
        userFirstname = firstname

        // TODO: Check is lastname is valid
        userLastname = lastname

        // TODO: Check is email is valid
        userEmail = email

        // TODO: Check is password is valid
        userPassword = password

        return true // or false
    }

    private fun userInfoAsJson(): String {
        val userObject = JSONObject()
        userObject.put("firstname", userFirstname)
        userObject.put("lastname", userLastname)
        userObject.put("email", userEmail)
        userObject.put("password", userPassword)
        return userObject.toString()
    }

    private fun registerUser() {
        if (validateForm()) {

            registerButton.visibility = View.INVISIBLE
            registerProgress.visibility = View.VISIBLE

            // Registering User in db
            UsersService.register(userInfoAsJson(), { response ->
                val jsonData: String = response.body()!!.string()
                val userObj = JSONObject(jsonData)

                TokenManager(baseContext).deviceToken = response.header("x-auth")
                UserInfoManager(baseContext).id = userObj.getString("_id")
                UserInfoManager(baseContext).firstname = userObj.getString("firstname")
                UserInfoManager(baseContext).lastname = userObj.getString("lastname")
                UserInfoManager(baseContext).email = userObj.getString("email")

                startMainActivity()
            }, {
                runOnUiThread {
                    registerError.text = getString(R.string.LoginActivity_loginError_text)
                    registerButton.visibility = View.VISIBLE
                    registerProgress.visibility = View.INVISIBLE
                }
            })
        }
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
