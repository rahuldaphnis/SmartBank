package com.codeitnow.smartbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codeitnow.smartbank.data.model.AddAccountPost;
import com.codeitnow.smartbank.data.remote.APIService;
import com.codeitnow.smartbank.data.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAccount extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText accountNumber,ifscCode;
    String bankId;
    APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        initialize();
    }

    private void initialize() {
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        apiService = ApiUtils.getAPIService();
        bankId = getIntent().getStringExtra("bankid");
        accountNumber = (EditText) findViewById(R.id.accountNumber);
        ifscCode = (EditText) findViewById(R.id.ifscCode);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        startActivity(new Intent(this,MyAccounts.class));
    }

    public void saveAccount(View view) {
        apiService.getAccountDetails(
                sharedPreferences.getString("phone",null),
                accountNumber.getText().toString(),
                ifscCode.getText().toString(),
                sharedPreferences.getString("userid",null),
                bankId
        ).enqueue(new Callback<AddAccountPost>() {
            @Override
            public void onResponse(Call<AddAccountPost> call, Response<AddAccountPost> response) {
                String success = response.body().getSuccess();
                String accountSaved = response.body().getAccountsaved();
                if(success.equals("1") && accountSaved.equals("1")) {
                    AddAccount.this.finish();
                    startActivity(new Intent(AddAccount.this,MyAccounts.class));
                }
                else {
                    Toast.makeText(AddAccount.this, "Bank Account doesn't exist \n Please check your details and try again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AddAccountPost> call, Throwable t) {
                Toast.makeText(AddAccount.this, "Please check your network connection and ry again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
