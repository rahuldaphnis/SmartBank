package com.codeitnow.smartbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountMenu extends AppCompatActivity {

    Button passbook, documents, removeaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);
        initialize();
    }

    private void initialize() {
        passbook = (Button) findViewById(R.id.passbook);
        documents = (Button) findViewById(R.id.documents);
        removeaccount = (Button) findViewById(R.id.removeaccount);

        passbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountMenu.this, PassBook.class));
            }
        });

        documents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountMenu.this,Documents.class));
            }
        });
    }


}
