package myapp.com.karry.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_user_trip.*
import myapp.com.karry.R
import myapp.com.karry.adapters.CardBackersAdapter
import myapp.com.karry.entity.Trip
import myapp.com.karry.entity.User
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.TripsService
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Rect
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import myapp.com.karry.model.SharedViewModel


class UserTripActivity : AppCompatActivity() {
    private lateinit var  model: SharedViewModel

    private var backerList: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_trip)

        model = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        loadTripDetails()
        backers.setOnClickListener { loadBackers(this) }
        addedBackersList.setOnClickListener { loadBackers(this) }
        closeDetailsTripButton.setOnClickListener { onBackPressed() }
    }


    private fun loadTripDetails() {
        val tripID = intent.getStringExtra("EVENT_ID")
        val token = TokenManager(this).deviceToken.toString()

        TripsService.tripById(tripID, token, {
            if (it.id.isEmpty()) {
                Log.d("yay", "Empty")
            } else {
                backerList = it.joinList
                setTexts(it)
            }
        }, {
            Log.d("yoy", "Something bad happened")
        })
    }

    private fun loadAddedBackers(backerArray: ArrayList<User>) {
        addedBackersList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        addedBackersList.addItemDecoration(OverlapDecoration())
        addedBackersList.setHasFixedSize(true)
        addedBackersList.adapter = CardBackersAdapter(backerArray)
    }

    private fun setTexts(it: Trip) = runOnUiThread {
        loadAddedBackers(it.joinList)
        departureCity.text = it.departureCity.capitalize()
        destinationCity.text = it.destinationCity.capitalize()
        tripDescription.text = it.description
    }

    private fun loadBackers(c: Context) {
        val intent = Intent(c, UserTripBackersActivity::class.java)
        val jsonArray = Gson().toJson(backerList)

        intent.putExtra("JOIN_LIST", jsonArray)
        c.startActivity(intent)
    }
}

class OverlapDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        outRect.set(0, 0, vertOverlap, 0)
    }
    companion object {
        private val vertOverlap = -40
    }
}
