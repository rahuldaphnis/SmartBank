package com.codeitnow.smartbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);
        initialize();
    }

    private void initialize() {

    }

    public void openPassbook(View view) {

        this.finish();
        startActivity(new Intent(AccountMenu.this, PassBookActivity.class));
    }

    public void openDocuments(View view) {

        this.finish();
        startActivity(new Intent(AccountMenu.this,Documents.class));

    }

    public void goBack(View view) {

        this.finish();
        startActivity(new Intent(AccountMenu.this,MyAccounts.class));
    }

    public void removeAccount(View view) {

        this.finish();
        startActivity(new Intent(AccountMenu.this,MyAccounts.class));
    }

}
