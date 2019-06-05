package myapp.com.karry.fragments.main


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_search.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.CreateTripWrapperActivity
import myapp.com.karry.model.SharedViewModel

class SearchFragment : Fragment() {

    private var departureValue: String = " "
    private var destinationValue: String = " "

    private lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_search, container, false)
        v.departureCityLabel.setOnClickListener { openCitySearch("departure", "search") }
        v.departureCityInput.setOnClickListener { openCitySearch("departure", "search") }
        v.destinationCityLabel.setOnClickListener { openCitySearch("destination", "search") }
        v.destinationCityInput.setOnClickListener { openCitySearch("destination", "search") }
        v.searchButton.setOnClickListener { redirectToSearchResults() }

        v.postSearch.setOnClickListener {
            val intent = Intent(v.context, CreateTripWrapperActivity::class.java)
            startActivity(intent)
        }
        displayReceivedData(v)
        return v
    }

    private fun redirectToSearchResults() {
        model.cleanTripsList()
        launchFragment(SearchResultsFragment())
    }


    private fun openCitySearch(currentSearch: String, currentFragment: String) {
        val bundle = Bundle()
        val cityPickerFragment = CityPickerFragment()
        bundle.putString("currentDirection", currentSearch)
        bundle.putString("currentFragment", currentFragment)
        cityPickerFragment.arguments = bundle

        launchFragment(cityPickerFragment)
    }

    private fun displayReceivedData(v: View) {
        if (model.departureValue.value !== null) {
            departureValue = model.departureValue.value.toString()
        }

        if (model.destinationValue.value !== null) {
            destinationValue = model.destinationValue.value.toString()
        }

        v.departureCityInput.text = departureValue
        v.destinationCityInput.text = destinationValue
    }

    private fun launchFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
