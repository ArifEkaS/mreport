package com.example.tutorial.User.Report;

import com.google.gson.annotations.SerializedName;

public class PostFormResponse {

    @SerializedName("message")
    private String strMessage;

    public String getStrMessage() { return strMessage;}

    public void getStrMessage(String strMessage) { this.strMessage = strMessage;}
}
