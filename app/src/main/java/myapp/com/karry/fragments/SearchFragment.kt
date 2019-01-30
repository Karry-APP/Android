package myapp.com.karry.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import myapp.com.karry.entity.Trip

import myapp.com.karry.R
import myapp.com.karry.adapters.TripsAdapter
import myapp.com.karry.network.TripsService
import org.json.JSONArray

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_search, container, false)

        v.tripsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)

        v.searchDestinationCity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchFor(p0.toString())
                searchProgress.visibility = View.VISIBLE
                if(p0.isNullOrBlank()) {
                    v.searchBeginSearch.text = ""
                } else {
                    v.searchBeginSearch.text = ""
                }
            }
        })
        return v
    }

    private fun searchFor(destinationCity: String) {
        TripsService.searchByCity(destinationCity, { response ->

            activity?.runOnUiThread {
                val tripsArray = JSONArray(response.body()?.string())
                val results = arrayListOf<Trip>()

                for (i in 0 until tripsArray.length()) {
                    val tripObject = tripsArray.getJSONObject(i)
                    val tripDescription = tripObject.getString("description").toString()
                    val destinationCity = tripObject.getString("destinationCity").toString()
                    val trip = Trip(tripDescription, destinationCity)
                    results.add(trip)
                }

                searchProgress.visibility = View.INVISIBLE
                if(results.isNullOrEmpty()) {
                    searchBeginSearch.text = "Oups aucun résultat trouvé "
                } else {
                    tripsList.adapter = TripsAdapter(results)
                }
            }
        }, {
            activity?.runOnUiThread {
                Toast.makeText(this.context, "oupsi", Toast.LENGTH_LONG).show()
            }
        })
    }
}
