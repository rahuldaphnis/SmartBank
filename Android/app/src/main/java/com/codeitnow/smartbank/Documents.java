package com.codeitnow.smartbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeitnow.smartbank.SmartBankAdapters.DocumentAdapter;
import com.codeitnow.smartbank.SmartBankObjects.PassbookObject;
import com.codeitnow.smartbank.SmartBankUtilities.Config;
import com.codeitnow.smartbank.SmartBankUtilities.VolleySingleton;
import com.codeitnow.smartbank.data.model.GetBanksData;
import com.codeitnow.smartbank.data.model.GetDocumentsData;
import com.codeitnow.smartbank.data.model.GetUserBankDocumentPost;
import com.codeitnow.smartbank.data.remote.APIService;
import com.codeitnow.smartbank.data.remote.ApiUtils;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class Documents extends AppCompatActivity {

    private RecyclerView myDocumentsList;
    DocumentAdapter documentAdapter;
    APIService apiService;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        initialize();
    }

    public void addDocument(View view) {
        this.finish();
        startActivity(new Intent(Documents.this,AddDocument.class));
    }

    public void chooseDocument(View view) {
        this.finish();
        startActivity(new Intent(Documents.this,ChooseDocument.class));
    }

    public void initialize() {

        myDocumentsList = (RecyclerView) findViewById(R.id.mydocumentslist);
        myDocumentsList.setHasFixedSize(true);
        myDocumentsList.setLayoutManager(new LinearLayoutManager(this));
        apiService = ApiUtils.getAPIService();
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        apiService.getUserBankDocuments(
                sharedPreferences.getString("userid",null),
                sharedPreferences.getString("bankid",null)
        ).enqueue(new Callback<GetUserBankDocumentPost>() {
            @Override
            public void onResponse(Call<GetUserBankDocumentPost> call, retrofit2.Response<GetUserBankDocumentPost> response) {
                String success = response.body().getSuccess();
                if(success.equals("1")) {
                    List<GetDocumentsData> getDocumentsDataList = response.body().getData();
                    documentAdapter = new DocumentAdapter(Documents.this,getDocumentsDataList);
                    myDocumentsList.setAdapter(documentAdapter);
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(Documents.this, "No Document Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserBankDocumentPost> call, Throwable t) {
                Toast.makeText(Documents.this, "Please check your network connection and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
