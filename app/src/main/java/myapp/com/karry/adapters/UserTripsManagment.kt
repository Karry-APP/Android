package myapp.com.karry.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import myapp.com.karry.fragments.main.PassedTrips
import myapp.com.karry.fragments.main.RunningTrips

class UserTripsManagment(fm: FragmentManager, private var tabCount: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> RunningTrips()
            1 -> PassedTrips()
            else -> RunningTrips()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}