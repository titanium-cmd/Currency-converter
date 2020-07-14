package com.example.currencyconverter;

public class Currencies {
    private String currency_name, currency_code;
    private String currency_id;

    public String getCurrencyId() {
        return currency_id;
    }

    public void setCurrencyId(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrencyName() {
        return currency_name;
    }

    public void setCurrencyName(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCurrencyCode() {
        return currency_code;
    }

    public void setCurrencyCode(String currency_code) {
        this.currency_code = currency_code;
    }
}
