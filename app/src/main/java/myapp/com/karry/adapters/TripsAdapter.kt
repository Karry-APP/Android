package myapp.com.karry.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.trip_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.Trip

class CustomViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class TripsAdapter(private val tripList: List<Trip>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return tripList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.trip_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val trip = tripList[position]
        holder.view.tripDescription.text = trip.description
        holder.view.tripDestinationCity.text = trip.destinationCity
        holder.view.tripCard.setOnClickListener { v -> loadEvent(v.context, trip) }
    }

    fun loadEvent(c: Context, trip: Trip) {
        Toast.makeText(c, "Yeah" + trip.description.toString(), Toast.LENGTH_LONG).show()
        /*val intent = Intent(c, ::class.java)
        intent.putExtra("RESTAURANT_ID", restaurant.id)
        c.startActivity(intent)*/
    }

}