package myapp.com.karry.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.request_trips_row.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.TripDetails
import myapp.com.karry.activities.UserTripActivity
import myapp.com.karry.entity.Trip

class RequestedTripViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class RequestedTripsAdapter(private val tripList: List<Trip>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<RequestedTripViewHolder>() {

    override fun getItemCount(): Int {
        return tripList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestedTripViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.request_trips_row, parent, false)
        return RequestedTripViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: RequestedTripViewHolder, position: Int) {
        val trip = tripList[position]

        holder.view.requestTripCard.setOnClickListener { v -> loadTrip(v.context, trip) }
        holder.view.departureCity.text = trip.departureCity
        holder.view.arrivalCity.text = trip.destinationCity
    }

    private fun loadTrip(c: Context, trip: Trip) {
        val intent = Intent(c, UserTripActivity::class.java)
        intent.putExtra("EVENT_ID", trip.id)
        c.startActivity(intent)
    }
}