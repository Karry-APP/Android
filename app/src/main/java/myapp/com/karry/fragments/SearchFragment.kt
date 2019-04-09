package myapp.com.karry.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_search.view.*
import myapp.com.karry.R
import myapp.com.karry.model.SharedViewModel


class SearchFragment : Fragment() {

    private var destinationValue: String = " "
    private var arrivalValue: String = " "

    private lateinit var model: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

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
        cityPickerFragment.arguments = bundle

        launchFragment(cityPickerFragment)
    }

    private fun displayReceivedData(v: View) {
        if (model.destinationValue.value !== null) {
            destinationValue = model.destinationValue.value.toString()
        }

        if (model.arrivalValue.value !== null) {
            arrivalValue = model.arrivalValue.value.toString()
        }

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
