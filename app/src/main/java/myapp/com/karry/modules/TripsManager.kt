package myapp.com.karry.modules

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import com.google.gson.Gson
import myapp.com.karry.activities.TripDetails
import myapp.com.karry.entity.Trip
import okhttp3.*
import java.io.IOException

class TripsManager(context: Context) {

    companion object {

        fun loadDetails(tripId: String?, success: (tripDetails: Trip) -> Unit, failure: () -> Unit) {
            val request = okhttp3.Request.Builder().url(ApiManager.URL.TRIP_DETAIL(tripId)).get().build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        val tripDetails = Gson().fromJson(response.body()!!.string(), Trip::class.java)
                        success(tripDetails)
                    } else {
                        Log.d("fail", response.body()?.string())
                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("failure", e.toString())
                    failure()
                }
            })
        }
    }

}