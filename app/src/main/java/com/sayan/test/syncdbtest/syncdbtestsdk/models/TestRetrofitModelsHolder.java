package com.sayan.test.syncdbtest.syncdbtestsdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TestRetrofitModelsHolder {
    @SerializedName("tests")
    @Expose
    private ArrayList<TestRetrofitModel> testRetrofitModels;

    public TestRetrofitModelsHolder(ArrayList<TestRetrofitModel> testRetrofitModels) {
        this.testRetrofitModels = testRetrofitModels;
    }
}
