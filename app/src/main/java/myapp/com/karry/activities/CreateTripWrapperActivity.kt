package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import myapp.com.karry.R
import myapp.com.karry.fragments.main.CityPickerFragment
import myapp.com.karry.fragments.main.CreateTripFragment
import myapp.com.karry.model.SharedViewModel

class CreateTripWrapperActivity : AppCompatActivity() {

    private lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_trip_wrapper)

        model = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        model.cleanValues()

        replaceFragment(CreateTripFragment())
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer2, fragment)
        fragmentTransaction.commit()
    }
}
