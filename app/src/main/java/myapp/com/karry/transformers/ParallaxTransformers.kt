package myapp.com.karry.transformers

import android.view.View
import android.widget.TextView


class ParallaxTransformer : androidx.viewpager.widget.ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if (position >= -1 && position <= 1) {
            //page.findViewById<TextView>(R.id.introductionTitle).translationX = (position * page.width * 2)
            //page.findViewById<TextView>(R.id.introductionDescription).translationX = (position * page.width * 3)
        } else {
            page.alpha = 1.toFloat()
        }
    }
}