package com.example.remotelight;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by thebeagle on 11/17/13.
 */
public class SSHReceiver extends BroadcastReceiver {


    private static final String COMMMAND_STATIC = "com.example.remotelight.COMMAND_STATIC";
    private static final String COMMMAND_DYNAMIC = "com.example.remotelight.COMMAND_DYNAMIC";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("ssh", "recevived a broadcast");
        Log.e("ssh", intent.getStringExtra("message"));
        try {
            if (intent.getAction().equals(COMMMAND_STATIC)) {
                Log.e("ssh", "statstic command");

            }

            if (intent.getAction().equals(COMMMAND_DYNAMIC)) {
                Log.e("ssh", "dynamic command");


            }
        } catch (NullPointerException npe) {
            // Don't care
        }
    }
}
