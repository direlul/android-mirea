package com.mirea.saburov.laboratory2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import android.os.Bundle;
import android.util.Log;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        server = new Server();
        getLifecycle().addObserver(server);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void stateUpdated() {
        System.out.println("Состояние изменено");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void stateCreated() {
        System.out.println("Создание");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void statePaused() {
        System.out.println("Пауза");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void stateUpdate() {
        System.out.println("Уничтожение");
    }

    public class Server implements LifecycleObserver {
        private String TAG = "lifecycle";
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void connect() {
            Log.d(TAG,"connect to web-server");
        }
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void disconnect() {
            Log.d(TAG,"disconnect");
        }
    }
}