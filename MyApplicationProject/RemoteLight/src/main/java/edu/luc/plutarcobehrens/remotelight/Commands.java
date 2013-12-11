package edu.luc.plutarcobehrens.remotelight;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remotelight.R;


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.command_options);
        initilizeButtons();
        initializeSession();

    }

    /**
     * Initalize the SSH session controller and initialize the SSH session inside of the controller
     */
    private void initializeSession() {
        sessionController = new SessionController(parameters.getUser(),parameters.getPassword(),parameters.getHost()
                ,getApplicationContext());

        sessionController.initSession();
    }

    /**
     * Initilialize the buttons for the activity
     */
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

    /**
     * If the activity crashes or goes to sleep reset the button listeners
     */
    @Override
    protected void onResume() {
        super.onResume();
        buttonListeners();

    }

    /**
     * Disconnect the sessioncontroller on destroy so a bunch of open SSH sessions dont happen
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sessionController.disconnect();
    }

    /**
     * Update the temperature class variable
     * @param temp
     */
    public void updateTemperature(String temp){
        temperature.setText(temp);
    }

    /**
     * Set the listener for the back button and refresh button
     */
    private void buttonListeners() {
        //if this button is clicked the session will disconnect and the main activty willl be started
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionController.disconnect();
                finish();
            }
        });

        // set listener for the refresh button
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if session controller is initiolized and then send command to get updated info
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

        //Send command for bulb turning on or off and update the image accordingly
        bulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check for ssh session initialization and update bulb image
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
