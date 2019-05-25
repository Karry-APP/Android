package myapp.com.karry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_transaction_details.view.*
import kotlinx.android.synthetic.main.shared_image_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.SharedImage


class SharedImageViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class SharedImagesAdapter(private var sharedImagesList: List<SharedImage>) : androidx.recyclerview.widget.RecyclerView.Adapter<SharedImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedImageViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.shared_image_row, parent, false)
        return SharedImageViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return sharedImagesList.count()
    }

    override fun onBindViewHolder(holder: SharedImageViewHolder, position: Int) {
        val sharedImage = sharedImagesList[position]

        Glide
            .with(holder.view)
            .load(sharedImage.src)
            .into(holder.view.sharedImage)
    }
}