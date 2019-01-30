package myapp.com.karry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_trip_form.*
import myapp.com.karry.R
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.TripsService
import org.json.JSONObject

class TripFormActivity : AppCompatActivity() {

    private lateinit var tripDescription: String
    private lateinit var tripDestinationCity: String
    private lateinit var tripDestinationCountry: String
    private var tripCarryWeight: Int = 0
    private var tripCarryMaxAmount: Int = 0
    private var tripCarryTaxe: Int = 0
    private lateinit var tripCreator: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_form)


        val items = arrayOf("paris", "berlin", "tokyo", "Lille", "Nantes", "Bordeaux")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        tripFormDestinationCity.adapter = adapter

        tripFormButton.setOnClickListener { createTrip() }
    }

    private fun validateForm(): Boolean {
        val description = tripFormDescription.text.toString()
        val destinationCity = tripFormDestinationCity.selectedItem.toString()
        val destinationCountry = "FRANCE"
        val carryWeight = tripFormCarryWeight.text.toString().toInt()
        val carryMaxAmount= tripFormCarryMaxAmount.text.toString().toInt()
        val carryTaxe= tripFormCarryTaxe.text.toString().toInt()



        val creator = UserInfoManager(this.applicationContext).id

        // TODO: Check is description is valid
        tripDescription = description

        // TODO: Check is destinationCity is valid
        tripDestinationCity = destinationCity

        // TODO: Check is destinationCountry is valid
        tripDestinationCountry = destinationCountry

        // TODO: Check is carryWeight is valid
        tripCarryWeight = carryWeight

        // TODO: Check is carryMaxAmount is valid
        tripCarryMaxAmount = carryMaxAmount

        // TODO: Check is carryTaxe is valid
        tripCarryTaxe = carryTaxe


        tripCreator = if(!creator.isNullOrEmpty()) creator else ""

        return true // or false
    }

    private fun tripInfoAsJson(): String {
        val tripObject = JSONObject()
        tripObject.put("description", tripDescription)
        tripObject.put("destinationCity", tripDestinationCity)
        tripObject.put("destinationCountry", tripDestinationCountry)
        tripObject.put("carryWeight", tripCarryWeight)
        tripObject.put("carryMaxAmount", tripCarryMaxAmount)
        tripObject.put("carryTaxe", tripCarryTaxe)
        tripObject.put("creator", tripCreator)
        return tripObject.toString()
    }

    fun createTrip() {
        if (validateForm()) {
            tripFormButton.visibility = View.INVISIBLE
            tripFormProgress.visibility = View.VISIBLE

            TripsService.create(tripInfoAsJson(), { response ->
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
