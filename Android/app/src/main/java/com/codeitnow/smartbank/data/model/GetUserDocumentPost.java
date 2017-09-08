package com.codeitnow.smartbank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Malhotra on 9/8/2017.
 */

public class GetUserDocumentPost {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private List<GetAllDocumentsData> data = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<GetAllDocumentsData> getData() {
        return data;
    }

    public void setData(List<GetAllDocumentsData> data) {
        this.data = data;
    }
}
