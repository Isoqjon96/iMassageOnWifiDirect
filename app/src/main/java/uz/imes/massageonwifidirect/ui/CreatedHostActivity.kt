package uz.imes.massageonwifidirect.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import uz.imes.massageonwifidirect.R

class CreatedHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_created_host)

//        val wifiManager = ChatActivity



        //        onOff.setOnClickListener {
//            if (wifiManager.isWifiEnabled) {
//                wifiManager.isWifiEnabled = false
//                onOff.text = "On"
//            } else {
//                wifiManager.isWifiEnabled = true
//                onOff.text = "Off"
//            }
//        }
//
//        discover.setOnClickListener {
//            mManager.discoverPeers(mChannel, object : WifiP2pManager.ActionListener {
//                override fun onSuccess() {
//                    connectionStatus.text = "qiditish boshlandi"
//                }
//
//                override fun onFailure(p0: Int) {
//                    connectionStatus.text = "topilmadi"
//                }
//
//            })
//        }




        if(intent.getIntExtra("name",0)==1){
//            ltCreate.visibility =View.GONE
//            ltWait.visibility = View.VISIBLE
        }else{
//            ltCreate.visibility =View.VISIBLE
//            ltWait.visibility = View.GONE
        }

    }
}
