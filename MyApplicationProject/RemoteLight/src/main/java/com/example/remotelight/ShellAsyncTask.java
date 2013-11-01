package com.example.remotelight;

import android.os.AsyncTask;
import android.text.Editable;
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
import java.util.Properties;


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
        JSch jsch = new JSch();
        String command = commands[0];
        Log.e("ssh", commands[0]);
//        String username = "wluw";
//        String password = "b3rcsdfg";
//        String host     = "wluw.org"; // sample ip address
        String username = "pcduino";
        String password = "honig08";
        String host = "192.168.43.171";
        Log.e("ssh", "is this working");
        try {

                session = jsch.getSession(username, host, 22);
                session.setPassword(password);
//                Toast.makeText(getApplicationContext(), "working....", Toast.LENGTH_LONG).show();

                Properties properties = new Properties();
                properties.put("StrictHostKeyChecking", "no");
                session.setConfig(properties);
                session.connect(3000);

                channel = session.openChannel("shell");
                channel.setInputStream(bitArrayInputStream);
                channel.setOutputStream(bitArrayOutputStream);
                channel.connect();
                if(channel.isConnected()){
                    Log.e("ssh", "connected");

                    Channel channel= session.openChannel("exec");
                    ((ChannelExec)channel).setCommand(command);

// X Forwarding
// channel.setXForwarding(true);

//channel.setInputStream(System.in);
                    channel.setInputStream(null);

                    ((ChannelExec)channel).setErrStream(System.err);

                    InputStream in=channel.getInputStream();
                    OutputStream out=channel.getOutputStream();

                    channel.connect();

                    out.write("ls".getBytes());
                    out.flush();

                    byte[] tmp=new byte[1024];
                    while(true){
                        while(in.available()>0){
                            int i=in.read(tmp, 0, 1024);
                            if(i<0)break;
                            System.out.print(new String(tmp, 0, i));
                            Log.e("ssh", new String(tmp, 0, i));
                        }
                        if(channel.isClosed()){
                            System.out.println("exit-status: "+channel.getExitStatus());
                            break;
                        }
                        try{Thread.sleep(1000);}catch(Exception ee){}
                    }
                    channel.disconnect();
                    session.disconnect();
                }else{
                    Log.e("ssh", "Not Connected");
                }


                    //Log.e("ssh-AA", baos.toString());
                } catch (JSchException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
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
