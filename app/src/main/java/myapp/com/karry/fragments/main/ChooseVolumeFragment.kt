package myapp.com.karry.fragments.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_choose_volume.view.*

import myapp.com.karry.R
import myapp.com.karry.model.SharedViewModel

class ChooseVolumeFragment : Fragment() {

    private lateinit var model: SharedViewModel

    private val SMALL = 1
    private val MEDIUM = 2
    private val BIG = 3

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
        val v = inflater.inflate(R.layout.fragment_choose_volume, container, false)

        v.backButton.setOnClickListener { replaceFragment(CreateTripStepTwoFragment()) }
        v.closeIcon.setOnClickListener { replaceFragment(SearchFragment()) }

        v.small.setOnClickListener {
            replaceFragment(CreateTripStepTwoFragment())
            model.carryVolume.value = SMALL
        }
        v.medium.setOnClickListener {
            replaceFragment(CreateTripStepTwoFragment())
            model.carryVolume.value = MEDIUM
        }
        v.big.setOnClickListener {
            replaceFragment(CreateTripStepTwoFragment())
            model.carryVolume.value = BIG
        }

        // Inflate the layout for this fragment
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
