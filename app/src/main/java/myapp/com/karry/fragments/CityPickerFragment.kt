package myapp.com.karry.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_city_picker.*
import kotlinx.android.synthetic.main.fragment_city_picker.view.*
import myapp.com.karry.entity.City
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import myapp.com.karry.R
import myapp.com.karry.adapters.CitiesAdapter

class CityPickerFragment : Fragment() {
    private val cityLisArray: ArrayList<City> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_city_picker, container, false)
        createCityList()
        v.arrivalCitiesList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
        v.arrivalCitiesList.adapter = CitiesAdapter(cityLisArray) { cityName ->
            fillSearchBar(cityName)
        }

        v.searchbarCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })

        v.closeCityPicker.setOnClickListener {
            closeCityPicker(SearchFragment())
        }

        return v
    }

    private fun  fillSearchBar(cityName: String) {
        val searchFragment = SearchFragment()
        val bundle = Bundle()
        val bundleArgs = arguments

        val direction = bundleArgs?.getString("currentDirection").toString()
        val destination = bundleArgs?.getString("destination").toString()
        val arrival = bundleArgs?.getString("arrival").toString()

        if(direction === "destination") {
            bundle.putString("destination", cityName)
            bundle.putString("arrival", arrival)
        } else if(direction === "arrival") {
            bundle.putString("destination", destination)
            bundle.putString("arrival", cityName)
        }
        searchFragment.arguments = bundle
        closeCityPicker(searchFragment)
    }

    private fun closeCityPicker(fragment: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun filter(text: String) {
        if (text.isBlank()) {
            arrivalCitiesList.adapter = CitiesAdapter(cityLisArray) { cityName ->
                fillSearchBar(cityName)
            }
        } else {
            val filteredList = arrayListOf<City>()
            for (city in cityLisArray) {
                if (city.name.toLowerCase().contains(text.toLowerCase())) {
                    Log.d("City filtered", city.name)
                    filteredList.add(city)
                }
            }
            arrivalCitiesList.adapter = CitiesAdapter(filteredList) { cityName ->
                fillSearchBar(cityName)
            }
        }
    }

    private fun createCityList() {
        cityLisArray.add(City("Paris"))
        cityLisArray.add(City("Londres"))
        cityLisArray.add(City("Tokyo"))
        cityLisArray.add(City("Bangkok"))
        cityLisArray.add(City("New York"))
        cityLisArray.add(City("Dublin"))
        cityLisArray.add(City("Sydney"))
        cityLisArray.add(City("Prague"))
        cityLisArray.add(City("Stockholm"))
        cityLisArray.add(City("Rio de Janeiro"))
        cityLisArray.add(City("Brasilia"))
        cityLisArray.add(City("Mexico"))
        cityLisArray.add(City("Los Angeles"))
        cityLisArray.add(City("Montreal"))
        cityLisArray.add(City("Chicoutimi"))
        cityLisArray.add(City("Madrid"))
        cityLisArray.add(City("Rome"))
        cityLisArray.add(City("La Valette"))
        cityLisArray.add(City("Marseille"))
        cityLisArray.add(City("Lille"))
        cityLisArray.add(City("Nantes"))
        cityLisArray.add(City("Rouen"))
        cityLisArray.add(City("Tour"))
        cityLisArray.add(City("Amsterdam"))
        cityLisArray.add(City("Berlin"))
        cityLisArray.add(City("Bobigny"))
        cityLisArray.add(City("Montreuil"))
        cityLisArray.add(City("Lima"))
        cityLisArray.add(City("Franckfort"))
        cityLisArray.add(City("Barcelone"))
        cityLisArray.add(City("Rabat"))
        cityLisArray.add(City("Casablanca"))
        cityLisArray.add(City("La Paz"))
        cityLisArray.add(City("Marracech"))
        cityLisArray.add(City("Chicago"))
        cityLisArray.add(City("Torronto"))
        cityLisArray.add(City("Sophia"))
        cityLisArray.add(City("Lisbonne"))
        cityLisArray.add(City("Tanger"))
        cityLisArray.add(City("Cardif"))
        cityLisArray.add(City("Séoul"))
        cityLisArray.add(City("Lagos"))
        cityLisArray.add(City("Niameu"))
        cityLisArray.add(City("Bamako"))
        cityLisArray.add(City("Johanesbourd"))
        cityLisArray.add(City("Monovia"))
        cityLisArray.add(City("Moscou"))
        cityLisArray.add(City("Libreville"))
        cityLisArray.add(City("Nerobi"))
        cityLisArray.add(City("Tunis"))
        cityLisArray.add(City("Alger"))
        cityLisArray.add(City("Le Caire"))
        cityLisArray.add(City("Beyrouth"))
        cityLisArray.add(City("Istanbul"))
        cityLisArray.add(City("Ankarra"))
        cityLisArray.add(City("Athenes"))
        cityLisArray.add(City("Bukarest"))
        cityLisArray.add(City("Zabreg"))
        cityLisArray.add(City("Vienne"))
        cityLisArray.add(City("Kiev"))
        cityLisArray.add(City("New Delhi"))
        cityLisArray.add(City("Katmandou"))
        cityLisArray.add(City("Hanoï"))
        cityLisArray.add(City("Pékin"))
        cityLisArray.add(City("Quimper"))
        cityLisArray.add(City("Shangaï"))
        cityLisArray.add(City("Luxembourg"))
        cityLisArray.add(City("Jakarta"))
        cityLisArray.add(City("Dubaï"))
    }
}
