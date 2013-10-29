package com.example.remotelight;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MainActivity extends Activity {
    EditText commandEditText;
    TextView resultTextView;
    Button sendCommandButton;
    Session session;
    ByteArrayOutputStream bitArrayOutputStream;
    ByteArrayInputStream bitArrayInputStream;
    Channel channel;
    JSch jsch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commandEditText = (EditText) findViewById(R.id.commandEditText);
        resultTextView  = (TextView) findViewById(R.id.terminalTextView);
        sendCommandButton = (Button) findViewById(R.id.sendCommandButton);
        setClickListeners();
        Log.e("ssh", "does it get before the async task");

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
                new ShellAsyncTask().execute("str", "str", "str");

//                String username = "wluw";
//                String password = "b3rcsdfg";
//                String host     = "wluw.org"; // sample ip address
//                if(commandEditText.getText().toString() != ""){
//                    jsch = new JSch();
//                    new ShellAsyncTask().execute();
//
//                    try {
//                        session = jsch.getSession(username, host, 22);
//                        session.setPassword(password);
//                        Toast.makeText(getApplicationContext(), commandEditText.getText(), Toast.LENGTH_LONG).show();
//
//                        Properties properties = new Properties();
//                        properties.put("StrictHostKeyChecking", "no");
//                        session.setConfig(properties);
//                        session.connect(30000);
//
//                        channel = session.openChannel("shell");
//                        channel.setInputStream(bitArrayInputStream);
//                        channel.setOutputStream(bitArrayOutputStream);
//                        channel.connect();
//
//                    } catch (JSchException e) {
//                        // TODO Auto-generated catch block
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Command cannot be empty !", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

    public void onCommandEditText(View v){

    }

    public void on_ResultTextView(View v){


    }
    
}
