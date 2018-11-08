package cubex.mahesh.bt_nov9am

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bAdapter = BluetoothAdapter.getDefaultAdapter()
        s1.isChecked = bAdapter.isEnabled
        s1.setOnCheckedChangeListener {
            compoundButton, b ->
            if(b){
                bAdapter.enable()
            }else{
                bAdapter.disable()
            }
        }

        scanBTDevices.setOnClickListener {

            bAdapter.startDiscovery()
            var temp_list = mutableListOf<String>()
            var adapter = ArrayAdapter<String>(
                    this@MainActivity,
                    android.R.layout.simple_list_item_single_choice,
                    temp_list)
            lview.adapter = adapter

            var ifilter = IntentFilter( )
            ifilter.addAction(BluetoothDevice.ACTION_FOUND)
            registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    var device = p1!!.getParcelableExtra<BluetoothDevice>(
                            BluetoothDevice.EXTRA_DEVICE)
                    temp_list.add(device.name+"\n"+device.address)
                    adapter.notifyDataSetChanged()
                }
            },ifilter)

        }

    }
}
