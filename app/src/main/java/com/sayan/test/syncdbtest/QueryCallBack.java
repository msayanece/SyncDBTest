package com.sayan.test.syncdbtest;

import java.util.ArrayList;

interface QueryCallBack<T> {
    void onSuccess(ArrayList<T> models);
}
