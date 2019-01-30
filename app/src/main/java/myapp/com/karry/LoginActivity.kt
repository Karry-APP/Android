package myapp.com.karry

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import myapp.com.karry.Modules.TokenManager
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginButton.setOnClickListener { loginUser() }
    }

    private fun userInfoAsJson(): String {
        val email = inputEmail.text.toString()
        val password = inputPassword.text.toString()

        val userObject= JSONObject()
        userObject.put("email",email)
        userObject.put("password",password)
        return userObject.toString()
    }

    private fun loginUser() {
        loginButton.visibility = View.INVISIBLE
        loginProgress.visibility = View.VISIBLE

        val baseUrl =  "https://karry-dev.herokuapp.com/users/login"
        val client = OkHttpClient()
        val appType = MediaType.parse("application/json; charset=utf-8")

        val body = RequestBody.create(appType, userInfoAsJson())
        val request = Request.Builder().url(baseUrl).post(body).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                loginUserFailed()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code() == 200) {
                    loginUserSucceed(response)
                } else {
                    loginUserFailed()
                }
            }
        })
    }

    fun loginUserFailed() {
        runOnUiThread {
            errorMessage.text = getString(R.string.loginActivity_failedLogin)
            loginButton.visibility = View.VISIBLE
            loginProgress.visibility = View.INVISIBLE
            Toast.makeText(this.baseContext, "Login failed", Toast.LENGTH_LONG).show()
            Log.d("KARRY-DEBUG", "Login failed")
        }
    }

    fun loginUserSucceed(response: Response) {
        loginButton.visibility = View.INVISIBLE
        val token = response.header("x-auth")
        val preferencesHelper = TokenManager(baseContext)
        preferencesHelper.deviceToken = token
        Log.d("KARRY-DEBUG", "Login Succeed")
        startActivity(Intent(baseContext, HomeActivity::class.java))
        finish()
    }


}
