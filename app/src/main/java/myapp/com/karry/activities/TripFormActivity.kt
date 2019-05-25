package myapp.com.karry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_trip_form.*
import myapp.com.karry.R
import myapp.com.karry.modules.EditTextChecker
import myapp.com.karry.modules.SpinnerChecker
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.TripsService
import org.json.JSONObject

class TripFormActivity : AppCompatActivity() {

    private lateinit var tripDescription: String
    private lateinit var tripDepartureCity: String
    private lateinit var tripDepartureCountry: String
    private lateinit var tripDestinationCity: String
    private lateinit var tripDestinationCountry: String
    private var tripCarryWeight: Int = 0
    private var tripCarryMaxAmount: Int = 0
    private var tripCarryTaxe: Int = 0
    private lateinit var tripOwner: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_form)

        val adapterDepartureCities = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, SpinnerChecker.itemsDeparture)
        val adapterDestinationCities = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, SpinnerChecker.itemsDestination)
        tripFormDepartureCity.adapter = adapterDepartureCities
        tripFormDestinationCity.adapter = adapterDestinationCities

        tripFormButton.setOnClickListener { createTrip() }
    }

    private fun validateForm(): Boolean {

        val isValidDescription = EditTextChecker(tripFormDescription)
            .isRequired()
            .minLength(10, "Votre description est trop courte")
            .maxLength(100, "Votre description est trop longue")
            .check { tripDescription = it }

        val isValidDestinationCity = SpinnerChecker(tripFormDepartureCity)
            .isRequired()
            .notEqualTo(SpinnerChecker.itemsDeparture[0])
            .check { tripDestinationCity = it }

        val isValidDepartureCity = SpinnerChecker(tripFormDestinationCity)
            .isRequired()
            .notEqualTo(SpinnerChecker.itemsDestination[0])
            .check { tripDepartureCity = it }


        // TODO: Check if departure city !== destination city

        // TODO: Check carry Weight
        // TODO: Check carry MaxAmount
        // TODO: Check carry Taxe

        tripDestinationCountry = "France"
        tripDepartureCountry = "France"
        tripCarryWeight = tripFormCarryWeight.text.toString().toInt()
        tripCarryMaxAmount = tripFormCarryMaxAmount.text.toString().toInt()
        tripCarryTaxe = tripFormCarryTaxe.text.toString().toInt()
        tripOwner = UserInfoManager(this.baseContext).id!!

        if(isValidDescription && isValidDestinationCity && isValidDepartureCity) {
            return true
        }
        return false
    }

    private fun tripInfoAsJson(): String {
        val tripObject = JSONObject()
        tripObject.put("description", tripDescription)
        tripObject.put("departureCity", tripDepartureCity)
        tripObject.put("departureCountry", tripDepartureCountry)
        tripObject.put("destinationCity", tripDestinationCity)
        tripObject.put("destinationCountry", tripDestinationCountry)
        tripObject.put("carryWeight", tripCarryWeight)
        tripObject.put("carryMaxAmount", tripCarryMaxAmount)
        tripObject.put("carryTaxe", tripCarryTaxe)
        tripObject.put("owner", tripOwner)
        return tripObject.toString()
    }

    private fun createTrip() {
        if (validateForm()) {
            tripFormButton.visibility = View.INVISIBLE
            tripFormProgress.visibility = View.VISIBLE

            TripsService.create(tripInfoAsJson(), {
                startMainActivity()
            }, {
                runOnUiThread {
                    tripFormProgress.visibility = View.INVISIBLE
                    tripFormButton.visibility = View.VISIBLE
                }
            })
        }
    }
    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
