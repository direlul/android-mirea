package com.mirea.saburov.loadermanager;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class MyLoader extends AsyncTaskLoader<String> {
    private String word;

    public static final String ARG_SHUFFLE_WORD = "shuffle";

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null)
            word = args.getString(ARG_SHUFFLE_WORD);
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {

        return shuffle(word);
    }

    public String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();

        for(char c:input.toCharArray()){
            characters.add(c);
        }

        StringBuilder output = new StringBuilder(input.length());

        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }

        return output.toString();
    }
}
