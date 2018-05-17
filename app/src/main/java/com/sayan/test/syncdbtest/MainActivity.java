package com.sayan.test.syncdbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sayan.test.syncdbtest.databaseclasses.AppDatabase;
import com.sayan.test.syncdbtest.databaseclasses.syncdbtestsdk.InterceptorHTTPClientCreator;
import com.sayan.test.syncdbtest.databaseclasses.syncdbtestsdk.Service;
import com.sayan.test.syncdbtest.databaseclasses.syncdbtestsdk.SyncDBTestSdk;
import com.sayan.test.syncdbtest.databaseclasses.syncdbtestsdk.TestResponse;
import com.sayan.test.syncdbtest.databaseclasses.tables.TestModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getAppDatabase(this);
    }

    public void onClickFetchData(View view) {
        getAllTestModels(appDatabase);
    }

    public void onClickSaveData(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        final String enteredName = editText.getText().toString();

        InterceptorHTTPClientCreator.createInterceptorHTTPClient(getApplicationContext());
        Service service = new SyncDBTestSdk.Builder().build(getApplicationContext()).getService();
        service.saveNameToServer(enteredName).enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            if (response.body().getSync().equalsIgnoreCase("Yes")) {
                                populateWithTestData(appDatabase, enteredName, "Y");
                            } else {
                                populateWithTestData(appDatabase, enteredName, "N");
                            }
                        } else {
                            populateWithTestData(appDatabase, enteredName, "N");
                        }
                    } else {
                        populateWithTestData(appDatabase, enteredName, "N");
                    }
                } else {
                    populateWithTestData(appDatabase, enteredName, "N");
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                populateWithTestData(appDatabase, enteredName, "N");
            }
        });
    }

    public void updateStatus(View view) {
        getAllTestModelsBySyncStatus(appDatabase, "N", new QueryCallBack<TestModel>() {
            @Override
            public void onSuccess(ArrayList<TestModel> models) {

            }
        });

        updateTestModel(appDatabase, "Y", "N");
    }

    //region User
    ////////////////***********user***********///////////////
    void getAllUsers(final AppDatabase appDatabase) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().getAll();
            }
        }).start();
    }

    void getUserByName(final AppDatabase appDatabase, final String firstName, final String lastName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().findByName(firstName, lastName).getAge();
            }
        }).start();
    }

    void updateFirstName(final AppDatabase appDatabase, final String firstName, final String oldFirstName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.userDao().updateFirstName(firstName, oldFirstName);
            }
        }).start();
    }
    //endregion

    //region TestModels
    ///////////////*******test********////////////////
    void getAllTestModels(final AppDatabase appDatabase) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.testModelDao().getAll();
            }
        }).start();
    }

    void getAllTestModelsBySyncStatus(final AppDatabase appDatabase, final String syncStatus, final QueryCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<TestModel> testModels = appDatabase.testModelDao().findBySyncStatus(syncStatus);
                callBack.onSuccess(testModels);
            }
        }).start();
    }

    void updateTestModel(final AppDatabase appDatabase, final String syncStatus, final String oldSyncStatus) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.testModelDao().updateSyncStatus(syncStatus, oldSyncStatus);
            }
        }).start();
    }

    private void populateWithTestData(final AppDatabase appDatabase, final String name, final String status) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TestModel testModel = new TestModel();
                testModel.setName(name);
                testModel.setSyncStatus(status);
                appDatabase.testModelDao().insertAll(testModel);
            }
        }).start();
    }
    //endregion
}
