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
        var i = Intent(this,CreatedHostActivity::class.java)
        tvJoinHost.setOnClickListener {
            i.putExtra("name",1)
            startActivity(i)
        }
        tvCreateHost.setOnClickListener {
            i.putExtra("name",2)
            startActivity(i)
        }
    }
}
