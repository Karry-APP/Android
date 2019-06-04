package myapp.com.karry.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.transactions_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.Transaction
import myapp.com.karry.activities.TransactionDetailsActivity
import myapp.com.karry.entity.UserRequest

class TransactionViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class TransactionsAdapter(private val transactionList: Array<UserRequest>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<TransactionViewHolder>() {

    override fun getItemCount(): Int {
        return transactionList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.transactions_row, parent, false)
        return TransactionViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.view.transactionPrice.text = transaction.estimatePrice.toString()
        holder.view.transactionName.text = transaction.name
        holder.view.transactionDescription.text = transaction.message
        holder.view.transactionCard.setOnClickListener { v -> loadTransaction(v.context, transaction) }
    }

    private fun loadTransaction(c: Context, transaction: UserRequest) {
        val intent = Intent(c, TransactionDetailsActivity::class.java)
        intent.putExtra("USER_REQUEST", Gson().toJson(transaction))
        c.startActivity(intent)
    }
}