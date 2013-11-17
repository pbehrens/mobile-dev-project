package com.example.remotelight;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Ranulfo on 11/3/13.
 */
public class Commands extends Activity {
    Button back;
    Button select;
    SessionController sessionOpen;
    Drawable Bulp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.command_options);
        back = (Button) findViewById(R.id.button);
        select = (Button) findViewById(R.id.button2);

        Intent i = getIntent();
        sessionOpen = (SessionController) i.getParcelableExtra("SessionController");


    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonListeners();

    }

    private void buttonListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionOpen.runCommand("echo lindo");
                Log.e("ssh","Get");
                sessionOpen.disconnect();
                finish();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(Commands.this, RunningCommand.class);
                startActivity(i);
            }
        });

    }


}
