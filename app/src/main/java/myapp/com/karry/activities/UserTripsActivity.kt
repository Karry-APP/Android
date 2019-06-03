package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_user_trips.*
import myapp.com.karry.R
import myapp.com.karry.adapters.UserRequestsAdapter

class UserTripsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_trips)

        val viewPager = userTripsViewPager
        val tabLayout = userTripsTabLayout
        configureTabLayout(viewPager, tabLayout)
        closeMyTripsButton.setOnClickListener { onBackPressed() }
    }

    private fun configureTabLayout(viewPager: ViewPager, tabLayout: TabLayout) {
        val adapter = UserRequestsAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        tabLayout.addTab(tabLayout.newTab().setText("En cours"))
        tabLayout.addTab(tabLayout.newTab().setText("Historique"))


        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) { viewPager.currentItem = tab.position }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}
