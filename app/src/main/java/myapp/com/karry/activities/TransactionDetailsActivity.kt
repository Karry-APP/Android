package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_transaction_details.*
import myapp.com.karry.R
import myapp.com.karry.adapters.SharedImagesAdapter
import myapp.com.karry.entity.SharedImage
import myapp.com.karry.entity.Transaction
import myapp.com.karry.entity.User
import myapp.com.karry.entity.UserRequest

class TransactionDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)

        closeTransactionDetailsButton.setOnClickListener { onBackPressed() }
        loadDetails()
    }

    private fun loadDetails() {
        val USER_REQUEST = intent.getStringExtra("USER_REQUEST").toString()

        Log.d("yay", USER_REQUEST)
        val currentRequest = Gson().fromJson(USER_REQUEST, UserRequest::class.java)

        transactionName.text = currentRequest.name
        transactionPrice.text = currentRequest.estimatePrice
    }
}
