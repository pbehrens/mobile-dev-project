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

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.example.remotelight.Command;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by thebeagle on 11/16/13.
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

    @Override
    public void onCreate (){
        sessionController = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.jsch = new JSch();
        this.timeout = 0;
        this.initialized = false;
        this.commandHashMap = new HashMap<String, Command>();
        this.kill = false;
        this.sessionConnected = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(sessionIsValid()){
            Log.i("ssh", "Received start id " + startId + ": " + intent);
            Log.e("ssh", "started szervice");
            //
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
                    sessionConnected = false;
                    Log.e("ssh", "connection success");
                    initialized = true;
                    //return true;
                    while(kill == false || sessionConnected){
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

            return Service.START_STICKY;
        }

        return -1;
    }

    public void setSessionData(String username, String password, String host){
        this.username = username;
        this.password = password;
        this.host = host;
    }


    public boolean sessionIsValid(){
        if(sessionController != null && sessionController.checkHostUserPassword()){
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
                //return true;
                while(kill == false || true){
                    //Loop

                }
            }
            return true;
        }
        catch (JSchException e1) {

            e1.printStackTrace();
            //return false;
        }
        return false;

    }

    public void sendCommand(String command){
        Log.e("ssh", command);
        if(sessionIsValid()){
            String result = sessionController.runCommand(command);
        }
        else{

        }

        Intent intent = new Intent();
        intent.setAction(COMMMAND_STATIC);
        sendBroadcast(intent);
    }



    public void sendConnectionStateBroadcast(){
        Intent intent = new Intent();

        if(sessionController.isUp()){
            intent.setAction(SESSION_IS_UP);
        }
        else{
            intent.setAction((SESSION_IS_DOWN));
        }
        sendBroadcast(intent);
    }

    public void sshLogin(String host, String username, String password){
        sessionController = new SessionController(username, password, host);
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
        if(!sessionConnected){
            connectSession();
        }
        try{
            Channel channel= session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            channel.setInputStream(null);

            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();
            OutputStream out=channel.getOutputStream();

            channel.connect();

            out.write(command.getBytes());
            out.flush();
            String response = "";
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    String newChars = new String(tmp, 0, i);
                    response += newChars;
                    System.out.print(new String(tmp, 0, i));
//                        Log.e("ssh", new String(tmp, 0, i));
                    Log.e("ssh", response);
                }
                if(channel.isClosed()){
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
//                Log.e("ssh", "the last pid is " + this.getLastPid());
            return response;
        }catch (JSchException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            Log.e("ssh", "it don't work");
            e1.printStackTrace();
        } catch (Exception e) {
    //      not connected to session
            Log.e("ssh", e.toString()+"HERE");
            e.printStackTrace();
        }
        return "";
    }

}