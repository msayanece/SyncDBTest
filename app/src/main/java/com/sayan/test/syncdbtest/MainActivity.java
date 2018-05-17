package com.sayan.test.syncdbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sayan.test.syncdbtest.databaseclasses.AppDatabase;
import com.sayan.test.syncdbtest.databaseclasses.tables.TestDBModel;
import com.sayan.test.syncdbtest.syncdbtestsdk.InterceptorHTTPClientCreator;
import com.sayan.test.syncdbtest.syncdbtestsdk.Service;
import com.sayan.test.syncdbtest.syncdbtestsdk.SyncDBTestSdk;
import com.sayan.test.syncdbtest.syncdbtestsdk.models.Test;
import com.sayan.test.syncdbtest.syncdbtestsdk.models.TestRetrofitModel;
import com.sayan.test.syncdbtest.syncdbtestsdk.models.TestRetrofitModelsHolder;
import com.sayan.test.syncdbtest.syncdbtestsdk.responses.TestResponse;
import com.sayan.test.syncdbtest.syncdbtestsdk.responses.UpdateTestsResponse;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getAppDatabase(this);
        InterceptorHTTPClientCreator.createInterceptorHTTPClient(getApplicationContext());
        service = new SyncDBTestSdk.Builder().build(getApplicationContext()).getService();
    }

    public void onClickFetchData(View view) {
        getAllTestModels(appDatabase);
    }

    public void onClickSaveData(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        final String enteredName = editText.getText().toString();
        ArrayList<TestRetrofitModel> retrofitModels = new ArrayList<>();
        retrofitModels.add(new TestRetrofitModel(1, enteredName == null? "" : enteredName, "Y"));

        service.saveNamesToServer(new TestRetrofitModelsHolder(retrofitModels))
                .enqueue(new Callback<UpdateTestsResponse>() {
                    @Override
                    public void onResponse(Call<UpdateTestsResponse> call, Response<UpdateTestsResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResult().equalsIgnoreCase("success")) {
                                    if (response.body().getTests() != null) {
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
                    public void onFailure(Call<UpdateTestsResponse> call, Throwable t) {
                        populateWithTestData(appDatabase, enteredName, "N");
                    }
                });
    }

    public void updateStatus(View view) {
        getAllTestModelsBySyncStatus(appDatabase, "N", new QueryCallBack<TestDBModel>() {
            @Override
            public void onSuccess(ArrayList<TestDBModel> models) {
                ArrayList<TestRetrofitModel> retrofitModels = new ArrayList<>();
                for (TestDBModel model :
                        models) {
                    retrofitModels.add(new TestRetrofitModel(model.getId(), model.getName(), model.getSyncStatus()));
                }
                service.saveNamesToServer(new TestRetrofitModelsHolder(retrofitModels))
                        .enqueue(new Callback<UpdateTestsResponse>() {
                            @Override
                            public void onResponse(Call<UpdateTestsResponse> call, Response<UpdateTestsResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getResult().equalsIgnoreCase("success")) {
                                            if (response.body().getTests() != null) {
                                                if (!response.body().getTests().isEmpty()) {
                                                    ArrayList<Test> tests = (ArrayList<Test>) response.body().getTests();
                                                    for (Test test :
                                                            tests) {
                                                        updateTestModel(appDatabase, "Y", test.getId());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateTestsResponse> call, Throwable t) {
                            }
                        });
            }
        });
    }

    public void clear(View view) {
        deleteAllRows();
    }

    //region UserDBModel
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
    private void getAllTestModels(final AppDatabase appDatabase) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.testModelDao().getAll();
            }
        }).start();
    }

    private void getAllTestModelsBySyncStatus(final AppDatabase appDatabase, final String syncStatus, final QueryCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TestDBModel[] testDBModels = appDatabase.testModelDao().findBySyncStatus(syncStatus);
                ArrayList<TestDBModel> models = new ArrayList<TestDBModel>(Arrays.asList(testDBModels));
                callBack.onSuccess(models);
            }
        }).start();
    }

    private void updateTestModel(final AppDatabase appDatabase, final String syncStatus, final String oldSyncStatus) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.testModelDao().updateSyncStatus(syncStatus, oldSyncStatus);
            }
        }).start();
    }

    private void updateTestModel(final AppDatabase appDatabase, final String syncStatus, final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.testModelDao().updateSyncStatus(syncStatus, id);
            }
        }).start();
    }

    private void populateWithTestData(final AppDatabase appDatabase, final String name, final String status) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TestDBModel testDBModel = new TestDBModel();
                testDBModel.setName(name);
                testDBModel.setSyncStatus(status);
                appDatabase.testModelDao().insertAll(testDBModel);
            }
        }).start();
    }

    private void deleteAllRows() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.testModelDao().deleteAll();
            }
        }).start();
    }
    //endregion
}
