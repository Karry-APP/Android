package myapp.com.karry.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import myapp.com.karry.fragments.main.PassedRequests
import myapp.com.karry.fragments.main.RunningRequests

class UserRequestsAdapter(fm: FragmentManager, private var tabCount: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> RunningRequests()
            1 -> PassedRequests()
            else -> RunningRequests()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}