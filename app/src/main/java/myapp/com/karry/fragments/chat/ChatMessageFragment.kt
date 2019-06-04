package myapp.com.karry.fragments.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_chat_message.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.AskingContactActivity
import myapp.com.karry.activities.ChatActivity
import myapp.com.karry.adapters.RoomAdapter
import myapp.com.karry.entity.Room
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.UsersService

class ChatMessageFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_chat_message, container, false)
        v.chatRoomsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)

        val token: String? = TokenManager(this.context!!).deviceToken ?: ""
        UsersService.getRooms(token!!, { roomsArray -> onSuccess(v, roomsArray) }, { onError() })
        return v
    }


    private fun onSuccess(v: View, tripsArray: List<Room>) = activity?.runOnUiThread {
        v.chatRoomsList.adapter = RoomAdapter(tripsArray) { roomId ->
            val intent = Intent(this.context, ChatActivity::class.java)
            intent.putExtra("ROOM_ID", roomId)
            startActivity(intent)
        }
    }

    private fun onError() = activity?.runOnUiThread {
        //Toast.makeText(this.context, "Oups something went wrong", Toast.LENGTH_LONG).show()
    }
}
