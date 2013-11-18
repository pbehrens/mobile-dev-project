package com.example.remotelight;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by thebeagle on 11/16/13.
 */

public class SSHService extends Service {

    private static final String ACTION = "com.example.remotelight.COMMAND_SENT";
    LocalBinder serviceBinder = new LocalBinder();

    private static final String COMMMAND_STATIC = "com.example.remotelight.COMMAND_STATIC";
    private static final String COMMMAND_DYNAMIC = "com.example.remotelight.COMMAND_DYNAMIC";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ssh", "Received start id " + startId + ": " + intent);


        Log.e("ssh", "started szervice");

        return Service.START_STICKY;
    }



    @Override
    public IBinder onBind(Intent intent) {

        return serviceBinder;
    }


    public class LocalBinder extends Binder {
        public SSHService getServerInstance() {
            return SSHService.this;
        }
    }

    public void sendCommand(String command){
        Log.e("ssh", command);
        Intent intent = new Intent();
        intent.setAction(COMMMAND_STATIC);
        sendBroadcast(intent);
    }


}