package myapp.com.karry.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_traveler_profile.*
import myapp.com.karry.R
import myapp.com.karry.adapters.CommentsAdapter
import myapp.com.karry.entity.Comment

class TravelerProfileActivity : AppCompatActivity() {
    private val commentList: ArrayList<Comment> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traveler_profile)

        closeTravelerProfile.setOnClickListener { onBackPressed() }
        travelerName.text = "John Doe"
        travelerLocation.text = "Londres"
        travelerDescriptionValue.text = "Nec minus feminae quoque calamitatum participes fuere similium. nam ex hoc quoque sexu peremptae sunt originis altae conplures, adulteriorum flagitiis obnoxiae vel stuprorum. "
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
        commentList.add(Comment("Nico", "Nec minus feminae quoque calamitatum participes fuere similium. nam ex hoc quoque sexu peremptae sunt originis altae conplures", "20/04/2019"))
        commentList.add(Comment("Nico", "Nec minus feminae quoque calamitatum participes fuere similium.", "20/04/2019"))
        commentList.add(Comment("Nico", "Nec minus feminae quoque calamitatum participes fuere similium. nam ex hoc quoque sexu peremptae sunt originis altae conplures", "20/04/2019"))
    }
}
