package com.example.instajewelry.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface JewelryDao {
    @Query("select * from Jewelry")
    LiveData<List<Jewelry>> getAll();

    @Query("select * from Jewelry")
    List<Jewelry> getAllList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Jewelry... jewelries);

    @Delete
    void deleteJewelry(Jewelry... jewelries);

}