package com.example.learnjava.room_database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * One to Many relationships: One User has many Logs
 *
 */
//TODO is this needed? reich foreign key aus?
public class UserWithLog {

    @Embedded public ModelUserProgress user;
    @Relation(parentColumn = "user_ID", entityColumn = "User_Owner_ID")

    public List<Logging> logging;
}
