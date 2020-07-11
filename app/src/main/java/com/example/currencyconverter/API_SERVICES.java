package com.example.currencyconverter;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_SERVICES {
    @GET("convert")
    Call<JsonObject> currencyConvert(@Query("access_key") String access_key, @Query("from") String from, @Query("to") String to, @Query("amount") float amount);

    @GET("{type}/profile")
    Call<CurrencyProfileResponse> getInfo(@Path("type") String type, @Query("symbol") String symbol, @Query("access_key") String access_key);
}
