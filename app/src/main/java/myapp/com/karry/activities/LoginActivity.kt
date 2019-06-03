package myapp.com.karry.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import myapp.com.karry.R
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService
import okhttp3.Response
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var userPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener { loginUser() }
        loginDontHaveAccount.setOnClickListener { startRegisterActivity() }
        loginForgotPassword.setOnClickListener { forgotPassword() }
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
        userEmail = email
        userPassword = password
        return true
    }

    private fun loginUser() {
        if (validateForm()) {
            setStartLoadingUi()
            UsersService.login(userInfoAsJson(), { response -> onSuccess(response) }, { onError() })
        }
    }

    private fun onSuccess(response: Response) = runOnUiThread {
        val jsonData: String = response.body()!!.string()
        val userObj = JSONObject(jsonData)
        TokenManager(baseContext).deviceToken = response.header("x-auth")
        UserInfoManager(baseContext).id = userObj.getString("id")
        UserInfoManager(baseContext).firstname = userObj.getString("firstname")
        UserInfoManager(baseContext).lastname = userObj.getString("lastname")
        UserInfoManager(baseContext).phone = userObj.getString("phone")
        UserInfoManager(baseContext).email = userObj.getString("email")
        UserInfoManager(baseContext).profilePicture = userObj.getString("profilePicture")
        startMainActivity()
    }

    private fun onError() = runOnUiThread {
        loginError.text = getString(R.string.LoginActivity_loginError_text)
        loginButton.text = "Se connecter"
        setFinishLoadingUi()
    }

    private fun setStartLoadingUi() {
        loginButton.visibility = View.INVISIBLE
        loginProgress.visibility = View.VISIBLE

    }
    private fun setFinishLoadingUi() {
        loginButton.visibility = View.VISIBLE
        loginProgress.visibility = View.INVISIBLE
    }

    private fun forgotPassword() {
        Snackbar.make(loginActivityLayout, "Lancement du processus de récupération de mot de passe", Snackbar.LENGTH_LONG).show()
    }

    private fun startRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
