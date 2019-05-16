package myapp.com.karry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_google_auth.*
import myapp.com.karry.R
import myapp.com.karry.entity.User
import myapp.com.karry.modules.UserManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class GoogleAuthActivity : AppCompatActivity() {

    private val RC_GOOGLE_SIGN_IN = 9001

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_auth)

        googleProgressBar.visibility = View.VISIBLE

        // Set Options
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account !== null) {

            //updateUserInfoManagerFromGoogleAccount(account)
            //goToMainActivity()

        } else {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken: String? = account?.idToken

            saveToken(idToken)

            val tokenAsJson = JSONObject().put("token", idToken).toString()

            if (idToken !== null) {
                UsersService.loginGoogle(tokenAsJson, { user, token ->
                    onUserLoggedIn(user, token)
                }, {
                    goToLoginActivity("Une erreur s'est produite 😱")
                })
            }
        } catch (e: ApiException) {
        }
    }

    private fun onUserLoggedIn(user: User, token: String?) {
        UserManager(this).syncUser(user, token!!, { user, token ->
            googleProgressBar.visibility = View.INVISIBLE
            goToMainActivity()
        }, {

        })
    }

    private fun saveToken(accessToken: String?) {
        UserManager(this).googleToken = accessToken ?: ""
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun goToLoginActivity(error: String?) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("error", error)
        startActivity(intent)
        finish()
    }
}
