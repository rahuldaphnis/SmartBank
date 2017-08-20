package com.codeitnow.smartbank;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeitnow.smartbank.SmartBankObjects.PassbookObject;
import com.codeitnow.smartbank.SmartBankObjects.UserAccount;
import com.codeitnow.smartbank.SmartBankUtilities.Config;
import com.codeitnow.smartbank.SmartBankUtilities.VolleySingleton;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PassBook extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView empty,ownername,passbookdata,accountstatus,phone,accountnumber,accountbalance,accountcreated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_book);
        initialize();
    }

    private void initialize() {
        empty = (TextView) findViewById(R.id.empty);
        ownername = (TextView) findViewById(R.id.name);
        accountstatus = (TextView) findViewById(R.id.accountstatus);
        phone = (TextView) findViewById(R.id.phone);
        accountnumber = (TextView) findViewById(R.id.accountnumber);
        accountbalance = (TextView) findViewById(R.id.accountbalance);
        accountcreated = (TextView) findViewById(R.id.accountcreated);
        passbookdata = (TextView) findViewById(R.id.passbookdata);
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        fetchpassbook();
    }

    private void fetchpassbook() {
        final CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.startAnimation();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = Config.coreUrl+"Bank/addAccount";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray array;
                JSONObject object,object1 = null;
                try {
                    object = new JSONObject(response);
                    object1 = (JSONObject) object.get("data");
                    ownername.setText(object1.getString("ownername"));
                    accountstatus.setText(object1.getString("accountstatus"));
                    phone.setText(object1.getString("phone"));
                    accountnumber.setText(object1.getString("accountnumber"));
                    accountbalance.setText(object1.getString("balance"));
                    accountcreated.setText(object1.getString("createddate"));
                    array = new JSONArray(object1.getString("passbook"));
                    progressView.stopAnimation();
                    empty.setVisibility(View.INVISIBLE);
                    progressView.setVisibility(View.INVISIBLE);
                    Gson gson = new Gson();
                    String p = "";
                    for (int i = 0; i < array.length(); i++) {
                        PassbookObject passBook = gson.fromJson(array.getJSONObject(i).toString(), PassbookObject.class);
                        p = p + passBook.date + passBook.particulars + passBook.debit + passBook.credit + passBook.balance + "\n";
                        //userAccountArrayList.add(userAccount);
                        //    Log.d("heya",reportedchildslist.get(i).age);
                        //    Log.d("hello",array.getJSONObject(i).toString());
                        //    Log.d("shared",sharedPreferences.getString("username",null));
                    }
                    passbookdata.setText(p);
                    if (array.length() == 0) {
                        empty.setVisibility(View.VISIBLE);
                        empty.setText("No results found!");
                    } else {
                        //userAccountAdapter.notifyDataSetChanged();
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
                Toast.makeText(PassBook.this, "Error please check your network connection and try again later", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", sharedPreferences.getString("userid",null));
                params.put("bankid", sharedPreferences.getString("bankid",null));
                params.put("accountnumber", sharedPreferences.getString("accountnumber",null));
                params.put("ifsccode", sharedPreferences.getString("ifsccode",null));
                params.put("phone", sharedPreferences.getString("phone",null));
                return params;
//                        return super.getParams();
            }
        };
        VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
    }
}
