package uz.imes.massageonwifidirect.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_chat.*
import uz.imes.imessanger.RoomItemAdapter
import uz.imes.massageonwifidirect.R

class ChatActivity : AppCompatActivity() {
    companion object {
        var Ischek: Int = 0
        lateinit var ouser: String
    }

    private lateinit var mrecycler: RecyclerView
    private var adapter: RoomItemAdapter = RoomItemAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mrecycler = recycleritem
        mrecycler.layoutManager = LinearLayoutManager(applicationContext)
        mrecycler.adapter = adapter

    }
}
