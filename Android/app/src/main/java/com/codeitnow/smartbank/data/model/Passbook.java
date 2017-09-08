package com.codeitnow.smartbank.data.model;

/**
 * Created by Rahul Malhotra on 8/31/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passbook {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("particulars")
    @Expose
    private String particulars;
    @SerializedName("debit")
    @Expose
    private String debit;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("balance")
    @Expose
    private String balance;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}