package com.example.currencyconverter;

import com.google.gson.annotations.SerializedName;

public class CurrencyProfile {
    @SerializedName("short_name")
    private String shortName;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;
    @SerializedName("code_n")
    private String codeN;
    @SerializedName("subunit")
    private String subUnit;
    @SerializedName("website")
    private String website;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("symbol_2")
    private String symbol2;
    @SerializedName("bank")
    private String bank;
    @SerializedName("banknotes")
    private String bankNotes;
    @SerializedName("banknotes_2")
    private String bankNotes2;
    @SerializedName("coins")
    private String coins;
    @SerializedName("coins_2")
    private String coins2;
    @SerializedName("icon")
    private String icon;
    @SerializedName("type")
    private String type;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCodeN() {
        return codeN;
    }

    public void setCodeN(String codeN) {
        this.codeN = codeN;
    }

    public String getSubUnit() {
        return subUnit;
    }

    public void setSubUnit(String subUnit) {
        this.subUnit = subUnit;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNotes() {
        return bankNotes;
    }

    public void setBankNotes(String bankNotes) {
        this.bankNotes = bankNotes;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol2() {
        return symbol2;
    }

    public void setSymbol2(String symbol2) {
        this.symbol2 = symbol2;
    }

    public String getBankNotes2() {
        return bankNotes2;
    }

    public void setBankNotes2(String bankNotes2) {
        this.bankNotes2 = bankNotes2;
    }

    public String getCoins2() {
        return coins2;
    }

    public void setCoins2(String coins2) {
        this.coins2 = coins2;
    }
}
