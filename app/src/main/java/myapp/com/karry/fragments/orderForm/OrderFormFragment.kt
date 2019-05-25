package myapp.com.karry.fragments.orderForm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_order_form.*
import kotlinx.android.synthetic.main.fragment_order_form.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.MainActivity
import myapp.com.karry.entity.Request
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.RequestsService

class OrderFormFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_order_form, container, false)

        v.input_name.setText( if ((activity?.intent!!.getStringExtra("name")) != null) activity?.intent!!.getStringExtra("name") else null)
        v.input_reward.setText( if ((activity?.intent!!.getStringExtra("reward")) != null) activity?.intent!!.getStringExtra("reward") else null)
        v.input_message.setText( if ((activity?.intent!!.getStringExtra("message")) != null) activity?.intent!!.getStringExtra("message") else null)
        v.valuePriceEstimate.text = if ((activity?.intent!!.getStringExtra("priceEstimate")) != null) activity?.intent!!.getStringExtra("priceEstimate") else null
        v.valueVolume.text = if ((activity?.intent!!.getStringExtra("volumeEstimate")) != null) activity?.intent!!.getStringExtra("volumeEstimate") else null
        v.valueWeight.text = if ((activity?.intent!!.getStringExtra("weightEstimate")) != null) activity?.intent!!.getStringExtra("weightEstimate") else null

        v.link_price_estimate_fragment.setOnClickListener { launchFragment(PriceEstimateOrderFragment()) }
        v.link_weight_estimate_fragment.setOnClickListener { launchFragment(WeightEstimateOrderFragment()) }
        v.link_volume_estimate_fragment.setOnClickListener { launchFragment(VolumeEstimateOrderFragment()) }

        v.buttonSendOrder.setOnClickListener{sendOrderForm()}

        return v
    }

    private fun launchFragment(fragment: Fragment) {

        if (input_name.text.toString() != "") {
            activity?.intent!!.putExtra("name", input_name.text.toString())
        }
        if (input_reward.text.toString() != "") {
            activity?.intent!!.putExtra("reward", input_reward.text.toString())
        }
        if (input_message.text.toString() != "") {
            activity?.intent!!.putExtra("message", input_message.text.toString())
        }

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
            R.anim.slide_out_left)
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun sendOrderForm() {
        val token = TokenManager(this.requireContext()).deviceToken ?: ""
        buttonSendOrder.visibility = View.INVISIBLE
        progressSendOrder.visibility = View.VISIBLE
        if (orderInfoAsJson() != "") {
            RequestsService.create(orderInfoAsJson(), token, { request -> goToMainActivity() }, { onError() })

        } else {
            onError()
        }
    }

    private fun onError() = activity?.runOnUiThread {
        orderFormError.text = getString(R.string.OrderFormFragment_errorSaveForm)
        orderFormError.visibility = View.VISIBLE
        buttonSendOrder.visibility = View.VISIBLE
        progressSendOrder.visibility = View.INVISIBLE
    }

    private fun goToMainActivity() = activity?.runOnUiThread {
        startActivity(Intent(this.context, MainActivity::class.java))
    }

    private fun orderInfoAsJson(): String {
        val name: String = input_name.text.toString()
        val reward: String = input_reward.text.toString()
        val estimatePrice: String = valuePriceEstimate.text.toString()
        val estimateVolume: String = valueVolume.text.toString()
        val estimateWeight: String = valueWeight.text.toString()
        val message: String = input_message.text.toString()

        val creator = UserInfoManager(this.context!!).id ?: ""
        val tripId: String = activity?.intent!!.getStringExtra("trip_id")

        if (name != "" && estimatePrice != "" && estimateVolume != "" && estimateWeight != ""
            && reward != "" && message != "" && creator != "" && tripId != "") {
            val request = Request(name, estimatePrice, estimateVolume, estimateWeight, reward, message, creator, tripId)
            return Gson().toJson(request)


        } else {
            val request = ""
            return request
        }
    }
}