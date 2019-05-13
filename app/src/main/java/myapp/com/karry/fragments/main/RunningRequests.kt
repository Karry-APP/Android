package myapp.com.karry.fragments.main


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_running_requests.view.*
import kotlinx.android.synthetic.main.fragment_search_results.view.*
import myapp.com.karry.R
import myapp.com.karry.adapters.RequestedTripsAdapter
import myapp.com.karry.adapters.TripsAdapter
import myapp.com.karry.adapters.UserRequestsAdapter
import myapp.com.karry.entity.Trip
import myapp.com.karry.model.SharedViewModel
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.TripsService
import myapp.com.karry.network.UsersService
import java.lang.Exception

class RunningRequests : Fragment() {

    private lateinit var  model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_running_requests, container, false)
        bindView(v)

        return v
    }

    private fun loadUserTrips(v: View) {
        val token = TokenManager(context!!).deviceToken.toString()
        val userId = UserInfoManager(this.requireContext()).id

        // if (model.tripListArray.isNullOrEmpty()) {
        //     UsersService.getCreatedTrips(token, userId,  {
        //         if (it.isNullOrEmpty()) {
        //             Log.d("yay", "Empty")
        //         } else {
        //             model.storeTrips(it)
        //             bindView(v)
        //         }
        //     }, {
        //         Log.d("yay", "Something bad happened")
        //     })
        // }
    }

    private fun bindView(v: View) {
        model.storeTrips(model.tripListArray)
        val tripListArray = model.tripListArray

        activity?.runOnUiThread {
            v.runningTripListRecylcerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)
            v.runningTripListRecylcerView.adapter = RequestedTripsAdapter(tripListArray)
        }
    }


}
