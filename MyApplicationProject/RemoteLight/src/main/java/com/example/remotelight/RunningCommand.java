package com.example.remotelight;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Ranulfo on 11/11/13.
 */
public class RunningCommand extends Activity {
    ImageButton imgButton;
    SessionController sessionController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgButton = (ImageButton) findViewById(R.id.imageButton);
        TestLamp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TurnOn();
    }

    private void TurnOn() {
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionController.runCommand("python turn_on_led.py");
                TestLamp();
            }
        });
    }

    private void TestLamp() {
        String result = sessionController.runCommand("python testlamp.py");
        if(result.equals("On")){
            imgButton.setBackgroundColor(0);
        }
        else{
            imgButton.setBackgroundColor(1);
        }
    }

}
