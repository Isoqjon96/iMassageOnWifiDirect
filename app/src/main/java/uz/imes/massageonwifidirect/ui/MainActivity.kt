package uz.imes.massageonwifidirect.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import uz.imes.massageonwifidirect.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvJoinHost.setOnClickListener {
            startActivity(Intent(this, WaitingActivity::class.java))
        }
        tvCreateHost.setOnClickListener {
            startActivity(Intent(this, CreatedHostActivity::class.java))
        }
    }
}
