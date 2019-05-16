package myapp.com.karry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_facebook_auth.*
import myapp.com.karry.R
import myapp.com.karry.entity.User
import myapp.com.karry.modules.UserManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject
import java.util.*

class FacebookAuthActivity : AppCompatActivity() {

    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_auth)

        facebookProgressBar.visibility = View.VISIBLE

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Log.d("FB_TOKEN", "Facebook token: %s" + loginResult.accessToken.token)
                val accessToken = loginResult.accessToken.token
                saveToken(accessToken)

                val request = GraphRequest.newMeRequest(loginResult.accessToken) { fbData, response ->
                    facebookProgressBar.visibility = View.INVISIBLE

                    val jsonUser = JSONObject()
                    jsonUser.put("firstname", fbData.getString("first_name"))
                    jsonUser.put("lastname", fbData.getString("last_name"))
                    jsonUser.put("email", fbData.getString("email"))
                    jsonUser.put("profilePicture", "https://graph.facebook.com/" + fbData.getString("id") + "/picture?type=large")

                    UsersService.loginFacebook(jsonUser.toString(), { user, token ->
                        onUserLoggedIn(user, token)
                    }, {
                        goToLoginActivity("Une erreur s'est produite 😱")
                    })
                }

                val parameters = Bundle()
                parameters.putString("fields", "id,first_name,last_name,email,gender")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                Log.d("FB_TOKEN", "Facebook onCancel.")
            }

            override fun onError(error: FacebookException) {
                Log.d("FB_TOKEN", "Facebook onError")
            }
        })
    }


    private fun onUserLoggedIn(user: User, token: String?) {
        UserManager(this).syncUser(user, token!!, { user, token ->
            Log.d("USER_SYNC", user.toString())
            facebookProgressBar.visibility = View.INVISIBLE
            goToMainActivity()
        }, {

        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun saveToken(accessToken: String) {
        UserManager(this).facebookToken = accessToken
    }

    private fun goToLoginActivity(error: String?) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("error", error)
        startActivity(intent)
        finish()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
