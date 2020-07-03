package com.example.currencyconverter;

import com.google.gson.annotations.SerializedName;


public class Model_Class {
    private static String currency;
    private float amount;
    private float newAmount;

    public float chosenCurrencyRate(String currency){
        float preferedCurrency = 0;
        switch (currency){
            case "CAD":
                preferedCurrency = getRates().CAD;
                break;
            case "HKD":
                preferedCurrency = getRates().HKD;
                break;
            case "ISK":
                preferedCurrency = getRates().ISK;
                break;
            case "MYR":
                preferedCurrency = getRates().MYR;
                break;
            case "KRW":
                preferedCurrency = getRates().KRW;
                break;
            case "ILS":
                preferedCurrency = getRates().ILS;
                break;
            case "MXN":
                preferedCurrency = getRates().MXN;
                break;
            case "USD":
                preferedCurrency = getRates().USD;
                break;
            case "ZAR":
                preferedCurrency = getRates().ZAR;
                break;
            case "GBP":
                preferedCurrency = getRates().GBP;
                break;

        }
        return preferedCurrency;
    }

    public float getNewAmount(float currencyRate, float amount){
        return amount * currencyRate;
    }

   @SerializedName("CAD")
    private float CAD;

    @SerializedName("HKD")
    private float HKD;

    @SerializedName("ILS")
    private float ILS;

    @SerializedName("MXN")
    private float MXN;

    @SerializedName("USD")
    private float USD;

    @SerializedName("ZAR")
    private float ZAR;

    @SerializedName("MYR")
    private float MYR;

    @SerializedName("KRW")
    private float KRW;

    @SerializedName("GBP")
    private float GBP;

    @SerializedName("base")
    private String base;

    public String getBase() { return base; }

    @SerializedName("rates")
    private Model_Class rates;

    @SerializedName("success")
    private boolean success;

    @SerializedName("ISK")
    private float ISK;

    //Setter and getters for properties
    public float getCAD() {
        return CAD;
    }

    public float getHKD() {
        return HKD;
    }

    public Model_Class getRates() {
        return rates;
    }

    public float getISK() {
        return ISK;
    }
}
