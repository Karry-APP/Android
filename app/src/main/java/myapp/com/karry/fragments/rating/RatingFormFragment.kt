package myapp.com.karry.fragments.rating

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.fragment_rating_form.*
import kotlinx.android.synthetic.main.fragment_rating_form.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.MainActivity
import myapp.com.karry.entity.Request
import myapp.com.karry.entity.RequestRating
import myapp.com.karry.entity.Trip
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.modules.UserInfoManager
import myapp.com.karry.network.RequestsService
import myapp.com.karry.network.TripsService
import myapp.com.karry.network.UsersService
import org.json.JSONObject






class RatingFormFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_rating_form, container, false)

        setText(v)

        v.btn_sendForm.setOnClickListener{ sendRatingForm() }

        return v
    }

    private fun launchFragment(fragment: Fragment) {

        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
            android.R.anim.slide_out_right)
        fragmentTransaction.replace(myapp.com.karry.R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    fun onRatingChanged(v: View) {

        activity?.intent!!.putExtra("ratingValue", v.ratingBar.getRating().toString())
        rating_text.text = v.ratingBar.getRating().toString()
    }


    private fun setText(v: View) {

        if (activity?.intent!!.getStringExtra("ownerId") == activity?.intent!!.getStringExtra("actualUserId")) {
            v.rating_text.text = "Si vous deviez noter la personne qui vous a commandé cet objet vous lui donneriez: "

        } else {
            v.rating_text.text = "Si vous deviez noter le voyageur qui vous a rapporté cet objet vous lui donneriez: "

        }
        v.rating_trip_date.text = activity?.intent!!.getStringExtra("endDate")
    }

    private fun sendRatingForm() {
        val token = TokenManager(this.requireContext()).deviceToken ?: ""
        btn_sendForm.visibility = View.INVISIBLE
        btn_progressBar.visibility = View.VISIBLE

        UsersService.patchProfile(ratingInfoAsJson(), token, { request -> goToMainActivity() }, { onError() })


        //if (ratingInfoAsJson() != "") {
        //    UsersService.patchProfile(ratingInfoAsJson(), token, { request -> goToMainActivity() }, { onError() })
//
        //} else {
        //    onError()
        //}
    }

    private fun goToMainActivity() = activity?.runOnUiThread {
        startActivity(Intent(this.context, MainActivity::class.java))
    }

    private fun onError() = activity?.runOnUiThread {
        btn_sendForm.visibility = View.VISIBLE
        error_sendForm.visibility = View.VISIBLE
        btn_progressBar.visibility = View.INVISIBLE

    }

    private fun ratingInfoAsJson():JSONObject {
        val userObject= JSONObject()

        userObject.put("ratings", ratingBar.getRating().toString())
        userObject.put("userID", activity?.intent!!.getStringExtra("actualUserId"))

        return userObject


        //val rate: String = activity?.intent!!.getStringExtra("radioRate")
        ////val comment: String = edit_comment.text.toString()
        //val user_id = activity?.intent!!.getStringExtra("actualUserId")
        //val user_firstname = activity?.intent!!.getStringExtra("actualFirstname")
        //val user_lastname = activity?.intent!!.getStringExtra("actualLastname")
//
        //if (rate != "" ) {
        //    val request = RequestRating(user_id, user_firstname, user_lastname, rate)
        //    return userObject
//
        //} else {
        //    val request = ""
        //    //return request
        //}
    }

}