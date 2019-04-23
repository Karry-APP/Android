package myapp.com.karry.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_introduction.*
import myapp.com.karry.R
import myapp.com.karry.adapters.IntroductionAdapter
import myapp.com.karry.fragments.introductions.Introduction1
import myapp.com.karry.fragments.introductions.Introduction2
import myapp.com.karry.transformers.ParallaxTransformer

class IntroductionActivity : AppCompatActivity() {

    private lateinit var introductionAdapter: IntroductionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)


        introductionAdapter = IntroductionAdapter(supportFragmentManager)
        introductionAdapter.addFragment(Introduction1())
        introductionAdapter.addFragment(Introduction2())
        introductionSlider.adapter = introductionAdapter
        introductionSlider.setPageTransformer(false, ParallaxTransformer())
        introduction_viewpager_dots_indicator.setViewPager(introductionSlider)
    }

    fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun getItem(i: Int): Int {
        return introductionSlider.currentItem + i
    }
}
