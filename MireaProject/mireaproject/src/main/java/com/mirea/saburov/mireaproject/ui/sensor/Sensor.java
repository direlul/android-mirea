package com.mirea.saburov.mireaproject.ui.sensor;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirea.saburov.mireaproject.R;

import org.jetbrains.annotations.NotNull;

public class Sensor extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private android.hardware.Sensor accelerometerSensor;
    private android.hardware.Sensor lightSensor;
    private android.hardware.Sensor gyroscopeSensor;

    private TextView azimuthTextView;
    private TextView pitchTextView;
    private TextView rollTextView;
    private TextView xView;
    private TextView yView;
    private TextView zView;

    private TextView lightView;

    public Sensor() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager =
                (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        accelerometerSensor = sensorManager
                .getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager
                .getDefaultSensor(android.hardware.Sensor.TYPE_LIGHT);
        gyroscopeSensor = sensorManager
                .getDefaultSensor(android.hardware.Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        azimuthTextView = view.findViewById(R.id.azimuthTextView);
        pitchTextView = view.findViewById(R.id.pitchTextView);
        rollTextView = view.findViewById(R.id.rollTextView);
        lightView = view.findViewById(R.id.lightView);
        xView = view.findViewById(R.id.xView);
        yView = view.findViewById(R.id.yView);
        zView = view.findViewById(R.id.zView);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == android.hardware.Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            azimuthTextView.setText("Azimuth: " + valueAzimuth);
            pitchTextView.setText("Pitch: " + valuePitch);
            rollTextView.setText("Roll: " + valueRoll);
        }

        if (event.sensor.getType() == android.hardware.Sensor.TYPE_LIGHT) {
            float light = event.values[0];
            lightView.setText("Light: " + light);
        }

        if (event.sensor.getType() == android.hardware.Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            xView.setText("X: " + x);
            yView.setText("Y: " + y);
            zView.setText("Z: " + z);
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }
}