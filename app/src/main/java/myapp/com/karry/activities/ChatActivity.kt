package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_chat.*
import myapp.com.karry.R
import myapp.com.karry.adapters.ChatConversationAdapter
import myapp.com.karry.entity.Message
import myapp.com.karry.modules.ApiManager
import myapp.com.karry.modules.UserInfoManager
import org.json.JSONObject
import java.net.URISyntaxException

class ChatActivity : AppCompatActivity() {

    private val socket: Socket = IO.socket(ApiManager.URL.BASE)
    private var messageArray: ArrayList<Message> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val roomId = intent.getStringExtra("ROOM_ID")
        val userName = UserInfoManager(this).firstname + " " + UserInfoManager(this).lastname
        messageList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        Toast.makeText(this, roomId, Toast.LENGTH_LONG).show()

        try {
            socket.connect()
            val data = JSONObject()
            data.put("username", userName)
            data.put("roomId", roomId)

            socket.emit("join", roomId)
            socket.on("join", onUserJoined)
            socket.on("message", onNewMessage)
            socket.on("disconnect", onUserDisconnect)

        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        send.setOnClickListener {
            socket.emit("sendmessage", userName, messageInput.text.toString())
            messageInput.setText("")
        }
    }

    private val onUserJoined = Emitter.Listener {
        runOnUiThread {
            Log.d("CHAT TAG", "on user joined chat")
            runOnUiThread {
                Toast.makeText(this, "A user joined the chat !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val onUserDisconnect = Emitter.Listener { args ->
        runOnUiThread {
            Log.d("CHAT TAG", "on userdisconnect trigger")
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val onNewMessage = Emitter.Listener { args ->
        runOnUiThread {

            Log.d("CHAT TAG", "Received message")

            val data = args[0] as JSONObject
            val username: String
            val message: String

            username = data.getString("senderNickname")
            message = data.getString("message")

            Log.d("CHAT TAG", message)

            val m = Message(username, message)
            messageArray.add(m)
            val adapter = ChatConversationAdapter(messageArray) {
                runOnUiThread {
                    Toast.makeText(this, "You clicked a message", Toast.LENGTH_LONG).show()
                }
            }

            messageList.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}