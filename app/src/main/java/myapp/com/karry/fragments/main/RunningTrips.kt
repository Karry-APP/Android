package myapp.com.karry.fragments.main


import android.os.Bundle
import android.provider.Settings.System.DATE_FORMAT
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_running_trips.view.*
import myapp.com.karry.R
import myapp.com.karry.adapters.RequestedTripsAdapter
import myapp.com.karry.model.SharedViewModel
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.UsersService
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*
import java.util.concurrent.TimeUnit

class RunningTrips : Fragment() {

    private lateinit var  model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_running_trips, container, false)
        loadUserTrips(v)
        bindView(v)

        return v
    }

    private fun loadUserTrips(v: View) {
        val token = TokenManager(context!!).deviceToken.toString()

        UsersService.getCreatedTrips(token,  {
            if (it.isNullOrEmpty()) {
            } else {
                val cleanTrip = it.filter {currentTrip ->
                    Log.d("yay", getDaysBetweenDates(currentTrip.arrivalDate).toString())
                    getDaysBetweenDates(currentTrip.arrivalDate) > 0
                }
                model.storeTrips(cleanTrip)
                bindView(v)
            }
        }, {
        })
    }



    private fun bindView(v: View) {
        val tripListArray = model.tripListArray

        activity?.runOnUiThread {
            v.runningTripListRecylcerView.layoutManager = LinearLayoutManager(this.context)
            v.runningTripListRecylcerView.adapter = RequestedTripsAdapter(tripListArray)
        }
    }

    fun getDaysBetweenDates(end: String): Long {
        val calendar = Calendar.getInstance()
        val DATE_FORMAT = "d/M/yyyy"
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val start = "$day/$month/$year"
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.FRENCH)

        val startDate: Date
        val endDate: Date
        var numberOfDays: Long = 0
        try {
            startDate = dateFormat.parse(start)
            endDate = dateFormat.parse(end)
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return numberOfDays
    }

    private fun getUnitBetweenDates(startDate: Date, endDate: Date, unit: TimeUnit): Long {
        val timeDiff = endDate.time - startDate.time
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS)
    }


}
