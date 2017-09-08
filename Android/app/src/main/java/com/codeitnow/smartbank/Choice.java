package com.codeitnow.smartbank;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.accountkit.AccountKit;

public class Choice extends AppCompatActivity {

    Button profile,account;
    SharedPreferences.Editor sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        initialize();
    }

    private void initialize() {
        profile = (Button) findViewById(R.id.profile);
        account = (Button) findViewById(R.id.accounts);
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE).edit();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice.this.finish();
                startActivity(new Intent(Choice.this, Profile.class));
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Choice.this.finish();
                startActivity(new Intent(Choice.this, MyAccounts.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    public void onLogout() {
        AccountKit.logOut();
        sharedPreferences.clear();
        sharedPreferences.commit();
        this.finish();
        startActivity(new Intent(Choice.this,MainActivity.class));
    }
}
