package com.sayan.test.syncdbtest.syncdbtestsdk.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sync")
    @Expose
    private String sync;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }
}
