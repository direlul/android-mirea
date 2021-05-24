package com.mirea.saburov.mireaproject.ui.story;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mirea.saburov.mireaproject.R;
import com.mirea.saburov.mireaproject.entity.Story;

import java.util.List;

public class StoryAdapter extends
        RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private List<Story> stories;

    public StoryAdapter(List<Story> stories) {
        this.stories = stories;
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_story, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder holder, int position) {
        Story story = stories.get(position);


        TextView textView = holder.nameTextView;
        textView.setText(story.content);
    }


    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
        }
    }
}