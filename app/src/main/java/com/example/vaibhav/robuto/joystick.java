package com.example.vaibhav.robuto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class joystick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);
    }

    public void Buttons (View view) {
        //Below code is to start new activity (new screen)//////
        Intent intent = new Intent(this,Buttons.class);
        startActivity(intent);
        /////////////////////////////////////////////////////
    }

    public void Joypad (View view) {
        Intent intent = new Intent(this,Joypad.class);
        startActivity(intent);

    }

    public void Sensor (View view) {
        Intent intent = new Intent(this,Sensor.class);
        startActivity(intent);

    }
}
