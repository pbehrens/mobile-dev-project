package com.example.remotelight;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ranulfo on 11/3/13.
 */
public class Commands extends Activity {
    Button back;
    Button select;
    ImageButton bulb;
    SessionController sessionController;
    Drawable Bulp;
    Parameters parameters;
    int bulbOff;
    int bulbOn;
    TextView temperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.command_options);
        Log.e("ssh", "I GOOOOT");
        initilizeButtons();
        initializeSession();


        //sessionOpen = (SessionController) i.getParcelableExtra("SessionController");


    }

    private void initializeSession() {
        sessionController = new SessionController(parameters.getUser(),parameters.getPassword(),parameters.getHost(),getApplicationContext());
        sessionController.initSession();
    }

    private void initilizeButtons(){
        temperature = (TextView) findViewById(R.id.textTemperature);
        back = (Button) findViewById(R.id.button);
        select = (Button) findViewById(R.id.button2);
        bulb = (ImageButton) findViewById(R.id.bulbButton);
        Log.e("ssh", "I GOOOOT1");
        Bundle b = getIntent().getExtras();
        Log.e("ssh", "I GOOOOT2");
        parameters = (Parameters) b.getSerializable("parameters"); //geting NullPointerException here
        Log.e("ssh", "I GOOOOT"+parameters.getPassword());
        bulbOff = R.drawable.bulboff;
        bulbOn = R.drawable.onlight;
        bulb.setTag("Off");
        bulb.setImageResource(bulbOff);
        //temperature.setText("TEMPERATURA");
    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonListeners();

    }

    public void updateTemperature(String temp){
        temperature.setText(temp);

    }

    private void buttonListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sessionOpen.runCommand("echo lindo");
                Log.e("ssh","Get");
                sessionController.disconnect();
                finish();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionController.isInitialized()){
                    sessionController.runCommand("python testLight.py");
                //Intent i;
                //i = new Intent(Commands.this, RunningCommand.class);
                //startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Wait a Little bit", Toast.LENGTH_LONG).show();
                }
            }
        });
        bulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bulb.getTag().equals("Off")){
                    bulb.setImageResource(bulbOn);
                    bulb.setTag("On");
                }
                else{
                    bulb.setImageResource(bulbOff);
                    bulb.setTag("Off");
                }
                sessionController.runCommand("python turn_on_led.py");
                //Intent i;
                //i = new Intent(Commands.this, RunningCommand.class);
                //startActivity(i);
            }
        });


    }


}
