package uz.imes.massageonwifidirect.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_waiting.*
import uz.imes.massageonwifidirect.R

class WaitingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)
        sbWait.visibility = View.VISIBLE
    }
}
