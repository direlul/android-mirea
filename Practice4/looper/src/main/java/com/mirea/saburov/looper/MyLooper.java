package com.mirea.saburov.looper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    private int number = 0;
    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    public void run(){
        Log.d("MyLooper", "run");
        Looper.prepare();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.d("MyLooper", number + " Age:"+ msg.getData().getString("Age"));
                Log.d("MyLooper", number + " Job:"+ msg.getData().getString("Job"));
                number++;
            }
        };
        Looper.loop();
    }
}
