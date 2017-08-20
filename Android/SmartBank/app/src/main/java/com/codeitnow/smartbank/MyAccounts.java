package com.codeitnow.smartbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.codeitnow.smartbank.SmartBankAdapters.UserAccountAdapter;
import com.codeitnow.smartbank.SmartBankObjects.UserAccount;
import com.codeitnow.smartbank.SmartBankUtilities.Config;
import com.codeitnow.smartbank.SmartBankUtilities.MyApplication;
import com.codeitnow.smartbank.SmartBankUtilities.VolleySingleton;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAccounts extends AppCompatActivity {

    private TextView empty;
    private RecyclerView addedaccounts;
    UserAccountAdapter userAccountAdapter;
    SharedPreferences sharedPreferences;
    private ArrayList<UserAccount> userAccountArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accounts);
        initialize();
        adddatatoview();
    }

    private void adddatatoview()
    {
        final CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.startAnimation();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = Config.coreUrl+"Bank/getsavedBanks";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONArray array;
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response);
                        array = new JSONArray(object.getString("data"));
                        progressView.stopAnimation();
                        progressView.setVisibility(View.INVISIBLE);
                        Gson gson = new Gson();
                        for (int i = 0; i < array.length(); i++) {
                            UserAccount userAccount = gson.fromJson(array.getJSONObject(i).toString(), UserAccount.class);
                            userAccountArrayList.add(userAccount);
                            //    Log.d("heya",reportedchildslist.get(i).age);
                            //    Log.d("hello",array.getJSONObject(i).toString());
                            //    Log.d("shared",sharedPreferences.getString("username",null));
                        }
                        if (array.length() == 0) {
                            empty.setVisibility(View.VISIBLE);
                            empty.setText("No results found!");
                        } else {
                            userAccountAdapter.notifyDataSetChanged();
                            //reportedchildsview.setAdapter(reportedChildAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.d("err",String.valueOf(error));
                    progressView.stopAnimation();
                    progressView.setVisibility(View.INVISIBLE);
                    Toast.makeText(MyAccounts.this, "Error please check your network connection and try again later", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("userid", sharedPreferences.getString("userid",null));
                    return params;
//                        return super.getParams();
                }
            };
            VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
    }

    public void initialize() {
        empty = (TextView) findViewById(R.id.empty);
        empty.setVisibility(View.GONE);
        addedaccounts = (RecyclerView) findViewById(R.id.reportedchilds);
        addedaccounts.setHasFixedSize(true);
        addedaccounts.setLayoutManager(new LinearLayoutManager(this));
        userAccountArrayList = new ArrayList<>();
        userAccountAdapter = new UserAccountAdapter(this,userAccountArrayList);
        addedaccounts.setAdapter(userAccountAdapter);
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
    }

}
