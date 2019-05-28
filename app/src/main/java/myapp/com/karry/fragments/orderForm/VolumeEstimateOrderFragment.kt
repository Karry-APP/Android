package myapp.com.karry.fragments.orderForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_order_form_volume_estimate.*
import kotlinx.android.synthetic.main.fragment_order_form_volume_estimate.view.*
import myapp.com.karry.R

class VolumeEstimateOrderFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_order_form_volume_estimate, container, false)

        v.back_arrow.setOnClickListener {launchFragment(OrderFormFragment())}

        v.layout_radioButton.setOnClickListener{v.radioButton.toggle()}
        v.layout_radioButton1.setOnClickListener{v.radioButton1.toggle()}
        v.layout_radioButton2.setOnClickListener{v.radioButton2.toggle()}

        v.radioButton.setOnCheckedChangeListener { bv, isChecked -> updateValue("PETIT") }
        v.radioButton1.setOnCheckedChangeListener { bv, isChecked -> updateValue("MOYEN") }
        v.radioButton2.setOnCheckedChangeListener { bv, isChecked -> updateValue("GRAND") }

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

    fun updateValue(radioValue: String) {
        activity?.intent!!.putExtra("volumeEstimate", radioValue)

        if (radioValue != null) {
            activity?.volumeFormError?.visibility = View.INVISIBLE
            launchFragment(OrderFormFragment())
        } else {
            activity?.volumeFormError?.visibility = View.VISIBLE
        }
    }

}