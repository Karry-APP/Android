package myapp.com.karry.network

import com.google.gson.Gson
import myapp.com.karry.entity.Request
import myapp.com.karry.entity.Transaction
import myapp.com.karry.entity.UserRequest
import myapp.com.karry.modules.ApiManager
import okhttp3.*
import java.io.IOException

class RequestsService {
    companion object {
        fun create(orderJson: String, token: String, success: (request: Request) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), orderJson)
            val request = okhttp3.Request.Builder().header("x-auth", token).url(ApiManager.URL.REQUESTS_CREATE).post(body).build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    when (response.code()) {
                        201 -> success(Gson().fromJson(response.body()?.string(), Request::class.java) )
                        else -> failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    failure()
                }
            })
        }

        fun getRequests(token: String, success: (request: Array<UserRequest>) -> Unit, failure: () -> Unit) {

            val request = okhttp3.Request.Builder().header("X-Auth", token).url(ApiManager.URL.USER_REQUESTS).build()
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    when (response.code()) {
                        200 -> success(Gson().fromJson(response.body()?.string(), Array<UserRequest>::class.java) )
                        else -> failure()
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("yay", e.toString())

                    failure()
                }
            })
        }


    }
}