package com.codeitnow.smartbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AddDocument extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
    }

    public void uploadImage(View view) {
        this.finish();
        startActivity(new Intent(AddDocument.this,Documents.class));
    }
}
