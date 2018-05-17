package com.sayan.test.syncdbtest.databaseclasses.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sayan.test.syncdbtest.databaseclasses.tables.TestModel;
import com.sayan.test.syncdbtest.databaseclasses.tables.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TestModelDao {

    @Query("SELECT * FROM test")
    List<TestModel> getAll();

    @Query("SELECT * FROM test where sync_status LIKE :syncStatus")
    ArrayList<TestModel> findBySyncStatus(String syncStatus);

    @Query("SELECT COUNT(*) from test")
    int countTests();

    @Insert
    void insertAll(TestModel... tests);

    @Delete
    void delete(TestModel test);

    @Query("UPDATE test SET sync_status = :syncStatus WHERE sync_status = :oldSyncStatus")
    void updateSyncStatus(String syncStatus, String oldSyncStatus);
}
