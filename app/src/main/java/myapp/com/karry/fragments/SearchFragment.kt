package myapp.com.karry.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.view.*
import myapp.com.karry.R


class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_search, container, false)

        v.destinationCityLabel.setOnClickListener { openCitySearch("destination") }
        v.destinationCityInput.setOnClickListener { openCitySearch("destination") }
        v.arrivalCityLabel.setOnClickListener { openCitySearch("arrival") }
        v.arrivalCityInput.setOnClickListener { openCitySearch("arrival") }

        displayReceivedData(v)

        return v
    }

    private fun openCitySearch(currentSearch:String) {
        val bundle = Bundle()
        val cityPickerFragment = CityPickerFragment()

        if (currentSearch === "arrival") {
            bundle.putString("currentDirection", "arrival")
        } else if (currentSearch === "destination") {
            bundle.putString("currentDirection", "destination")
        }

        cityPickerFragment.arguments = bundle

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, cityPickerFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun displayReceivedData(v: View) {
        val bundleArgs = arguments

        val cityNameSelected = bundleArgs?.getString("cityName")
        val currentDirectionSelected = bundleArgs?.getString("currentDirection").toString()

        if (currentDirectionSelected === "arrival") {
            v.arrivalCityInput.text = cityNameSelected.toString()
        } else if (currentDirectionSelected === "destination") {
            v.destinationCityInput.text = cityNameSelected.toString()
        }
    }
}
