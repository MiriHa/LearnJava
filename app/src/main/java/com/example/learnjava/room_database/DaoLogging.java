package com.example.learnjava.room_database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.learnjava.models.ModelUserProgress;

import java.util.List;

@Dao
public interface DaoLogging {

    @Insert
    void insertLog(Logging log);

    @Insert
    void insertMultipleLog(List<Logging> logs);

    //?????
    @Insert
    void insertUserWithLogs(ModelUserProgress user, List<Logging> logs);

    @Update
    void updateLog(Logging log);


    @Delete
    void deleteLog(Logging log);

    @Query("DELETE FROM Logging")
    void deleteTable();


     @Query("SELECT * FROM Logging WHERE userOwnerId=:userId")
     List<Logging> findLogsForUser(final int userId);


    @Transaction
    @Query("SELECT * FROM ModelUserProgress")
    List<UserWithLog> getUsersWithLogs();


}
