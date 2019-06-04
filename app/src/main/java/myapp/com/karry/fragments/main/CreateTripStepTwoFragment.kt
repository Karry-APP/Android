package myapp.com.karry.fragments.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_create_trip_step_two.*
import kotlinx.android.synthetic.main.fragment_create_trip_step_two.view.*
import myapp.com.karry.R
import myapp.com.karry.model.SharedViewModel
import java.lang.Float.parseFloat

class CreateTripStepTwoFragment : Fragment() {

    private lateinit var model: SharedViewModel
    private var weight: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putFloat("weight", weight)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState !== null) {
            weight = savedInstanceState.getFloat("weight", 0.0f)
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_create_trip_step_two, container, false)


        if (model.carryWeight.value !== null ) {
            v.weightParam.text = Editable.Factory.getInstance().newEditable(model.carryWeight.value.toString())
        }
        if (model.carryVolume.value !== null ) {
            v.volumeValue.text = when (model.carryVolume.value.toString()) {
                "1" -> "Petit"
                "2" -> "Moyen"
                "3" -> "Grand"
                else -> ""
            }
        } else {
            v.volumeValue.visibility = View.GONE
        }

        if (model.carryVolume.value !== null && model.carryWeight.value !== null) {
            v.validStepTwo.setOnClickListener {
                model.orderPayload.carryWeight = parseFloat(weightParam.text.toString())
                replaceFragment(CreateTripStepTreeFragment())
            }
        } else {
            v.validStepTwo.isEnabled = false
        }



        v.backButton.setOnClickListener { replaceFragment(CreateTripFragment()) }
        v.closeIcon.setOnClickListener { activity?.finish() }

        v.volumeParamContainer.setOnClickListener { replaceFragment(ChooseVolumeFragment()) }



        // Inflate the layout for this fragment
        return v
    }


    override fun onResume() {
        super.onResume()
        activity?.runOnUiThread {
            weightParam.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        model.carryWeight.value = parseFloat(s.toString())
                    }
                }
            })
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        fragmentTransaction.add(R.id.fragmentContainer2, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}
