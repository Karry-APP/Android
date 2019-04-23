package myapp.com.karry.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import myapp.com.karry.fragments.chat.ChatContactFragment
import myapp.com.karry.fragments.chat.ChatMessageFragment

class ChatAdaptater(fm: FragmentManager, private var tabCount: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> ChatMessageFragment()
            1 -> ChatContactFragment()
            else -> ChatMessageFragment()
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}