package com.example.remotelight;

import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Ranulfo on 11/3/13.
 */
public class SessionThread extends AsyncTask<String , Session, String> {

    private Exception exception;
    Session session;
    ByteArrayOutputStream bitArrayOutputStream;
    ByteArrayInputStream bitArrayInputStream;
    Channel channel;
    JSch jsch;
    String username;
    String password;
    String host;
    int timeout;
    boolean initialized;
    boolean kill = false;
    HashMap<String, Command> commandHashMap;

    public void disconnectSession(){
        kill = true;
    }

    public SessionThread(String username, String password, String host){
        this.username = username;
        this.password = password;
        this.host = host;
        this.jsch = new JSch();
        this.timeout = 0;
        this.initialized = false;
        this.commandHashMap = new HashMap<String, Command>();

    }
    public Session getSession(){
        return session;
    }


    @Override
    protected void onProgressUpdate(Session... values) {
        super.onProgressUpdate(values);


    }

    @Override
    protected String doInBackground(String... params) {
    try{
        session = jsch.getSession(username, host, 22);
        session.setPassword(password);

        // set some vanilla properties for the connection
        //TODO: more customized ssh connection properties
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        session.connect(timeout);

        //open the shell and set the I/O for i
        if(session.isConnected()){
            Log.e("ssh", "connection success");
            initialized = true;
            publishProgress(session);
            //return true;
            while(kill == false){
                //Loop

            }
        }
    }
    catch (JSchException e1) {

        e1.printStackTrace();
        //return false;
    }
    Log.e("ssh", "session disconnected");
    session.disconnect();
    //return false;
    return null;
    }
}
