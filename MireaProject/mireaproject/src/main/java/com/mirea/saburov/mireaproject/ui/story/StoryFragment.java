package com.mirea.saburov.mireaproject.ui.story;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mirea.saburov.mireaproject.App;
import com.mirea.saburov.mireaproject.R;
import com.mirea.saburov.mireaproject.dao.AppDatabase;
import com.mirea.saburov.mireaproject.dao.StoryDao;
import com.mirea.saburov.mireaproject.entity.Story;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StoryFragment extends Fragment implements View.OnClickListener  {

    private AppDatabase db;
    private List<Story> stories;
    private StoryDao storyDao;
    private StoryAdapter adapter;

    public StoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        RecyclerView rvStories = (RecyclerView) view.findViewById(R.id.stories);
        Button addStoryBtn = view.findViewById(R.id.addStoryBtn);
        addStoryBtn.setOnClickListener(this);

        db = App.getInstance().getDatabase();
        storyDao = db.storyDao();

        stories = storyDao.getAll();
        // Create adapter passing in the sample user data
        adapter = new StoryAdapter(stories);
        // Attach the adapter to the recyclerview to populate items
        rvStories.setAdapter(adapter);
        // Set layout manager to position the items
        rvStories.setLayoutManager(new LinearLayoutManager(getContext()));

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story, container, false);
    }

    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.addStoryBtn:
                addStory();
                break;
        }
    }

    public void addStory() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.promt, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
        mDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_story);

        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String storyText = userInput.getText().toString();
                                if (!storyText.isEmpty()) {
                                    Story newStory = new Story(storyText);
                                    storyDao.insert(newStory);
                                    stories.add(newStory);
                                    adapter.notifyItemInserted(stories.size());
                                } else {
                                    Toast.makeText(getActivity(), "Empty story doesn't allow", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                });

        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();


    }
}