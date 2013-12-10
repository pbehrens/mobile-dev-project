package com.example.remotelight;

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
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Ranulfo on 11/3/13.
 */
public class SessionThread extends AsyncTask<String , Integer, String> {

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
        return session.isConnected();
    }

    private boolean initialized;
    boolean kill = false;
    HashMap<String, Command> commandHashMap;

    public void disconnectSession(){
        kill = true;
    }

    public SessionThread(String username, String password, String host, Context context){
        this.username = username;
        this.password = password;
        this.host = host;
        this.session = null;
        this.context = context;
    }

    public Session getSession(){
        if(session == null){
            return null;
        }
        return session;
    }

    @Override
    protected void onProgressUpdate(Integer... value) {
        super.onProgressUpdate(value);
        if(value[0] == 1){
            Toast.makeText(context,
                    "Session Initialized", Toast.LENGTH_LONG).show();
            //((Commands) context).init = true;
            //TextView temp
        }
        if(value[0] == 2){
            Toast.makeText(context,
                    "Session Disconected", Toast.LENGTH_LONG).show();
            //((Commands) context).temperature.setText("echo Temperature=24C");
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        try{
            jsch = new JSch(); // THIS WASN'T HERE BEFORE
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
                publishProgress(1);

                while(kill == false){
                }
            }
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
}
