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
        fun login(userJson: String, success: (response: Response) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson)
            val request = Request.Builder().url(ApiManager.URL.USER_LOGIN).post(body).build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    when(response.code()) {
                        200 ->success(response)
                        else ->failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })
        }


        fun loginGoogle(tokenJson: String, success: (user: User, token: String?) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), tokenJson)
            val request = Request.Builder().url(ApiManager.URL.USER_LOGIN_GOOGLE).post(body).build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    when (response.code()) {
                        200 -> success(Gson().fromJson(response.body()?.string(), User::class.java), response.header("x-auth"))
                        201 -> success(Gson().fromJson(response.body()?.string(), User::class.java), response.header("x-auth"))
                        else -> failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })
        }

        fun loginFacebook(userJson: String, success: (user: User, token: String?) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson)
            val request = Request.Builder().url(ApiManager.URL.USER_LOGIN_FACEBOOK).post(body).build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    when (response.code()) {
                        200 -> success(Gson().fromJson(response.body()?.string(), User::class.java), response.header("x-auth"))
                        201 -> success(Gson().fromJson(response.body()?.string(), User::class.java), response.header("x-auth"))
                        else -> failure()
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
                    when (response.code()) {
                        200 -> success(response)
                        else -> failure()
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


        fun updateUser(token: String, userId: String?, userJson: String, success: (user: User) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson)
            val request = Request.Builder().header("X-Auth", token).url(ApiManager.URL.USER_UPDATE(userId)).patch(body).build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.code() == 200) {
                        Log.d("cool", "user updated")
                        val user = Gson().fromJson(response.body()?.string(), User::class.java)
                        success(user)
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