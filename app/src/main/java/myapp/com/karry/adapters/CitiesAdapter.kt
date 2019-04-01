package myapp.com.karry.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.city_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.City

class CityViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class CitiesAdapter(private var cityList: List<City>) : androidx.recyclerview.widget.RecyclerView.Adapter<CityViewHolder>() {
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
        holder.view.cityRow.setOnClickListener { v -> selectCity(v.context, city) }
    }

    private fun selectCity(c: Context, city: City) {
        Toast.makeText(c, "Selected city is "+city.name, Toast.LENGTH_LONG).show()
    }

}