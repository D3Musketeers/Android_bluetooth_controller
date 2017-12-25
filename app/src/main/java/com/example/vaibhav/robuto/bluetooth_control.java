package com.example.vaibhav.robuto;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.R.attr.action;
import static android.R.id.list;
import static android.util.Log.VERBOSE;

public class bluetooth_control extends AppCompatActivity {
    private static final String TAG = "bluetooth_control_act";
    ListView Bluetooth_paired_found_list;
    ListView Bluetooth_new_found_list;
    BluetoothAdapter BlueAdaptor = BluetoothAdapter.getDefaultAdapter();
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    Set<BluetoothDevice>pairedDevices;
    Set<BluetoothDevice>NewDevices;
    List<Object> deviceItemList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_control);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
        ArrayAdapter<String> pairedDevicesArrayAdapter =
                new ArrayAdapter<String>(this, R.layout.activity_bluetooth_control);
        mNewDevicesArrayAdapter =
                new ArrayAdapter<String>(this, R.layout.activity_bluetooth_control);

        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.listView_paired_devices);
        pairedListView.setAdapter(pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.ListView_new_device);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        Set<BluetoothDevice> pairedDevices = BlueAdaptor.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            findViewById(R.id.Paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = "None Paired";
            pairedDevicesArrayAdapter.add(noDevices);
        }


    }


    public void scandevices (View view) {
       // Intent intent = new Intent(this,scandevic.class);
       // startActivity(intent);
        int REQUEST_ENABLE_BT = 1;
        if (BlueAdaptor == null) {
            AlertDialog.Builder alert_build = new AlertDialog.Builder(this);
            alert_build.setTitle("Bluetooth Missing!");
            alert_build.setMessage("Your phone does not have Bluetooth. Get lost!!");
            alert_build.setPositiveButton("Exit",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alertdialog = alert_build.create();
            alertdialog.show();

        }

        if(!BlueAdaptor.isEnabled()) {
            Intent enable_bluetooth = new Intent(BlueAdaptor.ACTION_REQUEST_ENABLE);
            startActivityForResult(enable_bluetooth,REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_SHORT).show();
       } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_SHORT).show();
        }

        if (BlueAdaptor.isDiscovering()) {
            BlueAdaptor.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        BlueAdaptor.startDiscovery();


    }

    public void off(View v){
        BlueAdaptor.disable();
        Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = getIntent().getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice new_device = getIntent().getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //ArrayList list = new ArrayList();
                if(new_device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(new_device.getName() + "\n" + new_device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(getApplicationContext(), "Bluetooth device discovery done!", Toast.LENGTH_LONG).show();
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = "None found";
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

   // private AdapterView.OnItemClickListener myListClockListner = new AdapterView.OnItemClickListener()

    @Override
    public void onPause() {
    super.onPause();
        Log.v(TAG, "ON PAUSE");
       // unregisterReceiver(mreceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (BlueAdaptor != null) {
            BlueAdaptor.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            BlueAdaptor.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Create the result Intent and include the MAC address
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            // Set result and finish this Activity
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };


}





