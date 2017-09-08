package com.codeitnow.smartbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codeitnow.smartbank.data.model.AddProfilePost;
import com.codeitnow.smartbank.data.model.GetProfileData;
import com.codeitnow.smartbank.data.model.GetProfilePost;
import com.codeitnow.smartbank.data.remote.APIService;
import com.codeitnow.smartbank.data.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    APIService apiService;
    EditText name,email,address;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialize();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        startActivity(new Intent(Profile.this,Choice.class));
    }

    private void initialize() {
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        apiService = ApiUtils.getAPIService();
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address  = (EditText) findViewById(R.id.address);
        getProfile();
    }

    public void saveProfile(View view) {
        apiService.addUserProfile(
                name.getText().toString(),
                sharedPreferences.getString("phone",null),
                email.getText().toString(),
                address.getText().toString()
        ).enqueue(new Callback<AddProfilePost>() {

            @Override
            public void onResponse(Call<AddProfilePost> call, Response<AddProfilePost> response) {
                String success = response.body().getSuccess();
                if(success.equals("1")) {
                    Toast.makeText(Profile.this, "Profile Saved Successfully", Toast.LENGTH_SHORT).show();
                    Profile.this.finish();
                    startActivity(new Intent(Profile.this,Choice.class));
                }
                else {
                    Toast.makeText(Profile.this, "Profile Not Saved \n Please check your network connection and try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddProfilePost> call, Throwable t) {
                Toast.makeText(Profile.this, "Profile Not Saved \n Please check your network connection and try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProfile() {

        apiService.getUserProfile(sharedPreferences.getString("phone",null)).enqueue(new Callback<GetProfilePost>() {
            @Override
            public void onResponse(Call<GetProfilePost> call, Response<GetProfilePost> response) {
                String success = response.body().getSuccess();
                if(success.equals("1")) {
                    GetProfileData getProfileData = response.body().getData();
                    name.setText(getProfileData.getName());
                    email.setText(getProfileData.getEmail());
                    address.setText(getProfileData.getAddress());
                }
                else {

                    Toast.makeText(Profile.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfilePost> call, Throwable t) {

                Toast.makeText(Profile.this, "Unable to fetch data \n Please check your network connection\n and try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
