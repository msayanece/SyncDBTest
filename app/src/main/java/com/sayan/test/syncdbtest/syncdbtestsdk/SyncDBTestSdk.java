package com.sayan.test.syncdbtest.syncdbtestsdk;

import android.content.Context;

import com.sayan.test.syncdbtest.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pc41 on 15-01-2018.
 */

public class SyncDBTestSdk {

    private final Retrofit retrofit;
    private Service service;

    public SyncDBTestSdk(Retrofit retrofit) {
        this.retrofit = retrofit;
        createService();
    }
    /**
     * Builder for {@link SyncDBTestSdk}
     */
    public static class Builder {
        public Builder() {
        }
        /**
         * Create the {@link SyncDBTestSdk} to be used.
         *
         * @return {@link SyncDBTestSdk}
         */public SyncDBTestSdk build(Context context) {
            Retrofit retrofit = null;
            if (InterceptorHTTPClientCreator.getOkHttpClient() != null) {
                retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(InterceptorHTTPClientCreator.getOkHttpClient())
                    .baseUrl(context.getResources().getString(R.string.base_url))
                    .build();

                return new SyncDBTestSdk(retrofit);

            }else{
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
//                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(context.getResources().getString(R.string.base_url))
                        .build();

            }

            return new SyncDBTestSdk(retrofit);
            }

    }

    private void createService() {
        service = retrofit.create(Service.class);

    }
    public Service getService(){
        return service;
    }
}
