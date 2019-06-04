package myapp.com.karry.fragments.chat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_chat_contact.*
import kotlinx.android.synthetic.main.fragment_chat_contact.view.*
import myapp.com.karry.R
import myapp.com.karry.activities.AskingContactActivity
import myapp.com.karry.adapters.ContactAdapter
import myapp.com.karry.entity.Request
import myapp.com.karry.modules.TokenManager
import myapp.com.karry.network.UsersService

class ChatContactFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_chat_contact, container, false)
        v.chatRequestsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)

        val token: String? = TokenManager(this.context!!).deviceToken ?: ""
        UsersService.getRequests(token!!, { requestArray -> onSuccess(v, requestArray) }, { onError()})
        return v
    }

    private fun onSuccess(v: View, tripsArray: List<Request>) = activity?.runOnUiThread {
        v.chatRequestsList.adapter = ContactAdapter(tripsArray) {
            val intent = Intent(this.context, AskingContactActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onError() = activity?.runOnUiThread {
        Toast.makeText(this.context, "Oups something went wrong", Toast.LENGTH_LONG).show()
    }
}
