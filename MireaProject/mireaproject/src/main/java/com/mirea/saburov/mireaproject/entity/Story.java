package com.mirea.saburov.mireaproject.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Story {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String content;

    public Story(String content) {
        this.content = content;
    }
}
