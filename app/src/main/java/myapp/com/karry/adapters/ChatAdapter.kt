package myapp.com.karry.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import myapp.com.karry.fragments.chat.ChatMessagesFragment
import myapp.com.karry.fragments.chat.ChatRequestFragment

class ChatAdapter(fm: FragmentManager, private var tabCount: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> ChatMessagesFragment()
            1 -> ChatRequestFragment()
            else -> ChatMessagesFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}