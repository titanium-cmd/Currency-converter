package com.example.currencyconverter;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_CLIENT{
    private static final String CONVERTER_API_KEY = "9g91i7i8wkr9clsdzdz4ij95zpnqc6kvr5pfkb7oxr7fjvgaz49k85ha25wk";
    private static final String INFO_API_KEY = "59tGEctrFnm3H72Sbwc57Fm1Df3OF8zjI7YqpOLtqf04E";
    private static final String SYMBOL_TYPE = "forex";
    private static Retrofit retrofit = null;

    public static Retrofit getConverterApiClient() {
        final String CONVERTER_BASE_URL = "https://metals-api.com/api/";
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(CONVERTER_BASE_URL).addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setLenient()
                    .create())).build();
        }
        return retrofit;
    }

    public static Retrofit getInfoApiClient() {
        final String INFO_BASE_URL = "https://fcsapi.com/api-v2/";
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(INFO_BASE_URL).addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setLenient()
                    .create())).build();
        }
        return retrofit;
    }

    public static String getInfoApiKey(){
        return INFO_API_KEY;
    }

    public static String getConverterApiKey(){
        return CONVERTER_API_KEY;
    }

    public static String getSymbolType(){return SYMBOL_TYPE;}
}