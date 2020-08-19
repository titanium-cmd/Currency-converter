package com.example.currencyconverter;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_CLIENT{
    private static final String CONVERTER_API_KEY = "c017j2j7uufj4me86p3ls27xlpi105lzt88dl1a20gxdzlyi9c732g3eqs9c";
    private static final String INFO_API_KEY = "SwywdykjWcS8cpJJ8Mkrgp0pJLMoSUbunoINvgVfYCGTpSaEsv";
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
        retrofit = null;
        final String INFO_BASE_URL = "https://fcsapi.com/api-v2/";
        //if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://fcsapi.com/api-v2/").addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setLenient()
                    .create())).build();
        //}
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