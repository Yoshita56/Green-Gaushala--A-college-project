package com.example.refining_gaushala_app.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http:/192.168.0.111:8080/";  // Replace with your server's URL
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ReportApi getReportApi() {
        return getRetrofitInstance().create(ReportApi.class);
    }

    public static AuthApi getAuthApi() {
        return getRetrofitInstance().create(AuthApi.class);
    }

}
