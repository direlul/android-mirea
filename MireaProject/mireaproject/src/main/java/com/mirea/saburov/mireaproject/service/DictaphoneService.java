package com.mirea.saburov.mireaproject.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.mirea.saburov.mireaproject.R;

import java.io.IOException;

public class DictaphoneService extends Service {
    private MediaPlayer mediaPlayer;

    public DictaphoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        mediaPlayer.setLooping(false);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        try {
            mediaPlayer.setDataSource(this, Uri.parse(intent.getDataString()));
            mediaPlayer.prepare();
            mediaPlayer.start();

            while (mediaPlayer.isPlaying()) {}
            mediaPlayer.stop();
            mediaPlayer.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }




    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }
}