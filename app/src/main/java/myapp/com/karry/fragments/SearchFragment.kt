package myapp.com.karry.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.view.*
import myapp.com.karry.R


class SearchFragment : Fragment() {

    private var destinationValue: String? = ""
    private var arrivalValue: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_search, container, false)
        v.destinationCityLabel.setOnClickListener { openCitySearch("destination") }
        v.destinationCityInput.setOnClickListener { openCitySearch("destination") }
        v.arrivalCityLabel.setOnClickListener { openCitySearch("arrival") }
        v.arrivalCityInput.setOnClickListener { openCitySearch("arrival") }
        displayReceivedData(v)
        return v
    }

    private fun openCitySearch(currentSearch: String) {
        val bundle = Bundle()
        val cityPickerFragment = CityPickerFragment()
        bundle.putString("currentDirection", currentSearch)
        bundle.putString("destination", destinationValue)
        bundle.putString("arrival", arrivalValue)
        cityPickerFragment.arguments = bundle
        launchFragment(cityPickerFragment)
    }

    private fun displayReceivedData(v: View) {
        val bundleArgs = arguments
        destinationValue = bundleArgs?.getString("destination")
        arrivalValue = bundleArgs?.getString("arrival")
        v.destinationCityInput.text = destinationValue
        v.arrivalCityInput.text = arrivalValue
    }

    private fun launchFragment(fragment: Fragment) {

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
}
