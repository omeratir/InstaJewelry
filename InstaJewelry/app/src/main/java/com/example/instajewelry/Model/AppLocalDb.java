package com.example.instajewelry.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.instajewelry.MyApplication;

@Database(entities = {Jewelry.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract JewelryDao jewelryDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "123456.db")
                    .fallbackToDestructiveMigration()
                    .build();
}