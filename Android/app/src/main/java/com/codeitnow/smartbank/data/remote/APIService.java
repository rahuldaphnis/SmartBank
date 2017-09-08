package com.codeitnow.smartbank.data.remote;

import com.codeitnow.smartbank.data.model.AddAccountData;
import com.codeitnow.smartbank.data.model.AddAccountPost;
import com.codeitnow.smartbank.data.model.AddExistingDocument;
import com.codeitnow.smartbank.data.model.AddProfilePost;
import com.codeitnow.smartbank.data.model.GetBanksPost;
import com.codeitnow.smartbank.data.model.GetProfilePost;
import com.codeitnow.smartbank.data.model.GetUserBankDocumentPost;
import com.codeitnow.smartbank.data.model.GetUserDocumentPost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Rahul Malhotra on 8/31/2017.
 */

public interface APIService {

    @POST("Bank/addAccount")
    @FormUrlEncoded
    Call<AddAccountPost> getAccountDetails(
            @Field("phone") String phone,
            @Field("accountnumber") String accountnumber,
            @Field("ifsccode") String ifsccode,
            @Field("userid") String userid,
            @Field("bankid") String bankid
            );

    @POST("User/addProfile")
    @FormUrlEncoded
    Call<AddProfilePost> addUserProfile(
                    @Field("name") String name,
                    @Field("phone") String phone,
                    @Field("email")  String email,
                    @Field("address") String address
            );

    @POST("User/getProfile")
    @FormUrlEncoded
    Call<GetProfilePost> getUserProfile(
            @Field("phone") String phone
    );

    @POST("Bank/getBanks")
    @FormUrlEncoded
    Call<GetBanksPost> getAllBanks(
            @Field("hello") String hello
    );

    @POST("Bank/getUserBankDocuments")
    @FormUrlEncoded
    Call<GetUserBankDocumentPost> getUserBankDocuments(
            @Field("userid") String userid,
            @Field("bankid") String bankid
            );

    @POST("Bank/getUserDocuments")
    @FormUrlEncoded
    Call<GetUserDocumentPost> getUserDocuments(
            @Field("userid") String userid
    );

    @POST("Bank/addExistingDocument")
    @FormUrlEncoded
    Call<AddExistingDocument> addExistingDocument (
            @Field("userid") String userid,
            @Field("bankid") String bankid,
            @Field("type") String type,
            @Field("accountnumber") String accountnumber,
            @Field("documentid") String documentid
            );
}