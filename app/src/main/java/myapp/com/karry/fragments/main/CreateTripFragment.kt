package myapp.com.karry.fragments.main


import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_create_trip.*
import kotlinx.android.synthetic.main.fragment_create_trip.view.*
import kotlinx.android.synthetic.main.help_dialog_layout.view.*

import myapp.com.karry.R
import myapp.com.karry.model.SharedViewModel
import java.text.SimpleDateFormat
import java.util.*



class CreateTripFragment : Fragment() {

    private lateinit var model: SharedViewModel
    private var destinationValue: String = "Ville de départ de votre voyage"
    private var departureValue: String = "Ville d’arrivée de votre voyage"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_create_trip, container, false)

        v.closeIcon.setOnClickListener { activity?.finish() }

        if (model.roundTrip.value !== null) {
            v.roundTrip.isChecked = model.roundTrip.value!!
        }

        if (model.arrivalDate.value !== null) {
            v.arrivalDate.text = Editable.Factory.getInstance().newEditable(model.arrivalDate.value)
        }

        if (model.departureValue.value.isNullOrEmpty() && model.destinationValue.value.isNullOrEmpty() && model.arrivalDate.value.isNullOrEmpty()) {
            v.validStepOne.isEnabled = false
        } else {
            v.validStepOne.setOnClickListener {
                replaceFragment(CreateTripStepTwoFragment())
                model.roundTrip.value = v.roundTrip.isChecked
            }
        }

        v.departureCity.setOnClickListener { openCitySearch("departure", "createTrip") }
        v.departureLabel.setOnClickListener { openCitySearch("departure", "createTrip") }
        v.destinationCity.setOnClickListener { openCitySearch("destination", "createTrip") }
        v.destinationLabel.setOnClickListener { openCitySearch("destination", "createTrip") }

        v.helpButton.setOnClickListener {
            showHelperDialog(v)
        }

        v.arrivalDate.setOnClickListener {
            val curCalender = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this.context!!,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val dateShipMillis = calendar.timeInMillis

                    model.arrivalDate.value = getFormattedDateToISO8601(dateShipMillis)
                    arrivalDate.text = Editable.Factory.getInstance().newEditable(getFormattedDateSimple(dateShipMillis))
                },

                curCalender.get(Calendar.YEAR),
                curCalender.get(Calendar.MONTH),
                curCalender.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = curCalender.timeInMillis
            datePickerDialog.show()
        }

        v.description.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    model.description.value = s.toString()
                }
            }
        })

        displayReceivedData(v)

        return v
    }

    fun getFormattedDateSimple(dateTime: Long?): String {
        val newFormat = SimpleDateFormat("dd/MM/yyyy")
        return newFormat.format(Date(dateTime!!))
    }

    fun getFormattedDateToISO8601(dateTime: Long?): String {
        val newFormat = SimpleDateFormat("dd-MM-yy:HH:mm:SS")
        return newFormat.format(Date(dateTime!!))
    }

    private fun openCitySearch(currentSearch: String, currentFragment: String) {
        val bundle = Bundle()
        val cityPickerFragment = CityPickerFragment()
        bundle.putString("currentDirection", currentSearch)
        bundle.putString("currentFragment", currentFragment)
        cityPickerFragment.arguments = bundle

        replaceFragment(cityPickerFragment)
    }

    private fun displayReceivedData(v: View) {
        destinationValue = model.destinationValue.value.toString()
        departureValue = model.departureValue.value.toString()

        v.departureCity.text = Editable.Factory.getInstance().newEditable(departureValue)
        v.destinationCity.text = Editable.Factory.getInstance().newEditable(destinationValue)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        fragmentTransaction.replace(R.id.fragmentContainer2, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun closeForm() {
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, SearchFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun showHelperDialog(v: View) {
        val helpDialogLayout = R.layout.help_dialog_layout
        val mDialogView = LayoutInflater.from(v.context).inflate(helpDialogLayout, null)

        val alertDialog = AlertDialog.Builder(v.context) // this: Activity
            .setView(mDialogView)
            .create()
        alertDialog.show()

        mDialogView.skipButton.setOnClickListener {
            alertDialog.dismiss()
        }

    }
}
