package com.mirea.saburov.mireaproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mirea.saburov.mireaproject.entity.Story;

import java.util.List;

@Dao
public interface StoryDao {
    @Query("SELECT * FROM story")
    List<Story> getAll();
    @Query("SELECT * FROM story WHERE id = :id")
    Story getById(long id);
    @Insert
    void insert(Story employee);
    @Update
    void update(Story employee);
    @Delete
    void delete(Story employee);
}
