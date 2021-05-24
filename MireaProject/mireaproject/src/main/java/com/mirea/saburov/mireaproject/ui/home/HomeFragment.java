package com.mirea.saburov.mireaproject.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mirea.saburov.mireaproject.R;
import com.mirea.saburov.mireaproject.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private SharedPreferences preferences;
    private TextView hello;
    private String name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        hello = view.findViewById(R.id.text_home);
        name = preferences.getString("guest_name", "Guest");
        hello.setText("Hello, " + name);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        hello.setText("Hello, " + name);
        super.onStart();
    }

    @Override
    public void onResume() {
        hello.setText("Hello, " + name);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}