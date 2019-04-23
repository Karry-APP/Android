package myapp.com.karry.fragments.chat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.fragment_chat.view.*

import myapp.com.karry.R
import myapp.com.karry.adapters.ChatAdapter
import myapp.com.karry.modules.ApiManager

class ChatFragment : Fragment() {

    private lateinit var viewpager: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_chat, container, false)
        val viewPager = v.chatViewPager
        val tabLayout = v.chatTabLayout

        configureTabLayout(viewPager, tabLayout)
        return v
    }

    private fun configureTabLayout(viewPager: ViewPager, tabLayout: TabLayout) {

        tabLayout.addTab(tabLayout.newTab().setText("Messages"))
        tabLayout.addTab(tabLayout.newTab().setText("Demande de contact"))

        val adapter = ChatAdapter(activity?.supportFragmentManager!!, tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }

    fun setupConnection() {
        val socket: Socket = IO.socket(ApiManager.URL.BASE)
        socket.connect()
    }
}
