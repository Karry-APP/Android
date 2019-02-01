package myapp.com.karry.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
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

        v.tripsListView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)

        v.searchDestinationCity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkSearch()
            }
        })

        v.searchDepartureCity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkSearch()

            }
        })
        return v
    }

    fun checkSearch() {
        if(!searchDepartureCity.text.isNullOrBlank() && !searchDestinationCity.text.isNullOrBlank() ) {
            searchFor(searchDepartureCity.text.toString(), searchDestinationCity.text.toString())
            searchProgress.visibility = View.VISIBLE
            searchBeginSearch.text = ""
        } else {
            searchBeginSearch.text = ""
        }
    }

    private fun searchFor(departureCity: String, destinationCity: String) {
        TripsService.searchByCities(departureCity, destinationCity, { tripsList ->
            activity?.runOnUiThread {
                searchProgress.visibility = View.INVISIBLE
                if(tripsList.isNullOrEmpty()) {
                    searchBeginSearch.text = "Oups aucun résultat trouvé "
                } else {
                    tripsListView.adapter = TripsAdapter(tripsList)
                }
            }
        }, {
            activity?.runOnUiThread {
                Toast.makeText(this.context, "oupsi", Toast.LENGTH_LONG).show()
            }
        })
    }
}
