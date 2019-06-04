package myapp.com.karry.network

import com.google.gson.Gson
import myapp.com.karry.entity.Request
import myapp.com.karry.modules.ApiManager
import okhttp3.*
import java.io.IOException

class RequestsService {
    companion object {
        fun create(orderJson: String, token: String, success: (request: Request) -> Unit, failure: () -> Unit) {
            val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), orderJson)
            val request = okhttp3.Request.Builder().header("X-Auth", token).url(ApiManager.URL.REQUESTS_CREATE).post(body).build()
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
    }
}