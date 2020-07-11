package com.example.currencyconverter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrencyProfileResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("response")
    private List<CurrencyProfile> response;

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public List<CurrencyProfile> getResponse() { return response; }
    public void setResponse(List<CurrencyProfile> response) { this.response = response; }
}
