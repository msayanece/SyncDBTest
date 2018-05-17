package com.sayan.test.syncdbtest.databaseclasses.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sayan.test.syncdbtest.databaseclasses.tables.TestDBModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TestModelDao {

    @Query("SELECT * FROM test")
    List<TestDBModel> getAll();

    @Query("SELECT * FROM test where sync_status LIKE :syncStatus")
    TestDBModel[] findBySyncStatus(String syncStatus);

    @Query("SELECT COUNT(*) from test")
    int countTests();

    @Insert
    void insertAll(TestDBModel... tests);

    @Delete
    void delete(TestDBModel test);


    @Query("DELETE FROM test")
    public void deleteAll();

    @Query("UPDATE test SET sync_status = :syncStatus WHERE sync_status = :oldSyncStatus")
    void updateSyncStatus(String syncStatus, String oldSyncStatus);

    @Query("UPDATE test SET sync_status = :syncStatus WHERE id = :id")
    void updateSyncStatus(String syncStatus, int id);
}
