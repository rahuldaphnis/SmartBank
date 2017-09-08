package com.codeitnow.smartbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codeitnow.smartbank.SmartBankAdapters.BankAdapter;
import com.codeitnow.smartbank.SmartBankAdapters.UserAccountAdapter;
import com.codeitnow.smartbank.data.model.GetBanksData;
import com.codeitnow.smartbank.data.model.GetBanksPost;
import com.codeitnow.smartbank.data.remote.APIService;
import com.codeitnow.smartbank.data.remote.ApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectBank extends AppCompatActivity {

    RecyclerView bankRecyclerView;
    BankAdapter bankAdapter;
    List<GetBanksData> getBanksDataList;
    APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank);
        initialize();
    }

    private void initialize() {
        apiService = ApiUtils.getAPIService();
        bankRecyclerView = (RecyclerView) findViewById(R.id.banklist);
        bankRecyclerView.setHasFixedSize(true);
        bankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        apiService.getAllBanks("none").enqueue(new Callback<GetBanksPost>() {
            @Override
            public void onResponse(Call<GetBanksPost> call, Response<GetBanksPost> response) {
                String success = response.body().getSuccess();
                if(success.equals("1")) {
                    List<GetBanksData> getBanksDataList = response.body().getData();
                    bankAdapter = new BankAdapter(SelectBank.this,getBanksDataList);
                    bankRecyclerView.setAdapter(bankAdapter);
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(SelectBank.this, "No Banks Found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetBanksPost> call, Throwable t) {
                Toast.makeText(SelectBank.this, "Please check your network connection and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
