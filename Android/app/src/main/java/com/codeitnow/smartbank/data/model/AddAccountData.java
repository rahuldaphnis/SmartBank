package com.codeitnow.smartbank.data.model;

/**
 * Created by Rahul Malhotra on 8/31/2017.
 */

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class AddAccountData {

    @SerializedName("ownername")
    @Expose
    private String ownername;
    @SerializedName("accountstatus")
    @Expose
    private String accountstatus;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("accountnumber")
    @Expose
    private String accountnumber;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("accounttype")
    @Expose
    private String accounttype;
    @SerializedName("createddate")
    @Expose
    private String createddate;
    @SerializedName("passbook")
    @Expose
    private List<Passbook> passbook = null;

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public List<Passbook> getPassbook() {
        return passbook;
    }

    public void setPassbook(List<Passbook> passbook) {
        this.passbook = passbook;
    }

}