package myapp.com.karry.network

import com.google.gson.Gson
import myapp.com.karry.entity.Trip
import myapp.com.karry.modules.ApiManager
import okhttp3.*
import java.io.IOException

class TripsService {

    companion object {
        fun create(tripJson: String, success: (response: Response) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), tripJson)
            val request = okhttp3.Request.Builder().url(ApiManager.URL.TRIP_CREATE).post(body).build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 201) {
                        success(response)
                    } else {
                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })
        }

        fun searchByCities(departureCity: String, destinationCity: String, success: (tripsList: List<Trip>) -> Unit, failure: () -> Unit) {
            val request = okhttp3.Request.Builder().url(ApiManager.URL.TRIP_SEARCH + "?departureCity=$departureCity&destinationCity=$destinationCity").build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        val tripsArray = Gson().fromJson(response.body()?.string(), Array<Trip>::class.java).toList()
                        success(tripsArray)
                    } else {
                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })

        }
    }


}