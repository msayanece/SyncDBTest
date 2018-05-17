package com.sayan.test.syncdbtest.databaseclasses;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sayan.test.syncdbtest.databaseclasses.daos.TestModelDao;
import com.sayan.test.syncdbtest.databaseclasses.daos.UserDao;
import com.sayan.test.syncdbtest.databaseclasses.tables.TestModel;
import com.sayan.test.syncdbtest.databaseclasses.tables.User;


@Database(entities = {User.class, TestModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract TestModelDao testModelDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
//                            .allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
