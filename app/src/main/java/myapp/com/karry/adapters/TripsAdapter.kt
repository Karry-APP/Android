package myapp.com.karry.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.trip_row.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.TripDetails
import myapp.com.karry.entity.Trip

class TripViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class TripsAdapter(private val tripList: List<Trip>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<TripViewHolder>() {

    override fun getItemCount(): Int {
        return tripList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.trip_row, parent, false)
        return TripViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {

        Log.d("yay", "yay")

        val trip = tripList[position]

        holder.view.tripDepartureCityDetails.text = trip.departureCity
        holder.view.tripDestinationCity.text = trip.destinationCity

        holder.view.linkTripDetails.setOnClickListener { v -> loadTrip(v.context, trip) }
    }

    private fun loadTrip(c: Context, trip: Trip) {
        val intent = Intent(c, TripDetails::class.java)
        intent.putExtra("EVENT_ID", trip.id)
        c.startActivity(intent)
    }
}