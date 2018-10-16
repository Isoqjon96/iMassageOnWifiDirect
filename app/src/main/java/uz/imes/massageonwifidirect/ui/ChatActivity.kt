package uz.imes.massageonwifidirect.ui

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_chat.*
import uz.imes.massageonwifidirect.R
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.net.wifi.p2p.*
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_created_host.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket


class ChatActivity : AppCompatActivity() {
    companion object {
        var Ischek: Int = 0
        lateinit var ouser: String
        val READ_MESSAGE = 1
    }

    override fun onDestroy() {
        wifiManager.isWifiEnabled = false
        super.onDestroy()
    }

    lateinit var array: ArrayList<String>
    fun readMES(temp: String) {
        array.add(temp)
    }


    val intentFilter: IntentFilter? = IntentFilter()
    lateinit var mChannel: WifiP2pManager.Channel
    lateinit var wifiManager: WifiManager
    lateinit var mManager: WifiP2pManager
    lateinit var recieve: WifiDirectRecieve
    lateinit var peers: ArrayList<WifiP2pDevice>
    lateinit var deviceNameArray: Array<String?>
    lateinit var deviceArray: Array<WifiP2pDevice?>
    lateinit var serverClass: ServerClass
    lateinit var clientClass: ClientClass
    lateinit var sendResive: SendResive


    private lateinit var mrecycler: RecyclerView
    private var adapter: RoomItemAdapter = RoomItemAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mrecycler = recycleritem
        mrecycler.layoutManager = LinearLayoutManager(applicationContext)
        mrecycler.adapter = adapter


        peers = ArrayList()
        wifiManager = baseContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        mManager = baseContext.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        mChannel = mManager.initialize(this, mainLooper, null)

        recieve = WifiDirectRecieve(mManager, mChannel, this)

        intentFilter?.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)

        intentFilter?.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)

        intentFilter?.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)

        intentFilter?.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

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

        peerListView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                val device = deviceArray[i]
                var wifigonfig = WifiP2pConfig()
                wifigonfig.deviceAddress = device?.deviceAddress
                mManager.connect(mChannel, wifigonfig, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() {
                        Toast.makeText(applicationContext, "${device?.deviceName} ulandi!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(p0: Int) {
                        Toast.makeText(applicationContext, "${device?.deviceName} ulanmadi!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
//        sendButton.setOnClickListener {
//            // sendResive = SendResive()
//            val s:String = writeMsg.text.toString()
//            sendResive.write(s.toByteArray())
//        }
    }


    var peerListListener: WifiP2pManager.PeerListListener = object : WifiP2pManager.PeerListListener {
        override fun onPeersAvailable(p0: WifiP2pDeviceList?) {
            if (p0?.deviceList!! != peers) {
                peers.clear()
                peers.addAll(p0.deviceList)
                deviceNameArray = arrayOfNulls<String>(p0.deviceList.size)
                deviceArray = arrayOfNulls<WifiP2pDevice>(p0.deviceList.size)
                for ((i, device) in p0.deviceList.withIndex()) {
                    deviceNameArray[i] = device.deviceName
                    deviceArray[i] = device

                }
                var arrayAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, deviceNameArray)
                peerListView.adapter = arrayAdapter

            }

            if (peers.size == 0) {
                Toast.makeText(applicationContext, "device topilmadi", Toast.LENGTH_SHORT).show()
            }
        }

    }
    var connectionInfoManager = object : WifiP2pManager.ConnectionInfoListener {
        override fun onConnectionInfoAvailable(p0: WifiP2pInfo?) {
            val groupOwnerAddress: InetAddress = p0!!.groupOwnerAddress
            if (p0.groupFormed && p0.isGroupOwner) {
                // connectionStatus.text = "Host"
                serverClass = ServerClass()
                serverClass.start()
            } else {
                if (p0.groupFormed)
                    clientClass = ClientClass(groupOwnerAddress)
                clientClass.start()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        registerReceiver(recieve, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(recieve)
    }

    val handler = Handler(Handler.Callback { msg ->
        when (msg?.what) {
            READ_MESSAGE -> {
                var readBuffer = msg.obj as ByteArray
                var tempMsg = String(readBuffer, 0, msg.arg1)
            }
        }
        true
    })

    inner class ServerClass : Thread() {
        lateinit var socket: Socket
        lateinit var serverSocket: ServerSocket
        override fun run() {
            try {
                serverSocket = ServerSocket(8888)
                socket = serverSocket.accept()
                sendResive = SendResive(socket)
                sendResive.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            super.run()
        }
    }

    inner class SendResive(sk: Socket) : Thread() {
        private var socket: Socket = sk
        lateinit var inputStream: InputStream
        lateinit var outputStream: OutputStream

        init {
            try {
                inputStream = socket.getInputStream()
                outputStream = socket.getOutputStream()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun run() {
            var byte = ByteArray(1024)
            var bytes: Int
            while (socket != null) {
                try {
                    bytes = inputStream.read(byte)
                    if (bytes > 0) {
                        handler.obtainMessage(READ_MESSAGE, bytes, -1, byte).sendToTarget()
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            super.run()
        }

        fun write(bytes: ByteArray) {
            Thread(Runnable {
                try {
                    outputStream.write(bytes)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }).start()
        }

    }


    inner class ClientClass(hostAddress: InetAddress) : Thread() {
        var socket: Socket
        var hostAdd: String

        init {
            hostAdd = hostAddress.hostAddress
            socket = Socket()
        }

        override fun run() {
            try {
                socket.connect(InetSocketAddress(hostAdd, 8888), 500)
                sendResive = SendResive(socket)
                sendResive.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            super.run()
        }
    }

}
