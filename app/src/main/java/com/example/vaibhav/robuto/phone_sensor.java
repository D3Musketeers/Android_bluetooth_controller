package com.example.vaibhav.robuto;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import static android.R.id.list;

public class phone_sensor extends AppCompatActivity implements SensorEventListener {
    ListView all_phone_sensors;
    ListView sensor_name_list;
    ListView sensor_values;
    TextView acc_value;
    TextView gyro_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sensor);
        sensor_name_list = (ListView)findViewById(R.id.sensorlistView);
        acc_value = (TextView) findViewById(R.id.acc_value);
        gyro_value = (TextView) findViewById(R.id.gyro_value);

        SensorManager sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        //Sensor proximity_sensor = sMgr.getDefaultSensor(Sensor.);
        List<Sensor> sensor_list = sMgr.getSensorList(Sensor.TYPE_ALL);

        List<String> sn = new ArrayList<>();
        for (Sensor sensor_name: sensor_list) {
            sn.add(sensor_name.getName());
        }
        ArrayAdapter<String> sensorNames = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,sn);
        sensor_name_list.setAdapter(sensorNames);


    }

    public void onAccuracyChanged(Sensor sensor, int Accuracy) {

    }

    public void onSensorChanged (SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float accelerometer_value_sensor = event.values[0];
            acc_value.setText(Float.toHexString(accelerometer_value_sensor));

        } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float gyroscope_value_sensor = event.values[0];
            gyro_value.setText(Float.toHexString(gyroscope_value_sensor));
        }




      //  sensor_values = (ListView)findViewById(R.id.sensorvaluelistView);
        //ArrayAdapter<Float> sensor_values = new ArrayAdapter<Float>(getApplicationContext(),android.R.layout.simple_list_item_1,value_sensor);
    }





}

