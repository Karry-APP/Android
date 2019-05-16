package myapp.com.karry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_register_profil.*
import myapp.com.karry.R
import myapp.com.karry.fragments.register.RegisterProfilFragment
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var userPassword: String

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        if (savedInstanceState == null) {
            replaceFragment(RegisterProfilFragment())
        }
    }

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private fun validateForm(): Boolean {
        val email = registerEmail.text.toString()
        val password = registerPassword.text.toString()
        userEmail = email
        userPassword = password
        return true
    }

    private fun userInfoAsJson(): String {
        val randomFirstName = (1..8)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        val randomLastName = (1..8)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
        val userObject = JSONObject()
        userObject.put("firstname", randomFirstName)
        userObject.put("lastname", randomLastName)
        userObject.put("email", userEmail)
        userObject.put("password", userPassword)
        userObject.put("phone", "0000000000")
        return userObject.toString()
    }

    private fun registerUser() {
        if (validateForm()) {
            registerButton.visibility = View.INVISIBLE
            registerProgress.visibility = View.VISIBLE
            UsersService.register(userInfoAsJson(), { user, token ->
                TokenManager(baseContext).deviceToken = token
                UserManager(baseContext).id = user._id
                UserManager(baseContext).firstname = user.firstname
                UserManager(baseContext).lastname = user.lastname
                UserManager(baseContext).phone = user.phone
                UserManager(baseContext).email = user.email
                UserManager(baseContext).profilePicture = user.profilePicture
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
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
