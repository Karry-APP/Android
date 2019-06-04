package myapp.com.karry.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.user_trips_row.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.OverlapDecoration
import myapp.com.karry.activities.UserTripActivity
import myapp.com.karry.entity.Trip
import java.lang.Exception
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class RequestedTripViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class RequestedTripsAdapter(private val tripList: List<Trip>) : androidx.recyclerview.widget.RecyclerView.Adapter<RequestedTripViewHolder>() {

    override fun getItemCount(): Int {
        return tripList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestedTripViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.user_trips_row, parent, false)
        return RequestedTripViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: RequestedTripViewHolder, position: Int) {
        val trip = tripList[position]
        holder.view.requestTripCard.setOnClickListener { v -> loadTrip(v.context, trip) }
        holder.view.departureCity.text = trip.departureCity.capitalize()
        holder.view.arrivalCity.text = trip.destinationCity.capitalize()
        holder.view.addedBackersList.layoutManager = LinearLayoutManager(holder.view.context, LinearLayoutManager.HORIZONTAL, false)
        holder.view.addedBackersList.addItemDecoration(OverlapDecoration())
        holder.view.addedBackersList.setHasFixedSize(true)
        holder.view.addedBackersList.adapter = CardBackersAdapter(trip.joinList)
        holder.view.tripCreatedDate.text = getFormattedDateSimple(trip.createdAt)
        holder.view.arrivalDateValue.text = getFormattedDateSimple(trip.arrivalDate)
    }

    private fun getFormattedDateSimple(dateInString: String): String {
        try {
            if (dateInString !== null) {
                val date = OffsetDateTime.parse(dateInString)
                val f = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    .withLocale(Locale.FRANCE)  // Or Locale.CANADA_FRENCH and such.

                return date.format(f)
            } else {
                return dateInString
            }
        } catch (e: Exception) {
            return dateInString
        }
    }

    private fun loadTrip(c: Context, trip: Trip) {
        val intent = Intent(c, UserTripActivity::class.java)
        intent.putExtra("EVENT_ID", trip.id)
        c.startActivity(intent)
    }
}