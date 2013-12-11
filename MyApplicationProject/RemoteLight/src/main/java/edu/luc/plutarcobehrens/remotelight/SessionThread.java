package edu.luc.plutarcobehrens.remotelight;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.example.remotelight.Command;


/**
 COPYRIGHT (C) <2013> <plutarcobehrens>. All Rights Reserved.
A thread that keeps the SSh session open so commands can be sent to the server.
 @author <rplutarco>
 @version <1.0> <date:2013-12-9>
 */
public class SessionThread extends AsyncTask<String , Integer, String> {
    private Exception exception;
    Session session;
    JSch jsch;
    String username;
    String password;
    String host;
    int timeout;
    Context context;
    private boolean initialized;
    boolean kill = false;

    /**
     * Creates a session thread that stays alive between commands
     * Used to keep session alive if activty changes as well
     * @param username
     * @param password
     * @param host
     * @param context
     */
    public SessionThread(String username, String password, String host, Context context){
        this.username = username;
        this.password = password;
        this.host = host;
        this.session = null;
        this.context = context;
        this.initialized = false;
    }

    /**
     * Check if controller is initialized
     * @return initialized state of controller
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Disconnect the session by ending the while loop
     */
    public void disconnectSession(){
        kill = true;
    }

    /**
     * returns the current session or else null
     * @return current session
     */
    public Session getSession(){
        if(session == null){
            return null;
        }
        return session;
    }


    /**
     * Show updates on the SSh session through Toast
     * @param value //dummy value needed for async task
     */
    @Override
    protected void onProgressUpdate(Integer... value) {
        super.onProgressUpdate(value);
        //show if session initialized
        if(value[0] == 1){
            Toast.makeText(context,
                    "Session Initialized", Toast.LENGTH_LONG).show();

        }
        //show if not initialized
        if(value[0] == 2){
            Toast.makeText(context,
                    "Session Disconected", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        try{
            jsch = new JSch(); // THIS WASN'T HERE BEFORE
            session = jsch.getSession(username, host, 22);
            session.setPassword(password);
            // set some vanilla properties for the connection

            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
            session.connect(timeout);

            //open the shell and set the I/O for i
            if(session.isConnected()){
                Log.e("ssh", "connection success");
                initialized = true;
                publishProgress(1);

                while(kill == false){
                }
            }
            initialized = false;
            publishProgress(2);
            return "complete";
        }
        catch (JSchException e) {
            Log.e("ssh", "expection" + e.toString());
            e.printStackTrace();
            //return false;
        }
        return "complete";
    }



}
