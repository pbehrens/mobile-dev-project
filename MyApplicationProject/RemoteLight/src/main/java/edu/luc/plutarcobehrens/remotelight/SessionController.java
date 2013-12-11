package edu.luc.plutarcobehrens.remotelight;

import android.content.Context;
import android.util.Log;

import com.example.remotelight.Command;
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
 COPYRIGHT (C) <2013> <plutarco>. All Rights Reserved.
Controls the SSH session thread that is initialized within and allows for a continuous connection
 @author <rplutarco>
 @version <1.0> <date:2013-12-9>
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


    /**
     Constructor for creating a session controller given the provided username, passwoird and host. Sets up the JSCH library controllers also
     @param <username>
     @param <password>
     @param <host>
     @param <context>


     @return new instance of a Session Controller()
     */
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

    /**
     Blank constructor for session controller sets all class variables to null and allows for them to be set in an other fashion
     */
    public SessionController(){
        this.username = null;
        this.password = null;
        this.host = null;
        this.jsch = new JSch();
        this.timeout = 0;
        this.initialized = false;
        this.commandHashMap = new HashMap<String, Command>();
    }

    /**
        Check to see if the host username and password are set

     * @return boolean value of if the host password and username are not null
     */
    public boolean checkHostUserPassword(){
        if(username == null || password == null || host == null){
            return false;
        }
        return true;
    }

    /**
     * Returns the current session if it is connected else it returns null
     *
     * @return Session or null
     */
    public Session getSession(){
        if(session.isConnected()){
            return session;
        }
        return null;
    }

    /**
     * Sets the session thread for the controller
     * @param sessionThread
     */
    public void setSessionThread(SessionThread sessionThread){
        this.sessionThread = sessionThread;
    }

    /**
        Attempt to start an ssh session and create a shell for further commands
        If the session is created the initialized class variable will be set accordingly
     */
    public void initSession() {
        //attempt to start an ssh session and create a shell for further commands
        sessionThread = new SessionThread(username,password,host, context);
        sessionThread.execute("str","str","str");
        //wait until the session class variable is not null
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

    /**
     *
     * @param command // what is to be sent to the ssh server
     * @return response //the response from the server or an empty string if something went wrong
     */
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

    /**
        Disconnect the session
     */
    public void disconnect(){
        sessionThread.disconnectSession();
    }

    /**
     * Check if the connection is sitll up
     * @return // boolean value of session state
     */
    public boolean isUp(){
        return session.isConnected();
    }

    /**
     * Method used for retrieving the commnad list from the server
     *
     * @return The command list JSON file contents
     */
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


