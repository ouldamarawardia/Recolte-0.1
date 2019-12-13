package com.example.myapplication;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    long insertRacolt(Recolt donnees);


    @Query("SELECT * FROM " + MyDatabase.Racolt)
    List<Recolt> fetchAllTodos();


    @Delete
    int deleteRacolt(Recolt donnees);
}
