package myapp.com.karry.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_trip_details.*
import kotlinx.android.synthetic.main.activity_trip_details.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.Trip
import myapp.com.karry.modules.TripsManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject

class TripDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        buttonOrderForm.setOnClickListener { startOrderForm() }

        val jsonTrip: String = intent.getStringExtra("TRIP")
        val trip = Gson().fromJson(jsonTrip, Trip::class.java)

        TripsManager.loadDetails(trip.id, { tripDetails: Trip ->
            runOnUiThread {
                textPDescription.text = tripDetails.description
                textCity2.text = tripDetails.departureCity
                textCountry2.text = tripDetails.departureCountry
                textCity.text = tripDetails.destinationCity
                textCountry.text = tripDetails.destinationCountry
                textPWeight.text = tripDetails.carryWeight
                textPMaxProduct.text = tripDetails.carryMaxAmount
                //textPDescription.text = tripDetails.carryTaxe
                textUserName.text = tripDetails.creator
            }
        }, {
            runOnUiThread {
            }
        })
    }

    private fun startOrderForm() {
        val jsonTrip: String = intent.getStringExtra("TRIP")
        val trip = Gson().fromJson(jsonTrip, Trip::class.java)

        val intent = Intent(this, OrderFormActivity::class.java)
        intent.putExtra("trip_id", trip.id)

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


}
