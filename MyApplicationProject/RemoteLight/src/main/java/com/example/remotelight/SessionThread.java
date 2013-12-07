package com.example.remotelight;

import android.content.Intent;
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

        return "fopo";
    }


}
