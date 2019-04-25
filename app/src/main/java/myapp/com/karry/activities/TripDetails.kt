package myapp.com.karry.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_trip_details.*
import myapp.com.karry.R
import myapp.com.karry.entity.Trip
import myapp.com.karry.model.SharedViewModel
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.TripsManager
import java.lang.Exception

class TripDetails : AppCompatActivity() {
    private lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        model = this.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val tripId:String = intent.getStringExtra("EVENT_ID")
        val token = TokenManager(this).deviceToken.toString()

        closeDetailTrip.setOnClickListener { replaceFragment() }

        TripsManager.loadDetails(tripId, token, { tripDetails: Trip ->
            runOnUiThread {
                karryTax.text = tripDetails.carryTaxe
                availableWeight.text = tripDetails.carryWeight
                maxAmount.text = tripDetails.carryMaxAmount
                tripDepartureCityDetails.text = tripDetails.departureCity
                tripDestinationCity.text = tripDetails.destinationCity
                descriptionValue.text = tripDetails.description

                linkTravelerProfile.setOnClickListener { startTravelerProfileActivity() }

            }
        }, {
            runOnUiThread {
            }
        })
    }

    private fun startTravelerProfileActivity() {
        val intent = Intent(this, TravelerProfileActivity::class.java)
        startActivity(intent)
    }

    private fun replaceFragment() {
        model.cleanTripsList()
        onBackPressed()
    }


}
