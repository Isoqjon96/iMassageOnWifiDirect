package uz.imes.massageonwifidirect.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_created_host.*
import uz.imes.massageonwifidirect.R

class CreatedHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_created_host)
        if(intent.getIntExtra("name",0)==1){
            ltCreate.visibility =View.GONE
            ltWait.visibility = View.VISIBLE
        }else{
            ltCreate.visibility =View.VISIBLE
            ltWait.visibility = View.GONE
        }
    }
}
