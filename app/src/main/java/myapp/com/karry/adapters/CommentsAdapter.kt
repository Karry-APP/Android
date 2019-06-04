package myapp.com.karry.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.comment_row.view.*
import myapp.com.karry.R
import myapp.com.karry.entity.Comment

class CommentViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

class CommentsAdapter(private var commentList: List<Comment>, val click: (commentValue: String) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.comment_row, parent, false)
        return CommentViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return commentList.count()
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.view.userName.text = comment.creator
        holder.view.commentContent.text = comment.value
        holder.view.createdDate.text = comment.created
    }
}