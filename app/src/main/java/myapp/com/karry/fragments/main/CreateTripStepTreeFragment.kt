package myapp.com.karry.fragments.main


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_create_trip_step_tree.view.*

import myapp.com.karry.R
import myapp.com.karry.activities.MainActivity
import myapp.com.karry.model.SharedViewModel
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.TripsService
import java.lang.Integer.parseInt


class CreateTripStepTreeFragment : Fragment() {

    private lateinit var model: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_create_trip_step_tree, container, false)

        v.backButton.setOnClickListener { replaceFragment(CreateTripStepTwoFragment()) }
        v.closeIcon.setOnClickListener { activity?.finish() }


        if (model.carryTax.value !== null) {
            v.carryTax.text = Editable.Factory.getInstance().newEditable(model.carryTax.value.toString())
        }

        if (model.carryMaxAmount.value !== null) {
            v.carryMaxAmount.text = Editable.Factory.getInstance().newEditable(model.carryMaxAmount.value.toString())
        }


        v.carryTax.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    model.carryTax.value = parseInt(s.toString())
                }
            }
        })

        v.carryMaxAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    model.carryMaxAmount.value = parseInt(s.toString())
                }
            }
        })


        v.validStepTree.setOnClickListener {
            val token = TokenManager(context!!).deviceToken.toString()
            val userID = UserInfoManager(requireContext()).id
            model.userID.value = userID
            val payload = model.fillCreateOrderFormPayload().toString()

            TripsService.create(payload, token,{
                val intent = Intent(this.context, MainActivity::class.java)
                intent.putExtra("fragmentToDisplay", 2)
                startActivity(intent)
            }, {
            })
        }

        return v
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        fragmentTransaction.replace(R.id.fragmentContainer2, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}
