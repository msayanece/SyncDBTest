package com.sayan.test.syncdbtest.databaseclasses.syncdbtestsdk;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by pc41 on 15-01-2018.
 */

public interface Service {

    //////////////////////////////******************* NAVIGATION DRAWER***********************///////////////////////////////////
    @GET("/eva/dev/test/insert.php")
    Call<TestResponse> saveNameToServer(@Query("name") String name);

}
