package myapp.com.karry.fragments.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_trips.*
import kotlinx.android.synthetic.main.fragment_trips.view.*
import myapp.com.karry.activities.CreateTripWrapperActivity
import myapp.com.karry.activities.TripDetails
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
        v.placeHolder.visibility = View.INVISIBLE
        v.placeHolder_link.visibility = View.INVISIBLE

        v.placeHolder_link.setOnClickListener{
            val intent = Intent(v.context, CreateTripWrapperActivity::class.java)
            startActivity(intent)
        }

        loadUserCreatedTrips(v)
        return v
    }

    private fun loadUserCreatedTrips(v: View) {
        val token: String = TokenManager(requireContext()).deviceToken ?: ""
        UsersService.getCreatedTrips(token,
            {
                tripsArray -> onSuccess(tripsArray, v)
            }, {
                onError(v)
            }
        )
    }

    private fun onSuccess(tripsArray: List<Trip>, v: View) = activity?.runOnUiThread {

        if(tripsArray.isEmpty()) {
            v.placeHolder.visibility = View.VISIBLE
            v.placeHolder_link.visibility = View.VISIBLE

        }

        v.progressBarTrip.visibility = View.INVISIBLE
        userTripsList.adapter = TripsAdapter(tripsArray) {
            val intent = Intent(this.context, TripDetails::class.java)
            intent.putExtra("TRIP", Gson().toJson(it))
            startActivity(intent)
        }
        swiperefresh.isRefreshing = false
    }

    private fun onError(v: View) = activity?.runOnUiThread {
        v.placeHolder.visibility = View.VISIBLE
        v.placeHolder_link.visibility = View.VISIBLE
    }
}
