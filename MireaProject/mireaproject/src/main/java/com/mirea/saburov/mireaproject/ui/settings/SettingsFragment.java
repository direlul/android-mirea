package com.mirea.saburov.mireaproject.ui.settings;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.ViewUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.mirea.saburov.mireaproject.MainActivity;
import com.mirea.saburov.mireaproject.R;

import org.jetbrains.annotations.NotNull;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private SharedPreferences preferences;
    private EditText guestName;
    private Button saveBtn;
    private CheckBox checkBox;

    private NotificationManager notificationManager;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        guestName = view.findViewById(R.id.guestName);
        saveBtn = view.findViewById(R.id.saveBtn);
        checkBox = view.findViewById(R.id.checkBox);
        saveBtn.setOnClickListener(this);
        notificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.color_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        guestName.setText(preferences.getString("guest_name", "Guest"));

        if ("disable".equals(preferences.getString("sound", "enabled"))) {
            checkBox.setChecked(true);
        }

        String myString = preferences.getString("color_bar", "Red"); //the value you want the position for

        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(myString);
        spinner.setSelection(spinnerPosition);

        String sound = preferences.getString("sound", "enabled");

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String color = parent.getSelectedItem().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("color_bar", color);
        editor.apply();

        MainActivity.setColorBar(color);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                SharedPreferences.Editor editor = preferences.edit();
                boolean checked = ((CheckBox) checkBox).isChecked();
                boolean permission = checkPermissionOnSound();
                if (permission) {
                    if (checked) {
                        MainActivity.disableSound(getContext());
                        editor.putString("sound", "disable");
                        Toast.makeText(getContext(), "Sound disabled", Toast.LENGTH_SHORT).show();
                    } else {
                        MainActivity.enableSound(getContext());
                        editor.putString("sound", "enable");
                        Toast.makeText(getContext(), "Sound enabled", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Need access to song permission", Toast.LENGTH_SHORT).show();
                }

                editor.putString("guest_name", guestName.getText().toString());
                editor.apply();
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkPermissionOnSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        return notificationManager.isNotificationPolicyAccessGranted();
    }

}