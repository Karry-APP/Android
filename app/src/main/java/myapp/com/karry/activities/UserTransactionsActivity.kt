package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_user_transactions.*
import myapp.com.karry.R
import myapp.com.karry.adapters.TransactionsAdapter
import myapp.com.karry.entity.UserRequest
import myapp.com.karry.model.SharedViewModel
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.RequestsService

class UserTransactionsActivity : AppCompatActivity() {

    private lateinit var  model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_transactions)

        model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        loadTransactions()
        closeMytTransactionsButton.setOnClickListener { model.cleanTransactionList(); onBackPressed() }
    }

    private fun loadTransactions() {
        val token = TokenManager(this).deviceToken
        RequestsService.getRequests(token.toString(), {
            if (it.isNullOrEmpty()) {

            } else {
                runOnUiThread {
                    Log.d("yay", it.size.toString())
                    bindView(it)
                }
            }
        }, {

        })
    }


    private fun bindView(transactionListArray: Array<UserRequest>) {
            transactionsList.layoutManager = LinearLayoutManager(this.baseContext)
            transactionsList.adapter = TransactionsAdapter(transactionListArray)

    }
}
