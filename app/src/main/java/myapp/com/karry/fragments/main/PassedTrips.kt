package myapp.com.karry.fragments.main


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_running_trips.view.*
import myapp.com.karry.R
import myapp.com.karry.adapters.RequestedTripsAdapter
import myapp.com.karry.model.SharedViewModel
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.UsersService
import java.lang.Exception
import java.time.OffsetDateTime

class PassedTrips: Fragment() {

    private lateinit var  model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_running_trips, container, false)

        loadUserTrips(v)
        return v
    }

    private fun loadUserTrips(v: View) {
        val token = TokenManager(context!!).deviceToken.toString()

        UsersService.getCreatedTrips(token,  {
            if (it.isNullOrEmpty()) {
            } else {
                val cleanTrip = it.filter {currentTrip ->
                    val date = OffsetDateTime.parse(currentTrip.arrivalDate)
                    date.isAfter(OffsetDateTime.now())
                }
                model.storeTrips(cleanTrip)
                bindView(v)
            }
        }, {
        })
    }

    private fun bindView(v: View) {
        val tripListArray = model.tripListArray

        activity?.runOnUiThread {
            v.runningTripListRecylcerView.layoutManager = LinearLayoutManager(this.context)
            v.runningTripListRecylcerView.adapter = RequestedTripsAdapter(tripListArray)
        }
    }


}
