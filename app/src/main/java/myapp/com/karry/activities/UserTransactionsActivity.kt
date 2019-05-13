package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_user_transactions.*
import myapp.com.karry.R
import myapp.com.karry.adapters.TransactionsAdapter
import myapp.com.karry.model.SharedViewModel

class UserTransactionsActivity : AppCompatActivity() {
    private lateinit var  model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_transactions)
        model = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        closeMytTransactionsButton.setOnClickListener {
            model.cleanTransactionList()
            onBackPressed()
        }
        bindView()
    }


    private fun bindView() {
        model.storeTransactions()
        val transactionListArray = model.transactionListArray
        transactionsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.baseContext)
        transactionsList.adapter = TransactionsAdapter(transactionListArray)
    }
}
