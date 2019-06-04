package myapp.com.karry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.city_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.City

class CityViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class CitiesAdapter(private var cityList: List<City>, val click: (cityName: String) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<CityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.city_row, parent, false)
        return CityViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return cityList.count()
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cityList[position]
        holder.view.cityName.text = city.name
        holder.view.cityRow.setOnClickListener { selectCity(city) }
    }

    private fun selectCity(city: City) {
        click(city.name)
    }
}