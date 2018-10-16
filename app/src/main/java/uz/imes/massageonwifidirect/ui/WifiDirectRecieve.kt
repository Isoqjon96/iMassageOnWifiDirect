package uz.imes.massageonwifidirect.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import uz.imes.massageonwifidirect.ui.CreatedHostActivity

/**
 * Created by Isoq Hakimov on 11.10.2018.
 */
class WifiDirectRecieve(var manager: WifiP2pManager, var channel: WifiP2pManager.Channel, var activity: ChatActivity) : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        when (intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // Determine if Wifi P2P mode is enabled or not, alert
                // the Activity.
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
                    Toast.makeText(context, "Wifi yoqiq", Toast.LENGTH_SHORT).show()
                else Toast.makeText(context, "Wifi o'chiq", Toast.LENGTH_SHORT).show()

            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {

                if (manager != null) {
                    manager.requestPeers(channel, activity.peerListListener)
                }

                // The peer list has changed! We should probably do something about
                // that.

            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                if (manager == null) return
                var networkinfo = intent.getParcelableExtra<NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)
                if (networkinfo.isConnected) {
                    manager.requestConnectionInfo(channel, activity.connectionInfoManager)
                } else {
                   // activity.connectionStatus.setText("Ulanmadi")
                }
                // Connection state changed! We should probably do something about
                // that.

            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {

            }
        }
    }
}
