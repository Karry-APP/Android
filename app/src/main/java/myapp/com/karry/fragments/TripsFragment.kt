package myapp.com.karry.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_trips.*
import kotlinx.android.synthetic.main.fragment_trips.view.*

import myapp.com.karry.R
import myapp.com.karry.activities.TripFormActivity
import myapp.com.karry.adapters.TripsAdapter
import myapp.com.karry.entity.Trip
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService
import org.json.JSONArray

class TripsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_trips, container, false)
        v.userEventsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
        v.openTripForm.setOnClickListener { startTripFormActivity() }
        v.swiperefresh.setOnRefreshListener { loadUserCreatedTrips() }
        loadUserCreatedTrips()
        return v
    }

    private fun startTripFormActivity() {
        startActivity(Intent(this.context, TripFormActivity::class.java))
    }

    private fun loadUserCreatedTrips() {
        val userId = UserInfoManager(this.requireContext()).id
        val token = TokenManager(this.requireContext()).deviceToken

        UsersService.getCreatedTrips(token, userId, {response ->
            val tripsArray = JSONArray(response.body()!!.string())

            val results = arrayListOf<Trip>()
            for (i in 0 until tripsArray.length()) {

                val jObject = tripsArray.getJSONObject(i)
                val id = jObject.getString("_id").toString()
                val description = jObject.getString("description").toString()
                val departureCity = jObject.getString("departureCity").toString()
                val destinationCity = jObject.getString("destinationCity").toString()

                val event = Trip(id, description, departureCity, destinationCity)
                results.add(event)
            }

            activity?.runOnUiThread {
                userEventsList.adapter = TripsAdapter(results)
                Toast.makeText(context, "Succeed", Toast.LENGTH_LONG).show()
                swiperefresh.isRefreshing = false
            }
        }, {
            activity?.runOnUiThread {
                Toast.makeText(this.context, "Aie", Toast.LENGTH_LONG).show()
            }
        })
    }
}
