package com.example.remotelight;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    EditText ip;
    EditText user;
    EditText password;
    TextView resultTextView;
    Button sendCommandButton;
    SessionController sessionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewVariables();
        setClickListeners();
        initializeSessionController();
        Log.e("ssh", "does it get before the async task");

    }

    public void setViewVariables(){
        ip = (EditText) findViewById(R.id.etIp);
        resultTextView  = (TextView) findViewById(R.id.terminalTextView);
        sendCommandButton = (Button) findViewById(R.id.sendCommandButton);
    }

    public void initializeSessionController(){
        sessionController = new SessionController("mathilda", "foobar", "192.168.1.198");
        sessionController.initSession();
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
                ip = (EditText) findViewById(R.id.etIp);
                user = (EditText) findViewById(R.id.etUsername);
                password = (EditText) findViewById(R.id.etPassword);

                AsyncTask<String, String, String> newOne;
                Intent i;
                i = new Intent(MainActivity.this, Commands.class);
                //Session session = sessionController.getSession();



                startActivity(i);
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

    
}
