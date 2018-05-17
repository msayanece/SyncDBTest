package com.sayan.test.syncdbtest.syncdbtestsdk.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sayan.test.syncdbtest.syncdbtestsdk.models.Test;


import java.util.List;

public class UpdateTestsResponse {

    @SerializedName("tests")
    @Expose
    private List<Test> tests = null;
    @SerializedName("result")
    @Expose
    private String result;

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
