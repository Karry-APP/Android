package myapp.com.karry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_discussion_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.Message

class ChatConversationAdapter (private val messagesList: List<Message>, val click: (message: Message) -> Unit): RecyclerView.Adapter<ChatConversationAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.message_discussion_row, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messagesList[position]
        holder.setData(message)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setData(message: Message) {
            itemView.messageInput.text = message.content
            itemView.setOnClickListener {
                click(message)
            }
        }
    }
}