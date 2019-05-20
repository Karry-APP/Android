package myapp.com.karry.fragments.orderForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_order_form_weight_estimate.*
import kotlinx.android.synthetic.main.fragment_order_form_weight_estimate.view.*
import myapp.com.karry.R

class WeightEstimateOrderFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_order_form_weight_estimate, container, false)

        v.back_arrow.setOnClickListener {launchFragment(OrderFormFragment())}

        v.layout_radioButton.setOnClickListener{v.radioButton.toggle()}
        v.layout_radioButton1.setOnClickListener{v.radioButton1.toggle()}
        v.layout_radioButton2.setOnClickListener{v.radioButton2.toggle()}
        v.layout_radioButton3.setOnClickListener{v.radioButton3.toggle()}

        v.radioButton.setOnCheckedChangeListener { bv, isChecked -> updateValue("0-2kg") }
        v.radioButton1.setOnCheckedChangeListener { bv, isChecked -> updateValue("2-10kg") }
        v.radioButton2.setOnCheckedChangeListener { bv, isChecked -> updateValue("10-20kg") }
        v.radioButton3.setOnCheckedChangeListener { bv, isChecked -> updateValue("+20kg") }

        return v
    }

    private fun launchFragment(fragment: Fragment) {

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    }

    fun updateValue(radioValue: String) {
        activity?.intent!!.putExtra("weightEstimate", radioValue)

        if (radioValue != null) {
            activity?.weightFormError?.visibility = View.INVISIBLE
            launchFragment(OrderFormFragment())
        } else {
            activity?.weightFormError?.visibility = View.VISIBLE
        }
    }

}