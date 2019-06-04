package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_user_transactions.*
import kotlinx.android.synthetic.main.activity_user_trip_backers.*
import kotlinx.android.synthetic.main.backer_row.*
import myapp.com.karry.R
import myapp.com.karry.adapters.BackersAdapter
import myapp.com.karry.adapters.TransactionsAdapter
import myapp.com.karry.entity.Trip
import myapp.com.karry.entity.User
import myapp.com.karry.model.SharedViewModel

class UserTripBackersActivity : AppCompatActivity() {

    private var backerArrayList : ArrayList<User> = arrayListOf()

    private lateinit var backersAdapter: BackersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_trip_backers)

        val jsonArray = intent.getStringExtra("JOIN_LIST")
        val backerArray = Gson().fromJson(jsonArray, Array<User>::class.java)
        backerArrayList = backerArray.toCollection(ArrayList())
        backersAdapter = BackersAdapter(backerArrayList, {
        }, {
        })
        closeTripBackersButton.setOnClickListener { onBackPressed() }
        backersList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.baseContext)
        backersList.adapter = backersAdapter
    }
}
