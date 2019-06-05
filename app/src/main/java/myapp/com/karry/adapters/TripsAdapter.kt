package myapp.com.karry.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.trip_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.Trip

class TripViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class TripsAdapter(private val tripList: List<Trip>, val click: (trip: Trip) -> Unit) :
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
        val trip = tripList[position]
        val fullname = "${trip.owner.firstname} ${trip.owner.lastname}"

        Glide
            .with(holder.view)
            .load("https://" + trip.owner.profilePicture)
            .circleCrop()
            .into(holder.view.userAvatar)

        holder.view.tripDepartureCityDetails.text = trip.departureCity.capitalize()
        holder.view.tripDestinationCity.text = trip.destinationCity.capitalize()

        holder.view.userName.text = fullname
        holder.view.userRate.text = if (trip.owner.ratings !== "0") trip.owner.ratings else "N/A"
        holder.view.userRate.text = trip.owner.ratings
        holder.view.searchEndDate.text = trip.arrivalDate
        holder.view.tripCard.setOnClickListener { click(trip) }
    }
}