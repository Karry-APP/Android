package myapp.com.karry.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_trip_details.*
import myapp.com.karry.R
import myapp.com.karry.entity.Trip
import myapp.com.karry.fragments.main.SearchResultsFragment
import myapp.com.karry.model.SharedViewModel
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.TripsManager
import myapp.com.karry.modules.UserInfoManager

class TripDetails : AppCompatActivity() {
    private lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        val token = TokenManager(this).deviceToken.toString()
        val jsonTrip: String = intent.getStringExtra("TRIP")
        val trip = Gson().fromJson(jsonTrip, Trip::class.java)
        model = this.run { ViewModelProviders.of(this).get(SharedViewModel::class.java) }

        buttonOrderForm.setOnClickListener { startOrderForm() }
        closeDetailTrip.setOnClickListener { replaceFragment() }

        TripsManager.loadDetails(trip.id,token, { tripDetails: Trip ->
            runOnUiThread {
                val fullname = "${trip.owner.firstname} ${trip.owner.lastname}"
                userName.text = fullname
                userRate.text = trip.owner.ratings
                searchEndDate.text = trip.arrivalDate
                karryTax.text = tripDetails.carryTaxe
                availableWeight.text = tripDetails.carryWeight

                if (tripDetails.carryVolume !== "") {
                    maxAmount.text = when (tripDetails.carryVolume) {
                        "1" -> "PETIT"
                        "2" -> "MOYEN"
                        else -> "GRAND"
                    }
                }
                tripDepartureCityDetails.text = tripDetails.departureCity.capitalize()
                tripDestinationCity.text = tripDetails.destinationCity.capitalize()
                descriptionValue.text = tripDetails.description
                linkTravelerProfile.setOnClickListener { startTravelerProfileActivity() }

                if (tripDetails.owner._id == UserInfoManager(this).id) {
                    buttonOrderForm.visibility = View.INVISIBLE
                }

                Glide
                    .with(this)
                    .load("https://" + tripDetails.owner.profilePicture)
                    .circleCrop()
                    .into(userAvatar)
            }
        }, {
            runOnUiThread {
            }
        })


    }

  private fun startTravelerProfileActivity() {
      val jsonTrip: String = intent.getStringExtra("TRIP")
      val trip = Gson().fromJson(jsonTrip, Trip::class.java)
      val intent = Intent(this, TravelerProfileActivity::class.java)

      intent.putExtra("ownerName", trip.owner.firstname + " " + trip.owner.lastname)
      intent.putExtra("ownerRatings", trip.owner.ratings)
      intent.putExtra("ownerDescription", trip.owner.description)
      intent.putExtra("ownerCreatedTripsCount", trip.owner.createdTripsCount)
      intent.putExtra("ownerJoinedTripsCount", trip.owner.joinedTripsCount)

      startActivity(intent)
      overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
  }

  private fun startOrderForm() {
        val jsonTrip: String = intent.getStringExtra("TRIP")
        val trip = Gson().fromJson(jsonTrip, Trip::class.java)
        val intent = Intent(this, OrderFormActivity::class.java)
        intent.putExtra("trip_id", trip.id)
        intent.putExtra("TRIP", jsonTrip)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
  }

  private fun replaceFragment() {
      model.cleanTripsList()
      finish()
  }
}
