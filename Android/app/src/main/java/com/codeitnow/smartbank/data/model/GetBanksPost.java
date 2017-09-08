package com.codeitnow.smartbank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Malhotra on 9/2/2017.
 */

public class GetBanksPost {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<GetBanksData> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<GetBanksData> getData() {
        return data;
    }

    public void setData(List<GetBanksData> data) {
        this.data = data;
    }

}
