package myapp.com.karry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.contact_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.Request

class ContactViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class ContactAdapter(private var requesList: List<Request>, val click: (request: Request) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.contact_row, parent, false)
        return ContactViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return requesList.count()
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val request = requesList[position]
        holder.view.demande_title.text = request.name
        holder.view.creatorName.text = request.creator
        holder.view.setOnClickListener { click(request) }
    }
}