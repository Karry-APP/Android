package myapp.com.karry.fragments.orderForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_order_form_price_estimate.*
import kotlinx.android.synthetic.main.fragment_order_form_price_estimate.view.*


class PriceEstimateOrderFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_order_form_price_estimate, container, false)

        v.back_arrow.setOnClickListener {launchFragment(OrderFormFragment())}

        v.layout_radioButton.setOnClickListener{v.radioButton.toggle()}
        v.layout_radioButton1.setOnClickListener{v.radioButton1.toggle()}
        v.layout_radioButton2.setOnClickListener{v.radioButton2.toggle()}
        v.layout_radioButton3.setOnClickListener{v.radioButton3.toggle()}

        v.radioButton.setOnCheckedChangeListener { bv, isChecked -> updateValue("0-10€") }
        v.radioButton1.setOnCheckedChangeListener { bv, isChecked -> updateValue("10-30€") }
        v.radioButton2.setOnCheckedChangeListener { bv, isChecked -> updateValue("30-50€") }
        v.radioButton3.setOnCheckedChangeListener { bv, isChecked -> updateValue("50-100€") }
        v.radioButton4.setOnClickListener { customPriceValue(v.editPricePerso.text.toString()) }

        return v
    }

    private fun launchFragment(fragment: Fragment) {

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        fragmentTransaction.replace(myapp.com.karry.R.id.cityPickerContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    fun customPriceValue(radioValue: String) {

        if (radioValue != "") {
            activity?.intent!!.putExtra("priceEstimate", radioValue)
            activity?.priceFormError?.visibility = View.INVISIBLE
            launchFragment(OrderFormFragment())
        } else {
            activity?.priceFormError?.visibility = View.VISIBLE
        }
    }

    fun updateValue(radioValue: String) {
        activity?.intent!!.putExtra("priceEstimate", radioValue)

        if (radioValue != null || radioValue != "") {
            activity?.priceFormError?.visibility = View.INVISIBLE
            launchFragment(OrderFormFragment())
        } else {
            activity?.priceFormError?.visibility = View.VISIBLE
        }
    }

}