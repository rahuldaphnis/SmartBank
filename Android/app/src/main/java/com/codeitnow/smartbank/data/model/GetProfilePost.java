package com.codeitnow.smartbank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul Malhotra on 9/2/2017.
 */

public class GetProfilePost {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private GetProfileData data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public GetProfileData getData() {
        return data;
    }

    public void setData(GetProfileData data) {
        this.data = data;
    }

}