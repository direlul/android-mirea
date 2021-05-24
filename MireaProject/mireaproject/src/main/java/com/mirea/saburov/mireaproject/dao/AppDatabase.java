package com.mirea.saburov.mireaproject.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mirea.saburov.mireaproject.entity.Story;

@Database(entities = {Story.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StoryDao storyDao();
}
