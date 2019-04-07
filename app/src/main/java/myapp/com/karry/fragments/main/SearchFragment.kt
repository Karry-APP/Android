package myapp.com.karry.fragments.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.view.*
import myapp.com.karry.R


class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_search, container, false)
        v.destinationCityLabel.setOnClickListener { openDestinationCitySearch() }
        v.destinationCityInput.setOnClickListener { openDestinationCitySearch() }
        v.arrivalCityLabel.setOnClickListener { openArrivalCitySearch() }
        v.arrivalCityInput.setOnClickListener { openArrivalCitySearch() }
        return v
    }

    private fun openArrivalCitySearch() {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, CityPickerFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun openDestinationCitySearch() {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, SearchFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
