package com.example.tutorial.Admin.StatusLaporan;

import com.google.gson.annotations.SerializedName;

public class UpdateStatusResponse {
    @SerializedName("message")
    private String strMessage;

    public String getStrMessage() { return strMessage;}

    public void getStrMessage(String strMessage) { this.strMessage = strMessage;}
}
