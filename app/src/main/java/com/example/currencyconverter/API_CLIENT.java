package com.example.currencyconverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_CLIENT{
    private static String BASE_URL = "https://metals-api.com/api/";
    private static final String API_KEY = "9g91i7i8wkr9clsdzdz4ij95zpnqc6kvr5pfkb7oxr7fjvgaz49k85ha25wk";
    private static final String SYMBOL_TYPE = "forex";
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setLenient()
                    .create())).build();
        }
        return retrofit;
    }

    public static String getApiKey(){
        return API_KEY;
    }

    public static String getSymbolType(){return SYMBOL_TYPE;}
}