package com.mirea.saburov.practice3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Intent intent = getIntent();
        String message = intent.getStringExtra("time");

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }

}
