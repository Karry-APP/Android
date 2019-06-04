package myapp.com.karry.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_trips.*
import kotlinx.android.synthetic.main.fragment_trips.view.*
import myapp.com.karry.adapters.TripsAdapter
import myapp.com.karry.entity.Trip
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.UsersService

class TripsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_trips, container, false)
        v.userTripsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
        v.swiperefresh.setOnRefreshListener { loadUserCreatedTrips(v) }
        v.progressBarTrip.visibility = View.VISIBLE
        loadUserCreatedTrips(v)
        return v
    }

    private fun loadUserCreatedTrips(v: View) {
        val token: String = TokenManager(requireContext()).deviceToken ?: ""
        UsersService.getCreatedTrips(token,
            {
                tripsArray -> onSuccess(tripsArray, v)
            }, {
                onError()
            }
        )
    }

    private fun onSuccess(tripsArray: List<Trip>, v: View) = activity?.runOnUiThread {
        v.progressBarTrip.visibility = View.INVISIBLE
        userTripsList.adapter = TripsAdapter(tripsArray) {
            // activity?.intent!!.putExtra("trip", trip.id)
        }
        swiperefresh.isRefreshing = false
    }

    private fun onError() = activity?.runOnUiThread {

    }
}
