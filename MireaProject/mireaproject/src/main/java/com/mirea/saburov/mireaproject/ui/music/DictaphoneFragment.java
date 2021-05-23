package com.mirea.saburov.mireaproject.ui.music;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mirea.saburov.mireaproject.MainActivity;
import com.mirea.saburov.mireaproject.R;
import com.mirea.saburov.mireaproject.service.DictaphoneService;
import com.mirea.saburov.mireaproject.service.MusicService;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;


public class DictaphoneFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button startRecordButton;
    private Button stopRecordButton;
    private Button playRecordButton;
    private MediaRecorder mediaRecorder;
    private File audioFile;
    private boolean isWork;

    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    public DictaphoneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isWork = MainActivity.hasPermissions(getActivity(), PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS,
                    REQUEST_CODE_PERMISSION);
        }

        mediaRecorder = new MediaRecorder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dictaphone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        startRecordButton = view.findViewById(R.id.btnStart);
        stopRecordButton = view.findViewById(R.id.btnStop);
        playRecordButton = view.findViewById(R.id.playBtn);

        startRecordButton.setOnClickListener(this);
        stopRecordButton.setOnClickListener(this);
        playRecordButton.setOnClickListener(this);


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            // permission granted
            isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                    onRecordStart(v);
                break;
            case R.id.btnStop:
                    onStopRecord(v);
                break;
            case R.id.playBtn:
                    if (audioFile != null) {

                        Uri uri = Uri.fromFile(audioFile);
                        Intent intent = new Intent(getActivity(), DictaphoneService.class);
                        intent.setData(uri);
                        getActivity().startService(intent);
                    } else {
                        Toast.makeText(getActivity(), "Need to start record", Toast.LENGTH_SHORT).show();
                    }


                break;
        }
    }

    public void onRecordStart(View view) {
        try {
            startRecordButton.setEnabled(false);
            stopRecordButton.setEnabled(true);
            stopRecordButton.requestFocus();
            startRecording();
        } catch (Exception e) {
            Log.e(TAG, "Caught io exception " + e.getMessage());
        }
    }
    // нажатие на копку стоп
    public void onStopRecord(View view) {
        startRecordButton.setEnabled(true);
        stopRecordButton.setEnabled(false);
        startRecordButton.requestFocus();

        stopRecording();
        processAudioFile();
    }

    private void startRecording() throws IOException, IOException {
        // проверка доступности sd - карты
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "sd-card success");
            // выбор источника звука
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // выбор формата данных
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // выбор кодека
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null) {
                // создание файла
                audioFile = new File(getActivity().getExternalFilesDir(
                        Environment.DIRECTORY_MUSIC), "mirea.3gp");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(getActivity(), "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();

            Toast.makeText(getActivity(), "You are not recording right now!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        // установка meta данных созданному файлу
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = getActivity().getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.canRead());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);
        // оповещение системы о новом файле
        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }
}