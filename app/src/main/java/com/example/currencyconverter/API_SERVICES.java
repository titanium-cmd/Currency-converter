package com.example.currencyconverter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API_SERVICES {
    /*@GET("rates/latest.json?beautify=true&key=d4495b79-cd2e-4dac-8c48-1fbcaadbd17b")
    Call<Model_Class> currency();*/

    @GET("https://v2.api.forex/infos/currencies.json")
    Call<CurrencyDetails> getDetails();

    @GET("live?access_key=18d7bf6034f7f102b38da91d64d506c8")
    Call<List<Model_Class>> currency();

}
