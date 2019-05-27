package myapp.com.karry.fragments.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search_results.view.*
import kotlinx.android.synthetic.main.trip_row.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.TripDetails
import myapp.com.karry.adapters.TripsAdapter
import myapp.com.karry.entity.Trip
import myapp.com.karry.model.SharedViewModel
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.TripsService
import java.lang.Exception

class SearchResultsFragment : Fragment() {
    private lateinit var  model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View= inflater.inflate(R.layout.fragment_search_results, container, false)

        v.destinationValue.text = model.destinationValue.value?.toUpperCase()
        v.arrivalValue.text = model.arrivalValue.value?.toUpperCase()
        v.closeSearchResult.setOnClickListener { launchFragment(SearchFragment()) }

        loadTripsQuery(v)

        return v
    }

    private fun bindView(v: View) {

        val tripListArray = model.tripListArray

        activity?.runOnUiThread {
            v.tripsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
            v.tripsList.adapter = TripsAdapter(tripListArray) { trip ->
                val intent = Intent(this.context, TripDetails::class.java)
                intent.putExtra("TRIP", Gson().toJson(trip))
                startActivity(intent)
            }
        }
    }

    private fun loadTripsQuery(v: View) {
        val token = TokenManager(context!!).deviceToken.toString()
        val destination = model.destinationValue.value.toString()
        val arrival = model.arrivalValue.value.toString()

        TripsService.searchByCities(destination, arrival,token, {
            if (it.isNullOrEmpty()) {

            } else {
                model.storeTrips(it)
                bindView(v)
            }
        }, {
            Log.d("yay", "Something bad happened")
        })
    }

    private fun launchFragment(fragment: Fragment) {
        model.cleanTripsList()
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
