package myapp.com.karry.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.trip_row.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.TripDetails
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
        holder.view.tripDepartureCity.text = trip.departureCity
        holder.view.tripDestinationCity.text = trip.destinationCity
        holder.view.tripCard.setOnClickListener { v -> loadTrip(v.context, trip) }
    }

    fun loadTrip(c: Context, trip: Trip) {
        val intent = Intent(c, TripDetails::class.java)
        intent.putExtra("EVENT_ID", trip.id)
        c.startActivity(intent)
    }

}