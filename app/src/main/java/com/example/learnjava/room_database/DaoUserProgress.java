package com.example.learnjava.room_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoUserProgress {

    @Insert
    void insertModelUserProgress(ModelUserProgress user);

    @Update
    void updateUserProgress(ModelUserProgress userProgress);

    @Delete
    void deleteUserProgress(ModelUserProgress user);

    @Query("DELETE FROM ModelUserProgress")
    void deleteTable();

    @Query("SELECT * FROM ModelUserProgress ORDER BY user_ID ASC")
    List<ModelUserProgress> getAllModelUserPRogress();

//    @Query("SELECT * FROM ModelUserProgress ORDER BY userId ASC")
//    LiveData<List<ModelUserProgress>> getAllModelUserProgress();

    @Query("SELECT * FROM ModelUserProgress WHERE user_ID=:userId LIMIT 1")
    ModelUserProgress getModelUserProgress(String userId);

//    @Query("SELECT * FROM ModelUserProgress WHERE userId=:userId LIMIT 1")
//    LiveData<ModelUserProgress> getModelUserProgress(String userId);

}
