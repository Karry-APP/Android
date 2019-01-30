package myapp.com.karry.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_trips.view.*

import myapp.com.karry.R
import myapp.com.karry.activities.TripFormActivity

class TripsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_trips, container, false)
        v.openTripForm.setOnClickListener { startTripFormActivity() }
        return v
    }

    private fun startTripFormActivity() {
        startActivity(Intent(this.context, TripFormActivity::class.java))
    }
}
