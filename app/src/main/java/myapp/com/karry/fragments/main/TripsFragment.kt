package myapp.com.karry.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_trips.*
import kotlinx.android.synthetic.main.fragment_trips.view.*
import myapp.com.karry.activities.TripDetails
import myapp.com.karry.activities.TripFormActivity
import myapp.com.karry.adapters.TripsAdapter
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.UsersService

class TripsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_trips, container, false)
        v.userTripsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
        v.openTripForm.setOnClickListener { startTripFormActivity() }
        v.swiperefresh.setOnRefreshListener { loadUserCreatedTrips() }
        loadUserCreatedTrips()
        return v
    }

    private fun startTripFormActivity() {
        startActivity(Intent(this.context, TripFormActivity::class.java))
    }

    private fun loadUserCreatedTrips() {
        val token: String = TokenManager(requireContext()).deviceToken ?: ""

        UsersService.getCreatedTrips(token, { tripsArray ->
            activity?.runOnUiThread {
                userTripsList.adapter = TripsAdapter(tripsArray) {
                   // activity?.intent!!.putExtra("trip", trip.id)
                }
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
