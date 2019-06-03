package myapp.com.karry.fragments.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_chat_contact.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.AskingContactActivity

class ChatContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_chat_contact, container, false)

        v.askingContactRow.setOnClickListener {
            val intent = Intent(this.context, AskingContactActivity::class.java)
            startActivity(intent)
        }

        return v
    }
}
