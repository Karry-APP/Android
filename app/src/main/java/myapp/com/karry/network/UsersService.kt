package myapp.com.karry.network

import android.util.Log
import com.google.gson.Gson
import myapp.com.karry.entity.Trip
import myapp.com.karry.entity.User
import myapp.com.karry.modules.ApiManager
import okhttp3.*
import java.io.IOException

class UsersService {
    companion object {
        fun login(userJson: String, success: (response: Response) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson)
            val request = Request.Builder().url(ApiManager.URL.USER_LOGIN).post(body).build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
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

        fun register(userJson: String, success: (user: User, header: String?) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson)
            val request = Request.Builder().url(ApiManager.URL.USER_REGISTER).post(body).build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 201) {
                        val header = response.header("x-auth")
                        val user = Gson().fromJson(response.body()?.string(), User::class.java)
                        success(user, header)
                    } else {
                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })
        }

        fun logout(token: String, success: (response: Response) -> Unit, failure: () -> Unit) {
            val request = Request.Builder().delete().url(ApiManager.URL.USER_LOGOUT).header("x-auth", token).build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        success(response)
                    } else {
                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("yiy", e.toString())
                    failure()
                }
            })
        }

        fun getCreatedTrips(token: String, userId: String?, success: (tripsList: List<Trip>) -> Unit, failure: () -> Unit) {
            val request = Request.Builder().url(ApiManager.URL.USER_TRIPS(userId)).header("x-auth", token).build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        val tripsArray = Gson().fromJson(response.body()!!.string(), Array<Trip>::class.java).toList()
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