package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_rating_form.*
import myapp.com.karry.R
import myapp.com.karry.entity.Trip
import myapp.com.karry.fragments.rating.RatingFormFragment
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.TripsService
import myapp.com.karry.network.UsersService

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        val tripId = "5caa7048a5350522bccad2e0" //il faut le récuperer le manière dynamique sur la notification cliqué
        val userId = "5ca605d6d1802f00174e93db"

        loadTripData()
        loadUserData()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        if (savedInstanceState == null) {
            replaceFragment(RatingFormFragment())

        }
    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun loadTripData() {

        val tripId = "5caa7048a5350522bccad2e0" //TODO il faut le récuperer le manière dynamique sur la notification cliqué
        val token = TokenManager(this).deviceToken.toString()

        TripsService.tripById(tripId, token, {
            if (it.id.isEmpty()) {
                Log.d("yay", "Empty")
            } else {
                Log.d("yay", "Ca a marché")
                val trip = it
                intent.putExtra("endDate", "TODO") //TODO à remplacer par la date de retour quand on l'aura
                intent.putExtra("ownerId", trip.owner._id)
                intent.putExtra("backerList", trip.joinList)
            }
        }, {
            Log.d("yoy", "Something bad happened")
        })
    }

    private fun loadUserData() {
        val userId = "5ca605d6d1802f00174e93db" //TODO il faut le récuperer le manière dynamique sur la notification cliqué
        val token = TokenManager(this).deviceToken.toString()

        UsersService.userById(userId, token, {
            if (it._id.isEmpty()) {
                Log.d("yay", "Empty")
            } else {
                Log.d("yay", "Ca a marché")
                val actualUser = it
                intent.putExtra("actualUserId", actualUser._id)
                intent.putExtra("actualFirstname", actualUser.firstname)
                intent.putExtra("actualLastname", actualUser.lastname)
                intent.putExtra("actualProfilePicture", actualUser.profilePicture)
                intent.putExtra("actualRatings", actualUser.ratings)
            }
        }, {
            Log.d("yoy", "Something bad happened")
        })
    }

}
