package com.example.remotelight;

import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by thebeagle on 10/29/13.
 */

public class ShellAsyncTask extends AsyncTask<String , String, String> {

    private Exception exception;
    Session session;
    ByteArrayOutputStream bitArrayOutputStream;
    ByteArrayInputStream bitArrayInputStream;
    Channel channel;
    JSch jsch;
    static String command;


    public static void setCommand(String comd){
        command = comd;

    }

    protected String doInBackground(String... commands) {
        SessionController sessionController = new SessionController("mathilda", "foobar", "192.168.1.198");
        boolean isInitialized = false;//sessionController.initSession();
        if(isInitialized){
            Log.e("ssh", "session was initialized");
            sessionController.runCommand(commands[0]);
//            sessionController.runCommand("echo $!");
        }
        else{
            Log.e("ssh", "session wasn't initialized");
        }
        return "testing";
    }

    protected void onProgressUpdate(Integer... progress) {
        //usr_nm.setText(asd);
        //cantar();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

    public static void setCommand(Editable text) {
    }
}
