package myapp.com.karry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.backer_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.User

class BackerViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class BackersAdapter(private var backerList: List<User>, val click: (userId: String) -> Unit, val deleteBacker: (userId: String) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<BackerViewHolder>() {

    private var isDeleteMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackerViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.backer_row, parent, false)
        return BackerViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return backerList.count()
    }

    fun setTrashButtons() {
        isDeleteMode = !isDeleteMode
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BackerViewHolder, position: Int) {
        val backer = backerList[position]
        val fullname = backer.firstname + " " + backer.lastname
        Glide.with(holder.view).load(backer.profilePicture).circleCrop().into(holder.view.backerAvatar)
        if(isDeleteMode) { holder.view.trashButton.visibility = View.VISIBLE } else { holder.view.trashButton.visibility = View.GONE }
        holder.view.backerCard.setOnClickListener { click(backer._id) }
        holder.view.trashButton.setOnClickListener { deleteBacker(backer._id) }
        holder.view.backerName.text = fullname
    }
}