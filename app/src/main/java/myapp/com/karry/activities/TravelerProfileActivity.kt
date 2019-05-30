package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_traveler_profile.*
import myapp.com.karry.R
import myapp.com.karry.adapters.CommentsAdapter
import myapp.com.karry.entity.Comment
import myapp.com.karry.entity.Trip

class TravelerProfileActivity : AppCompatActivity() {
    private val commentList: ArrayList<Comment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traveler_profile)

        closeTravelerProfile.setOnClickListener { onBackPressed() }
        travelerName.text = intent.getStringExtra("ownerName")
        travelerLocation.text = "TODO" //TODO location traveler dans son compte
        tripsCount.text = "TODO" //TODO get Count trip
        transactionCount.text = "TODO" //TODO get Count transaction
        rate.text = intent.getStringExtra("ownerRatings") //TODO rating traveler dans son compte
        travelerDescriptionValue.text = intent.getStringExtra("ownerDescription") //TODO description traveler dans son compte

        fillComments()

        bindView()
    }



    private fun bindView() {
        runOnUiThread {
            travelerComments.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            travelerComments.adapter = CommentsAdapter(commentList) {}
        }
    }

    private fun fillComments() {
        // TODO COMMENTS
        commentList.add(Comment("Nico", "Nec minus feminae quoque calamitatum participes fuere similium. nam ex hoc quoque sexu peremptae sunt originis altae conplures", "20/04/2019"))
        commentList.add(Comment("Nico", "Nec minus feminae quoque calamitatum participes fuere similium.", "20/04/2019"))
        commentList.add(Comment("Nico", "Nec minus feminae quoque calamitatum participes fuere similium. nam ex hoc quoque sexu peremptae sunt originis altae conplures", "20/04/2019"))
    }
}
