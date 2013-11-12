package com.example.remotelight;

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
    boolean initialized;
    SessionThread sessionThread;

    HashMap<String, Command> commandHashMap;



    public SessionController(String username, String password, String host){
        this.username = username;
        this.password = password;
        this.host = host;
        this.jsch = new JSch();
        this.timeout = 0;
        this.initialized = false;
        this.commandHashMap = new HashMap<String, Command>();
    }

    public Session getSession(){
        if(session.isConnected()){
            return session;
        }
        return null;
    }

    public void initSession() {
        //attempt to start an ssh session and create a shell for further commands
        sessionThread = new SessionThread(username,password,host);
        sessionThread.execute();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("ssh","thread");
        }
        session = sessionThread.getSession();
        if(session == null)
            Log.e("ssh","Here shit");
        /*try{
            session = jsch.getSession(username, host, 22);
            session.setPassword(password);

            // set some vanilla properties for the connection
            //TODO: more customized ssh connection properties
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
            session.connect(timeout);

            //open the shell and set the I/O for it
            channel = session.openChannel("shell");
            channel.setInputStream(bitArrayInputStream);
            channel.setOutputStream(bitArrayOutputStream);
            channel.connect();
            if(channel.isConnected()){
                Log.e("ssh", "connection failed");
                initialized = true;
                return true;
            }
        }
        catch (JSchException e1) {
            e1.printStackTrace();
            return false;
        }
        Log.e("ssh", "connected and ready for exec channels");

        return false;*/
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


