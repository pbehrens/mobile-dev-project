package edu.luc.plutarcobehrens.remotelight;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.remotelight.R;
import com.example.remotelight.SSHService;

import java.io.Serializable;

/**
 COPYRIGHT (C) <2013> <plutarco>. All Rights Reserved.
 Main Activity that allows for useres to specify a username pass word and host to connect to
 @author <rplutarco>
 @version <1.0> <date:2013-12-9>
 */
public class MainActivity extends Activity{

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setViewVariables(){

        //set variables used for manipulating the view
        etIp = (EditText) findViewById(R.id.etIp);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        resultTextView  = (TextView) findViewById(R.id.terminalTextView);
        sendCommandButton = (Button) findViewById(R.id.sendCommandButton);

        //auto set text to the debugging settings
        etIp.setText("192.168.1.179");
        etUsername.setText("pcduino");
        etPassword.setText("honig08");
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
                parameters = new Parameters(etUsername.getText().toString(),etPassword.getText().toString(),etIp.getText().toString());
                Intent i = new Intent(MainActivity.this, Commands.class);
                //Bundle b = i.getExtras();
                i.putExtra("parameters", (Serializable) parameters); //geting NullPointerException here
                startActivityForResult(i, 0);
            }
        });
    }
}
