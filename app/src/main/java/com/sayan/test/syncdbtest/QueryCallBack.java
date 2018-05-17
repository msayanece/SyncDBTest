package com.sayan.test.syncdbtest;

import com.sayan.test.syncdbtest.databaseclasses.tables.TestModel;

import java.util.ArrayList;

interface QueryCallBack<T> {
    void onSuccess(ArrayList<T> models);
}
