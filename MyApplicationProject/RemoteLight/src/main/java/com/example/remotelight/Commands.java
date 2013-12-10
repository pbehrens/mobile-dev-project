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
 COPYRIGHT (C) <2013> <plutarco>. All Rights Reserved.
 Controls the SSH session thread that is initialized within and allows for a continuous connection
 @author <rplutarco>
 @version <1.0> <date:2013-12-9>
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
    public boolean init;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.command_options);
        Log.e("ssh", "I GOOOOT");
        initilizeButtons();
        initializeSession();

    }

    private void initializeSession() {
        sessionController = new SessionController(parameters.getUser(),parameters.getPassword(),parameters.getHost()
                ,getApplicationContext());
        sessionController.initSession();
    }

    private void initilizeButtons(){
        temperature = (TextView) findViewById(R.id.textTemperature);
        back = (Button) findViewById(R.id.button);
        select = (Button) findViewById(R.id.button2);
        bulb = (ImageButton) findViewById(R.id.bulbButton);

        Bundle b = getIntent().getExtras();
        parameters = (Parameters) b.getSerializable("parameters"); //geting NullPointerException here

        bulbOff = R.drawable.bulboff;
        bulbOn = R.drawable.onlight;

        bulb.setTag("Off");
        bulb.setImageResource(bulbOff);

    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonListeners();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sessionController.disconnect();
    }

    public void updateTemperature(String temp){
        temperature.setText(temp);

    }

    private void buttonListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionController.disconnect();
                finish();
            }
        });


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionController.isInitialized()){
                    String[] result;
                    result = sessionController.runCommand("python testLight.py").split("/");
                    temperature.setText("Temp ="+result[1]+"C");
                    if(result[0].equals("On")){
                        bulb.setImageResource(bulbOn);
                    }
                    else{
                        bulb.setImageResource(bulbOff);
                    }

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
                if(sessionController.isInitialized()){
                    if(bulb.getTag().equals("Off")){
                        bulb.setImageResource(bulbOn);
                        bulb.setTag("On");
                    }
                    else{
                        bulb.setImageResource(bulbOff);
                        bulb.setTag("Off");
                    }
                    sessionController.runCommand("python turn_on_led.py");
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Wait a Little bit", Toast.LENGTH_LONG).show();
                }

            }
        });


    }


}
