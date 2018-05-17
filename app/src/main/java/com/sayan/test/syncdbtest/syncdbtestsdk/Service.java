package com.sayan.test.syncdbtest.syncdbtestsdk;

import com.sayan.test.syncdbtest.syncdbtestsdk.models.TestRetrofitModel;
import com.sayan.test.syncdbtest.syncdbtestsdk.models.TestRetrofitModelsHolder;
import com.sayan.test.syncdbtest.syncdbtestsdk.responses.TestResponse;
import com.sayan.test.syncdbtest.syncdbtestsdk.responses.UpdateTestsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pc41 on 15-01-2018.
 */

public interface Service {

    //////////////////////////////******************* NAVIGATION DRAWER***********************///////////////////////////////////
    @POST("/eva/dev/test/insert.php")
    @FormUrlEncoded
    Call<TestResponse> saveNameToServer(@Field("name") String name);

    @POST("/eva/dev/test/insert.php")
    Call<UpdateTestsResponse> saveNamesToServer(@Body TestRetrofitModelsHolder testRetrofitModelsHolder);

}
