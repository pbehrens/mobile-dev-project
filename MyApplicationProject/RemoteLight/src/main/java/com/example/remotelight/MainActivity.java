package com.example.remotelight;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends Activity {
    //TODO: fix all the permissions for the variables
    EditText etIp;
    EditText etUsername;
    EditText etPassword;
    Parameters parameters;
    TextView resultTextView;
    Button sendCommandButton;
    SessionController sessionController;
    BroadcastReceiver sshBroadcastReceiver;
    private SSHService sshService;
    boolean isBound;
    SessionThread sessionThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewVariables();
        setClickListeners();

        /*
        Intent serviceStartIntent = new Intent(this, SSHService.class);
        serviceStartIntent.putExtra("username", etUsername.getText().toString());
        serviceStartIntent.putExtra("password", etPassword.getText().toString());
        serviceStartIntent.putExtra("host", etIp.getText().toString());
        startService(serviceStartIntent);

        sessionThread = (SessionThread) new SessionThread("pi", "foobar", "192.168.1.106" ).execute();

        initReceiver();
        sshBroadcastReceiver = new SSHReceiver();
        Log.e("ssh", "does it get before the async task");
        doBindService();*/
    }


    public void initReceiver(){
        sshBroadcastReceiver = new BroadcastReceiver() {
            private static final String COMMMAND_STATIC = "com.example.remotelight.COMMAND_STATIC";
            private static final String COMMMAND_DYNAMIC = "com.example.remotelight.COMMAND_DYNAMIC";

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("ssh", "recevived a broadcast");
                //look for what command was returned by the service and handle the right way
                try {
                    if (intent.getAction().equals(COMMMAND_STATIC)) {
                        Log.e("ssh", "static command");

                    }

                    if (intent.getAction().equals(COMMMAND_DYNAMIC)) {
                        Log.e("ssh", "dynamic command");
                    }
                } catch (NullPointerException npe) {
                    // Don't care
                }
            }
        };
        //TODO: make a list in the manifest instead of dynamically creating the actions
        IntentFilter filter = new IntentFilter("com.toxy.LOAD_URL");
        filter.addAction("com.example.remotelight.COMMAND_STATIC");
        filter.addAction("com.example.remotelight.COMMAND_STATIC");
        registerReceiver(sshBroadcastReceiver, filter);
    }

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(MainActivity.this,
                SSHService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        isBound = true;
        Log.e("ssh", "bound service");
    }

    private void doUnbindService() {
        if (isBound) {
            // Detach our existing connection.
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initializeSessionController();
    }

    public void setViewVariables(){
        etIp = (EditText) findViewById(R.id.etIp);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        resultTextView  = (TextView) findViewById(R.id.terminalTextView);
        sendCommandButton = (Button) findViewById(R.id.sendCommandButton);


        /*etIp.setText("192.168.1.106");
        etUsername.setText("pi");
        etPassword.setText("foobar");*/
        etIp.setText("192.168.43.171");
        etUsername.setText("pcduino");
        etPassword.setText("honig08");
    }

    public void initializeSessionController(){
        //sessionController = new SessionController("pcduino", "honig08", "192.168.43.171");
        //sessionController = new SessionController("mathilda", "foobar", "192.168.1.198");
       // sessionController.initSession();
        //sessionController.runCommand("ps ax | tail");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void setClickListeners(){
        sendCommandButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //Intent newIntent;
                etIp = (EditText) findViewById(R.id.etIp);
                etUsername = (EditText) findViewById(R.id.etUsername);
                etPassword = (EditText) findViewById(R.id.etPassword);
                Log.e("ssh", "Here got1");
                parameters = new Parameters(etUsername.getText().toString(),etPassword.getText().toString(),etIp.getText().toString());
                Log.e("ssh", "Here got2");
                Intent i = new Intent(MainActivity.this, Commands.class);
                Log.e("ssh", "Here got3");
                //Bundle b = i.getExtras();
                i.putExtra("parameters", (Serializable) parameters); //geting NullPointerException here
                startActivityForResult(i, 0);
                Log.e("ssh", "Here got4");
                /*if(isBound){
                    Log.e("ssh", "get here 5");
                    sshService.setSessionData(etUsername.getText().toString(), etPassword.getText().toString(), etIp.getText().toString());
                    sshService.setSessionThread(sessionThread);

                    Log.e("ssh", "get here 6");

                    sshService.runCommand("will this work");
                }
                */


//                sessionController.runCommand("echo lindo");
//
//                AsyncTask<String, String, String> newOne;
//                Intent i;
//
//                i = new Intent(MainActivity.this, Commands.class);
//                i.putExtra("SessionController", sessionController);
//                //Session session = sessionController.getSession();
//
//
//
//                startActivity(i);
                /*SessionController Session;
                Session = new SessionController("mathilda", "foobar", "192.168.1.198");
                Session.initSession();*/
                //sessionController.runCommand("echo Ranulfo");
                //sessionController.disconnect();
                //sessionController.runCommand("touch worked21");

                /*//ShellAsyncTask.setCommand(commandEditText.getText().toString());
                newOne = new ShellAsyncTask();
                newOne.execute(commandEditText.getText().toString(), "str", "str");

                //Activity Commands = new Activity();
                AsyncTask<String, String, String> secondOne;
                secondOne = new ChannelThread();
                secondOne.execute("touch ranulfofile11");
                AsyncTask<String, String, String> secondtwo;
                secondtwo = new ShellAsyncTask();
                secondtwo.execute("touch ranulfofile21");*/
                //
            }
        });
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been established

            sshService = ((SSHService.LocalBinder)service).getServerInstance();
            Log.e("ssh", "service connected?");
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            sshService = null;
            Log.e("ssh", "service unconnected?");

        }
    };

}
