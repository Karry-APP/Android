package myapp.com.karry.fragments.rating

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.fragment_rating_form.*
import kotlinx.android.synthetic.main.fragment_rating_form.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.MainActivity
import myapp.com.karry.entity.Request
import myapp.com.karry.entity.RequestRating
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.UsersService
import org.json.JSONObject


class RatingFormFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(myapp.com.karry.R.layout.fragment_rating_form, container, false)

        setText(v)

        v.btn_sendForm.setOnClickListener{
            if(ratingBar.getRating() != 0.0f) {
                sendRatingForm()
            } }

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

    private fun setText(v: View) {

        v.rating_trip_date.text = activity?.intent!!.getStringExtra("endDate")
    }

    private fun sendRatingForm() {
        val token = TokenManager(this.requireContext()).deviceToken ?: ""
        btn_sendForm.visibility = View.INVISIBLE
        btn_progressBar.visibility = View.VISIBLE

        UsersService.patchProfile(ratingInfoAsJson(), token, { request -> goToMainActivity() }, { onError() })

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

        val oldRate = activity?.intent!!.getStringExtra("actualRatings").toFloat()
        val formRate = ratingBar.getRating()
        var newRate: Float = 0.0f

        if (formRate > oldRate) {
            newRate = (formRate - oldRate)
            newRate = oldRate + (newRate / 2) //TODO get et increment un attribut du user qui continet le nombre de vote qu'il a reçu

        } else {
            newRate = (oldRate - formRate)
            newRate = oldRate - (newRate / 2) //TODO get et increment un attribut du user qui continet le nombre de vote qu'il a reçu

        }

        //TODO iterer nombre de vote

        val number3digits:Double = String.format("%.3f", newRate).toDouble()
        val number2digits:Double = String.format("%.2f", number3digits).toDouble()
        val solutionRate:Double = String.format("%.1f", number2digits).toDouble()

        userObject.put("ratings", solutionRate)
        userObject.put("userID", activity?.intent!!.getStringExtra("actualUserId"))

        return userObject
    }

}