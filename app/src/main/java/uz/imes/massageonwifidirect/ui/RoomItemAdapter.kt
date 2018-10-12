package uz.imes.imessanger

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.my_message.view.*
import kotlinx.android.synthetic.main.other_message.view.*
import uz.imes.massageonwifidirect.R

/**
 * Created by Isoq Hakimov on 09.10.2018.
 */
class RoomItemAdapter(val context: Context) : RecyclerView.Adapter<MessagesViewHolder>() {

    private val MY_MESSAGE_KEY = 1
    private val OTHER_MESSAGE_KEY = 2
    var Messages: ArrayList<MessageData> = ArrayList()

    fun addMessage(message: MessageData) {
        Messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val message = Messages.get(position)
        var a: Int
        if (App.user.equals(message.User)) {
            a = MY_MESSAGE_KEY
        } else {
            a = OTHER_MESSAGE_KEY
        }

        return a
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MessagesViewHolder {
        return if (viewType == MY_MESSAGE_KEY) {
            MyMessageViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.my_message, parent, false))
        } else {
            OtherMessageViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.other_message, parent, false))
        }
    }

    override fun getItemCount(): Int = Messages.size

    override fun onBindViewHolder(holder: MessagesViewHolder?, position: Int) {
        val message = Messages.get(position)
        holder?.bind(message)

    }


}

open class MessagesViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    open fun bind(message: MessageData) {}
}

class MyMessageViewHolder(itemview: View) : MessagesViewHolder(itemview) {
    var messageText: TextView = itemview.tv_MyMessage
    private var messageTime: TextView = itemview.tv_MyMessageTime
    override fun bind(message: MessageData) {
        messageText.text = message.messageText
        messageTime.text = message.date

    }
}

class OtherMessageViewHolder(itemview: View) : MessagesViewHolder(itemview) {
    private var User: TextView = itemview.tv_OtherUser
    private var messageText: TextView = itemview.findViewById(R.id.tv_OtherMessage)
    private var messageTime: TextView = itemview.tv_OtherMessageTime
    override fun bind(message: MessageData) {
        User.text = message.User
        messageText.text = message.messageText
        messageTime.text = message.date
    }
}