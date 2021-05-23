package com.mirea.saburov.practice4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Текущий поток: " + mainThread.getName());

        mainThread.setName("MireaThread");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());
    }

    public void onClick(View view) {
        new Thread(() -> {
            int numberThread = counter++;
            Log.i("ThreadProject", "Запущен поток № " + numberThread);
            long endTime = System.currentTimeMillis()
                    + 20 * 1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime -
                                System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            Log.i("ThreadProject", "Выполнен поток № " + numberThread);
        }).start();
    }
}