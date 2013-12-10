package com.example.remotelight;

import android.content.Context;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by thebeagle on 11/2/13.
 */
public class SessionController implements Serializable{
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
    Context context;

    public boolean isInitialized() {
        return sessionThread.isInitialized();
    }

    boolean initialized;
    SessionThread sessionThread;

    HashMap<String, Command> commandHashMap;



    public SessionController(String username, String password, String host, Context context){
        this.username = username;
        this.password = password;
        this.host = host;
        this.jsch = new JSch();
        this.timeout = 0;
        this.initialized = false;
        this.context = context;
        this.commandHashMap = new HashMap<String, Command>();
    }

    public SessionController(){
        this.username = null;
        this.password = null;
        this.host = null;
        this.jsch = new JSch();
        this.timeout = 0;
        this.initialized = false;
        this.commandHashMap = new HashMap<String, Command>();
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

    public void setSessionThread(SessionThread sessionThread){
        this.sessionThread = sessionThread;
    }



    public void initSession() {
        //attempt to start an ssh session and create a shell for further commands
        sessionThread = new SessionThread(username,password,host, context);
        sessionThread.execute("str","str","str");
        while(sessionThread.getSession() == null){
            try{Thread.sleep(1000); Log.e("ssh", "waiting");}catch(Exception ee){}
        }
        session = sessionThread.getSession();

        if(session.isConnected()){
            initialized = true;
        }
        else{
            initialized = false;
        }

    }

    public String runCommand(String command){
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
                Log.e("ssh", e.toString()+"HERE");
                e.printStackTrace();

            }
        return "";
    }

    public void disconnect(){
        sessionThread.disconnectSession();
    }

    public boolean isUp(){
        return session.isConnected();
    }

    public String getCommandList(){
        if(initialized == true){
            String commandList = this.runCommand("echo ./commands.json");
            return commandList;
        }
        else{
            return "";
        }

    }


}


