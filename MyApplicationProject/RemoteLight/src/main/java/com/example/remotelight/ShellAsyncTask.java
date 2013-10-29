package com.example.remotelight;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Properties;


/**
 * Created by thebeagle on 10/29/13.
 */
public class ShellAsyncTask extends AsyncTask<String, String, String> {

    private Exception exception;
    Session session;
    ByteArrayOutputStream bitArrayOutputStream;
    ByteArrayInputStream bitArrayInputStream;
    Channel channel;
    JSch jsch;

    protected String doInBackground(String... urls) {
        JSch jsch = new JSch();
        String username = "wluw";
        String password = "b3rcsdfg";
        String host     = "wluw.org"; // sample ip address
        Log.e("ssh", "is this working");
        try {
                session = jsch.getSession(username, host, 22);
                session.setPassword(password);
//                Toast.makeText(getApplicationContext(), "working....", Toast.LENGTH_LONG).show();

                Properties properties = new Properties();
                properties.put("StrictHostKeyChecking", "no");
                session.setConfig(properties);
                session.connect(30000);

                channel = session.openChannel("shell");
                channel.setInputStream(bitArrayInputStream);
                channel.setOutputStream(bitArrayOutputStream);
                channel.connect();
                if(channel.isConnected()){
                    Log.e("ssh", "connected");
                }
                else{
                    Log.e("ssh", "not connected");
                }
            } catch (JSchException e) {
                // TODO Auto-generated catch block
                Log.e("ssh", e.toString());
            }

        return "testing";
    }

    protected void onProgressUpdate(Integer... progress) {
//        Log.e("ssh", bitArrayOutputStream.toString());

    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
