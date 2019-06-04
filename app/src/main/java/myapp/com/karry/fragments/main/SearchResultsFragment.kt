package myapp.com.karry.fragments.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search_results.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.TripDetails
import myapp.com.karry.adapters.TripsAdapter
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

        v.departureValue.text = model.departureValue.value?.toUpperCase()
        v.destinationValue.text = model.destinationValue.value?.toUpperCase()
        v.closeSearchResult.setOnClickListener { launchFragment(SearchFragment()) }

        v.errorSearchResult.visibility = View.INVISIBLE
        v.tripListProgress.visibility = View.INVISIBLE

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
        val departure = model.departureValue.value.toString()
        val destination = model.destinationValue.value.toString()
        v.tripListProgress.visibility = View.VISIBLE
        v.tripsList.visibility = View.INVISIBLE

            TripsService.searchByCities(departure, destination, token, {
                if (it.isNullOrEmpty()) {
                    activity?.runOnUiThread {
                        v.errorSearchResult.visibility = View.VISIBLE
                        v.tripListProgress.visibility = View.INVISIBLE
                        v.tripsList.visibility = View.INVISIBLE
                    }

                } else {
                    activity?.runOnUiThread {
                        v.errorSearchResult.visibility = View.INVISIBLE
                        v.tripListProgress.visibility = View.INVISIBLE
                        v.tripsList.visibility = View.VISIBLE

                        model.storeTrips(it)
                        bindView(v)
                    }
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
