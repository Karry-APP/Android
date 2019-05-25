package myapp.com.karry.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_transaction_details.*
import kotlinx.android.synthetic.main.added_backer_row.view.*
import kotlinx.android.synthetic.main.backer_row.*
import kotlinx.android.synthetic.main.backer_row.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.TransactionDetailsActivity
import myapp.com.karry.activities.UserTripBackersActivity
import myapp.com.karry.entity.Backer
import myapp.com.karry.entity.Transaction
import myapp.com.karry.entity.User

class CardBackerViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)
class CardBackersAdapter(private var backerList: List<User>) : androidx.recyclerview.widget.RecyclerView.Adapter<BackerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackerViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.added_backer_row, parent, false)
        return BackerViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return backerList.count()
    }

    override fun onBindViewHolder(holder: BackerViewHolder, position: Int) {
        val backer = backerList[position]
        Glide
            .with(holder.view.context)
            .load("https://" + backer.profilePicture)
            .circleCrop()
            .into(holder.view.addedBacker)
    }
}

