package myapp.com.karry.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import myapp.com.karry.R
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var userPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUiListeners()
    }

    // Listeners
    private fun setUiListeners() {
        loginButton.setOnClickListener { loginUser() }
        loginDontHaveAccount.setOnClickListener { startRegisterActivity() }
        loginForgotPassword.setOnClickListener { forgotPassword() }
        facebookLoginButton.setOnClickListener { goToFacebookAuthActivity() }
        googleLoginButton.setOnClickListener { goToGoogleAuthActivity() }
    }

    private fun userInfoAsJson(): String {
        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()

        val userObject= JSONObject()
        userObject.put("email",email)
        userObject.put("password",password)
        return userObject.toString()
    }

    private fun validateForm(): Boolean {
        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()

        // TODO: Check is email is valid
        userEmail = email

        // TODO: Check is password is valid
        userPassword = password
        return true
    }

    private fun loginUser() {
        if (validateForm()) {
            loginButton.visibility = View.INVISIBLE
            loginProgress.visibility = View.VISIBLE
            UsersService.login(userInfoAsJson(), { response ->
                val jsonData: String = response.body()!!.string()
                val userObj = JSONObject(jsonData)
                TokenManager(baseContext).deviceToken = response.header("x-auth")
                UserManager(baseContext).id = userObj.getString("id")
                UserManager(baseContext).firstname = userObj.getString("firstname")
                UserManager(baseContext).lastname = userObj.getString("lastname")
                UserManager(baseContext).phone = userObj.getString("phone")
                UserManager(baseContext).email = userObj.getString("email")
                UserManager(baseContext).profilePicture = userObj.getString("profilePicture")

                startMainActivity()
            }, {
                runOnUiThread {
                    loginError.text = getString(R.string.LoginActivity_loginError_text)
                    loginProgress.visibility = View.INVISIBLE
                    loginButton.text = "Se connecter"
                }
            })
        }
    }

    private fun forgotPassword() {
        Snackbar.make(loginActivityLayout, "Lancement du processus de récupération de mot de passe", Snackbar.LENGTH_LONG).show()
    }

    private fun startRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // Go to
    private fun goToGoogleAuthActivity() {
        startActivity(Intent(this, GoogleAuthActivity::class.java))
        finish()
    }

    private fun goToFacebookAuthActivity() {
        startActivity(Intent(this, FacebookAuthActivity::class.java))
        finish()
    }
}
