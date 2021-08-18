package com.example.currencyconverter;

import com.google.gson.annotations.SerializedName;

public class CurrencyProfile {
    @SerializedName("name")
    private String name;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("country")
    private String country;
    @SerializedName("icon")
    private String icon;
    @SerializedName("type")
    private String type;
    @SerializedName("shortname")
    private String shortname;
    @SerializedName("bank")
    private String bank;
    @SerializedName("banknotes")
    private String banknotes;
    @SerializedName("banknotes2")
    private String banknotes2;
    @SerializedName("coins")
    private String coins;
    @SerializedName("code_n")
    private String codeN;
    @SerializedName("subunit")
    private String subunit;
    @SerializedName("website")
    private String website;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {return type;}
    public void setType(String type) { this.type = type; }

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShortName() {
        return shortname;
    }

    public void setShortName(String shortname) {
        this.shortname = shortname;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNotes() {
        return banknotes;
    }

    public void setBankNotes(String banknotes) {
        this.banknotes = banknotes;
    }

    public String getBankNotes2() {
        return banknotes2;
    }

    public void setBanknotes2(String banknotes2) {
        this.banknotes2 = banknotes2;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getCodeN() {
        return codeN;
    }

    public void setCodeN(String codeN) {
        this.codeN = codeN;
    }

    public String getSubUnit() {
        return subunit;
    }

    public void setSubUnit(String subunit) {
        this.subunit = subunit;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
