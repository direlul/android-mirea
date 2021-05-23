package com.mirea.saburov.mireaproject.ui.music;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mirea.saburov.mireaproject.MainActivity;
import com.mirea.saburov.mireaproject.R;
import com.mirea.saburov.mireaproject.service.MusicService;


public class MusicFragment extends Fragment implements View.OnClickListener {

    public Button play, stop;

    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        play = getActivity().findViewById(R.id.play);
        play.setOnClickListener(this);
        stop = getActivity().findViewById(R.id.stop);
        stop.setOnClickListener(this);
    }

    public void onClick(final View v) { //check for what button is pressed
        switch (v.getId()) {
            case R.id.play:
                getActivity().startService(
                        new Intent(getActivity(), MusicService.class));
                break;
            case R.id.stop:
                getActivity().stopService(
                        new Intent(getActivity(), MusicService.class));
                break;
        }
    }
}