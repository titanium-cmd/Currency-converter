package com.example.currencyconverter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_CLIENT{
    private static String BASE_URL = "https://v2.api.forex/";
    //private  static  String BASE_URL = "http://apilayer.net/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}