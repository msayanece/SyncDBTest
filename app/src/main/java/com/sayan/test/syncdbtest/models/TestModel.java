package com.sayan.test.syncdbtest.models;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

/**
 * Created by Admin on 12-01-2018.
 */

@Table(name = "TestModel")
public class TestModel extends Model {

    @Column(name = "name")
    public String name;

    @Column(name = "sync_status")
    public String syncStatus;

    public TestModel() {
        // You have to call super in each constructor to create the table.
        super();
    }

    public TestModel(String name, String syncStatus) {
        // You have to call super in each constructor to create the table.
        super();
        this.name = name;
        this.syncStatus = syncStatus;
    }

    public static List<TestModel> getAllTestModels() {
        return new Select().from(TestModel.class).execute();
    }

    public static TestModel getStudentBySyncStatus(String syncStatus) {
        return new Select().from(TestModel.class).where("sync_status = ?", syncStatus).executeSingle();
    }

    public static void updateSyncStatusBySyncStatus(String syncStatus, String newSyncStatus){
        new Update(TestModel.class).set("sync_status = ?", newSyncStatus).where("sync_status = ?", syncStatus).execute();
    }

    public static void deleteAllRows(){
        ActiveAndroid.execSQL("DELETE FROM TestModel;");
    }

    public static void deleteRowsBySyncStatus(String syncStatus){
        new Delete().from(TestModel.class).where("sync_status = ?", syncStatus).execute();
    }
}
