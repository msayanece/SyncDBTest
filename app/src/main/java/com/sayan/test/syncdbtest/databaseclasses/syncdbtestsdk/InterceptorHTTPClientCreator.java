package com.sayan.test.syncdbtest.databaseclasses.syncdbtestsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;


import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Admin on 08-11-2017.
 */

public class InterceptorHTTPClientCreator {
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "SYNCDBTEST_PREF_UNIQUE_ID";

    private static OkHttpClient defaultHttpClient;

    private synchronized static String getUniqueId(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }

    public static void createInterceptorHTTPClient(final Context context){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            defaultHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
//                                            .addHeader("deviceIdPhysical", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID))
//                                            .addHeader("fcmId", FirebaseInstanceId.getInstance().getToken())
                                            .addHeader("deviceType","android")
                                            .addHeader("deviceId",("Desideals" + getUniqueId(context)))
                                            .build();
                                    return chain.proceed(request);
                                }
                            })
                    .readTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .build();
    }

    static OkHttpClient getOkHttpClient(){
        if (defaultHttpClient != null){
            return defaultHttpClient;
        }
        return null;
    }
}
