package myapp.com.karry.network

import android.util.Log
import com.google.gson.Gson
import myapp.com.karry.entity.Trip
import myapp.com.karry.entity.User
import myapp.com.karry.modules.ApiManager
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class UsersService {
    companion object {

        fun me(token: String, userId: String?, success: (user: User) -> Unit, failure: () -> Unit) {
            val request = Request.Builder().url(ApiManager.URL.USER_PATCH + userId).header("x-auth", token).build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                        val user = Gson().fromJson(response.body()?.string(), User::class.java)
                    if (response.code() == 200) {
                        success(user)
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

        fun patchProfile(payload: JSONObject, token: String, success: (updatedUser: User) -> Unit, failure: () -> Unit) {
            val userID = payload.getString("userID")
            payload.remove("userID")
            val body = RequestBody
                .create(MediaType.parse("application/json; charset=utf-8"), payload.toString())
            val request = Request.Builder()
                .url(ApiManager.URL.USER_PATCH + userID)
                .header("X-Auth", token)
                .patch(body)
                .build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        val updatedUser = Gson().fromJson(response.body()?.string(), User::class.java)
                        success(updatedUser)
                    } else {
                        failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })
        }

        fun getCreatedTrips(token: String, success: (tripsList: List<Trip>) -> Unit, failure: () -> Unit) {
            val request = Request.Builder().url(ApiManager.URL.USER_TRIPS).header("x-auth", token).build()

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
                    Log.d("yay", e.toString())

                    failure()
                }
            })
        }

        fun removeCreatedTrips(token: String, success: (tripsList: List<Trip>) -> Unit, failure: () -> Unit) {
            val request = Request.Builder().url(ApiManager.URL.USER_TRIPS).header("x-auth", token).build()

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