package com.example.vaibhav.robuto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.About:
            Toast.makeText(getApplicationContext(),"About clicked",Toast.LENGTH_SHORT).show();
        return true;
        case R.id.Search_menu:
            Toast.makeText(getApplicationContext(), "Search_menu clicked", Toast.LENGTH_SHORT).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
    }

    public void bluetooth_control (View view) {
        //Below code is to start new activity (new screen)//////
        Intent intent = new Intent(this,bluetooth_control.class);
        startActivity(intent);
        /////////////////////////////////////////////////////

    }

    public void phone_sensor (View view) {
        //Below code is to start new activity (new screen)//////
        Intent intent = new Intent(this,phone_sensor.class);
        startActivity(intent);
        /////////////////////////////////////////////////////

    }

    public void joystick (View view) {
        //Below code is to start new activity (new screen)//////
        Intent intent = new Intent(this,joystick.class);
        startActivity(intent);
        /////////////////////////////////////////////////////

    }
}
