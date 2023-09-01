package com.example.tutorial.User.Report;

import com.google.gson.annotations.SerializedName;

public class ReportItem {
    @SerializedName("content")
    private String strContent;

    @SerializedName("date")
    private String strDate;

    @SerializedName("id")
    private int intId;

    @SerializedName("image_url")
    private String strImg;

    @SerializedName("status")
    private int intStatus;

    @SerializedName("type_report")
    private String strType;

    @SerializedName("phone")
    private String strPhone;

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }

    public String getStrPhone() {
        return strPhone;
    }

    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrImg() {
        return strImg;
    }

    public void setStrImg(String strImg) {
        this.strImg = strImg;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public int getIntId() {
        return intId;
    }

    public void setIntId(int intId) {
        this.intId = intId;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }
}
