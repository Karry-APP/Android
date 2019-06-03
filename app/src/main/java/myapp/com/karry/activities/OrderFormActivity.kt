package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import myapp.com.karry.R
import myapp.com.karry.fragments.orderForm.OrderFormFragment

class OrderFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_form)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        if (savedInstanceState == null) {
            replaceFragment(OrderFormFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.cityPickerContainer, fragment)
        fragmentTransaction.commit()
    }

}
