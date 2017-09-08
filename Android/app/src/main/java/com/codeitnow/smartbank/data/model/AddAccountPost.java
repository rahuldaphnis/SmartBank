package com.codeitnow.smartbank.data.model;

/**
 * Created by Rahul Malhotra on 8/31/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAccountPost {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private AddAccountData data;
    @SerializedName("accountsaved")
    @Expose
    private String accountsaved;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public AddAccountData getData() {
        return data;
    }

    public void setData(AddAccountData data) {
        this.data = data;
    }

    public String getAccountsaved() {
        return accountsaved;
    }

    public void setAccountsaved(String accountsaved) {
        this.accountsaved = accountsaved;
    }

    @Override
    public String toString() {
        return "AddAccountPost{" +
                "success='" + success + '\'' +
                ", data=" + data +
                ", accountsaved='" + accountsaved + '\'' +
                '}';
    }
}