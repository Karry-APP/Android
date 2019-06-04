package myapp.com.karry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import myapp.com.karry.R
import myapp.com.karry.entity.Room

class RoomViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class RoomAdapter(private var roomList: List<Room>, val click: (roomId: String) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<RoomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.message_row, parent, false)
        return RoomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return roomList.count()
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = roomList[position]
        //holder.view.demande_title.text = request.name
        //holder.view.creatorName.text = request.creator
        holder.view.setOnClickListener { click(room.id) }

    }
}