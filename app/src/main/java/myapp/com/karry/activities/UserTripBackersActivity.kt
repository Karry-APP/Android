package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_user_transactions.*
import kotlinx.android.synthetic.main.activity_user_trip_backers.*
import kotlinx.android.synthetic.main.backer_row.*
import myapp.com.karry.R
import myapp.com.karry.adapters.BackersAdapter
import myapp.com.karry.adapters.TransactionsAdapter
import myapp.com.karry.model.SharedViewModel

class UserTripBackersActivity : AppCompatActivity() {
    private lateinit var  model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_trip_backers)

        model = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        bindView()
    }


    private fun bindView() {
        model.storeBackers()
        backersList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.baseContext)
        backersList.adapter = BackersAdapter(model.backerListArray)
    }
}
