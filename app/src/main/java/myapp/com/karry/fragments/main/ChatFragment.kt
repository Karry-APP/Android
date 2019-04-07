package myapp.com.karry.fragments.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.socket.client.IO
import io.socket.client.Socket

import myapp.com.karry.R
import myapp.com.karry.modules.ApiManager

class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_chat, container, false)
        return v
    }

    fun setupConnection() {
        val socket: Socket = IO.socket(ApiManager.URL.BASE)
        socket.connect()
    }
}
