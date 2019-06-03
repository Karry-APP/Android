package myapp.com.karry.modules

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import myapp.com.karry.entity.Trip
import okhttp3.*
import java.io.IOException

class TripsManager(context: Context) {
    companion object {
        fun loadDetails(tripId: String?, token: String, success: (tripDetails: Trip) -> Unit, failure: () -> Unit) {
            val request = okhttp3.Request.Builder().url(ApiManager.URL.TRIP_DETAIL(tripId)).header("X-Auth", token).get().build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        val tripDetails = Gson().fromJson(response.body()!!.string(), Trip::class.java)
                        success(tripDetails)
                    } else {
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