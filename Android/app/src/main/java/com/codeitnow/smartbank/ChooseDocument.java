package com.codeitnow.smartbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.codeitnow.smartbank.SmartBankAdapters.AllDocumentAdapter;
import com.codeitnow.smartbank.SmartBankAdapters.DocumentAdapter;
import com.codeitnow.smartbank.data.model.GetAllDocumentsData;
import com.codeitnow.smartbank.data.model.GetDocumentsData;
import com.codeitnow.smartbank.data.model.GetUserBankDocumentPost;
import com.codeitnow.smartbank.data.model.GetUserDocumentPost;
import com.codeitnow.smartbank.data.remote.APIService;
import com.codeitnow.smartbank.data.remote.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ChooseDocument extends AppCompatActivity {

    private RecyclerView myDocumentsList;
    AllDocumentAdapter documentAdapter;
    APIService apiService;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_document);
        initialize();
    }

    public void initialize() {

        myDocumentsList = (RecyclerView) findViewById(R.id.chooseDoc);
        myDocumentsList.setHasFixedSize(true);
        myDocumentsList.setLayoutManager(new LinearLayoutManager(this));
        apiService = ApiUtils.getAPIService();
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        apiService.getUserDocuments(
                sharedPreferences.getString("userid",null)
        ).enqueue(new Callback<GetUserDocumentPost>() {
            @Override
            public void onResponse(Call<GetUserDocumentPost> call, retrofit2.Response<GetUserDocumentPost> response) {
                String success = response.body().getSuccess();
                if(success.equals("1")) {
                    List<GetAllDocumentsData> getDocumentsDataList = response.body().getData();
                    documentAdapter = new AllDocumentAdapter(ChooseDocument.this,getDocumentsDataList);
                    myDocumentsList.setAdapter(documentAdapter);
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(ChooseDocument.this, "No Document Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserDocumentPost> call, Throwable t) {
                Toast.makeText(ChooseDocument.this, "Please check your network connection and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveDocument(View view) {
        this.finish();
        startActivity(new Intent(ChooseDocument.this,Documents.class));
    }
}
