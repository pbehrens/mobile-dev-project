package com.example.remotelight;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {
    EditText commandEditText;
    TextView resultTextView;
    Button sendCommandButton;
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
        commandEditText = (EditText) findViewById(R.id.commandEditText);
        resultTextView  = (TextView) findViewById(R.id.terminalTextView);
        sendCommandButton = (Button) findViewById(R.id.sendCommandButton);
    }

    public void initializeSessionController(){
        SessionController sessionController = new SessionController("mathilda", "foobar", "192.168.1.198");
        sessionController.initSession();
        sessionController.runCommand("ps ax | tail");

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
                AsyncTask<String, String, String> newOne;
                //ShellAsyncTask.setCommand(commandEditText.getText().toString());
                newOne = new ShellAsyncTask();
                newOne.execute(commandEditText.getText().toString(), "str", "str");
                try {
                    String result = newOne.get();
                    Log.e("ssh", result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
}
