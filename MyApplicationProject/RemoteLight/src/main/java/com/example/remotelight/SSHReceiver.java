package com.example.remotelight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 COPYRIGHT (C) <2013> <plutarco>. All Rights Reserved.
Failed attempt as using a broadcast receiver to make the project fancier
 @author <pbehrens>
 @version <1.0> <date:2013-12-9>
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
