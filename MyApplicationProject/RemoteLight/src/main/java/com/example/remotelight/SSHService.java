package com.example.remotelight;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.HashMap;

import edu.luc.plutarcobehrens.remotelight.SessionController;
import edu.luc.plutarcobehrens.remotelight.SessionThread;


/**
 COPYRIGHT (C) <2013> <plutarco>. All Rights Reserved.
Failed code that was supposed to run the SSH session as a service so things could be communicated between activities better
 @author <rplutarco>
 @version <1.0> <date:2013-12-9>
 */
public class SSHService extends Service {

    private static final String ACTION = "com.example.remotelight.COMMAND_SENT";
    LocalBinder serviceBinder = new LocalBinder();
    private SessionController sessionController;
    private static final String COMMMAND_STATIC = "com.example.remotelight.COMMAND_STATIC";
    private static final String COMMMAND_DYNAMIC = "com.example.remotelight.COMMAND_DYNAMIC";
    private static final String SESSION_IS_UP =  "com.example.remotelight.SESSION_IS_UP";
    private static final String SESSION_IS_DOWN =  "com.example.remotelight.SESSION_IS_UP";


    private String username;
    private String password;
    private String host;
    private JSch jsch;
    private int timeout;
    private boolean initialized;
    private HashMap<String, Command> commandHashMap;
    private Exception exception;
    private boolean kill;
    private boolean sessionConnected;
    Session session;
    public String name;
    public boolean proceed;

    public SSHService() {
        sessionController = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.jsch = new JSch();
        this.timeout = 0;
        this.initialized = false;
        this.commandHashMap = new HashMap<String, Command>();
        this.kill = false;
        this.sessionConnected = false;
        this.proceed = true;

    }

    @Override
    public void onCreate (){
        this.username = null;
        this.password = null;
        this.host = null;
        this.jsch = new JSch();
        this.timeout = 0;
        this.initialized = false;
        this.commandHashMap = new HashMap<String, Command>();
        this.kill = false;
        this.sessionConnected = false;
        this.proceed = true;

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.username = intent.getStringExtra("username");
        this.password = intent.getStringExtra("password");
        this.host = intent.getStringExtra("host");
        //this.sessionController = new SessionController(username, password, host);


        return -1;
    }

    public void setSessionThread(SessionThread sessionThread){
        sessionController.setSessionThread(sessionThread);
        sessionController.initSession();
    }

    public void setSessionData(String username, String password, String host){
        this.username = username;
        this.password = password;
        this.host = host;
    }


    public boolean sessionIsValid(){
        if(username != null && password != null && host != null){
            return true;
        }
        return false;
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

    public boolean connectSession(){

        return false;

    }

    public void sendConnectionStateBroadcast(){
        Intent intent = new Intent();
        if(sessionConnected){
            intent.setAction(SESSION_IS_UP);
        }
        else{
            intent.setAction((SESSION_IS_DOWN));
        }
        sendBroadcast(intent);
    }

    public void sshLogin(String host, String username, String password){
        //sessionController = new SessionController(username, password, host);
        Log.e("ssh", "session hopefully logged in");
    }

    public boolean checkHostUserPassword(){
        if(username == null || password == null || host == null){
            return false;
        }
        return true;
    }

    public Session getSession(){
        if(session.isConnected()){
            return session;
        }
        return null;
    }


    public String runCommand(String command){

        return "";
    }

}