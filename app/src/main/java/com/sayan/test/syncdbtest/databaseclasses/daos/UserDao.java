package com.sayan.test.syncdbtest.databaseclasses.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.sayan.test.syncdbtest.databaseclasses.tables.UserDBModel;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<UserDBModel> getAll();

    @Query("SELECT * FROM user where first_name LIKE  :firstName AND last_name LIKE :lastName")
    UserDBModel findByName(String firstName, String lastName);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(UserDBModel... userDBModels);

    @Delete
    void delete(UserDBModel userDBModel);

    @Query("UPDATE user SET first_name = :firstName WHERE first_name = :oldFirstName")
    void updateFirstName(String firstName, String oldFirstName);
}
