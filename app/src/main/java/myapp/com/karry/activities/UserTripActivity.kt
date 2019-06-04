package myapp.com.karry.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_user_trip.*
import myapp.com.karry.R
import myapp.com.karry.adapters.CardBackersAdapter
import myapp.com.karry.entity.Trip
import myapp.com.karry.entity.User
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.TripsService
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Rect
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import myapp.com.karry.model.SharedViewModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList


class UserTripActivity : AppCompatActivity() {

    private lateinit var  model: SharedViewModel

    private var backerList: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_trip)

        model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        loadTripDetails()
        backers.setOnClickListener { loadBackers() }
        addedBackersList.setOnClickListener { loadBackers() }
        closeDetailsTripButton.setOnClickListener { onBackPressed() }
    }


    private fun loadTripDetails() {
        val tripID = intent.getStringExtra("EVENT_ID")
        val token = TokenManager(this).deviceToken.toString()

        TripsService.tripById(tripID, token, {trip -> onSuccess(trip) }, { onError() })
    }

    private fun onSuccess(trip: Trip) = runOnUiThread{
        if (trip.id.isNotEmpty()) {
            backerList = trip.joinList
            setTexts(trip)
        }
    }

    private fun onError() = runOnUiThread {
        Log.d("yoy", "Something bad happened")
    }

    private fun loadAddedBackers(backerArray: ArrayList<User>) {
        addedBackersList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        addedBackersList.addItemDecoration(OverlapDecoration())
        addedBackersList.setHasFixedSize(true)
        addedBackersList.adapter = CardBackersAdapter(backerArray)
    }

    private fun setTexts(trip: Trip) = runOnUiThread {
        loadAddedBackers(trip.joinList)
        departureCity.text = trip.departureCity.capitalize()
        destinationCity.text = trip.destinationCity.capitalize()
        tripDescription.text = trip.description
        arrivalDate.text = trip.arrivalDate
    }


    private fun loadBackers() {
        val intent = Intent(this, UserTripBackersActivity::class.java)
        val jsonArray = Gson().toJson(backerList)
        intent.putExtra("JOIN_LIST", jsonArray)
        startActivity(intent)
    }
}

class OverlapDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) = outRect.set(0, 0, vertOverlap, 0)
    companion object {
        private const val vertOverlap = -40
    }
}
