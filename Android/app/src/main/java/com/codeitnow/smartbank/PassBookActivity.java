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
import com.codeitnow.smartbank.data.model.AddAccountData;
import com.codeitnow.smartbank.data.model.AddAccountPost;
import com.codeitnow.smartbank.data.model.Passbook;
import com.codeitnow.smartbank.data.remote.APIService;
import com.codeitnow.smartbank.data.remote.ApiUtils;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class PassBookActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    APIService apiService;
    TextView empty,ownername,passbookdata,accountstatus,phone,accountnumber,accountbalance,accountcreated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_book);
        initialize();
    }

    private void initialize() {
        apiService = ApiUtils.getAPIService();
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
/*        String ps = sharedPreferences.getString("phone",null)+
                sharedPreferences.getString("accountnumber",null)+
                sharedPreferences.getString("ifsccode",null)+
                sharedPreferences.getString("userid",null)+
                sharedPreferences.getString("bankid",null);
*/            apiService.getAccountDetails(
                sharedPreferences.getString("phone",null),
                sharedPreferences.getString("accountnumber",null),
                sharedPreferences.getString("ifsccode",null),
                sharedPreferences.getString("userid",null),
                sharedPreferences.getString("bankid",null)).enqueue(new Callback<AddAccountPost>() {
            @Override
            public void onResponse(Call<AddAccountPost> call, retrofit2.Response<AddAccountPost> response) {
                String success = response.body().getSuccess();
                //Toast.makeText(PassBookActivity.this, "hehe"+response.body().toString(), Toast.LENGTH_SHORT).show();
                if(success.equals("1")) {
                    AddAccountData addAccountData = response.body().getData();
                    ownername.setText("Name = "+addAccountData.getOwnername());
                    accountstatus.setText("Account Status = "+addAccountData.getAccountstatus());
                    phone.setText("Phone = "+addAccountData.getPhone());
                    accountnumber.setText("Account Number = "+addAccountData.getAccountnumber());
                    accountbalance.setText("Balance = "+addAccountData.getBalance());
                    accountcreated.setText("Created Date = "+addAccountData.getCreateddate());
                    List<Passbook> passbookList = addAccountData.getPassbook();
                    String p = "";
                    for (int i = 0; i < passbookList.size() ; i++) {
                        Passbook passBook = passbookList.get(i);
                        p = p + passBook.getDate() +"||"+ passBook.getParticulars() +"||"+ passBook.getDebit() +"||"+ passBook.getCredit() +"||"+ passBook.getBalance() + "\n";
                    }
                    passbookdata.setText(p);
                    empty.setVisibility(View.INVISIBLE);
                    progressView.stopAnimation();
                    progressView.setVisibility(View.INVISIBLE);
                }
                else {
                    empty.setVisibility(View.VISIBLE);
                    empty.setText("No results found!");
                    progressView.stopAnimation();
                    progressView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AddAccountPost> call, Throwable t) {
                empty.setVisibility(View.VISIBLE);
                empty.setText("Error Fetching Data!");
                progressView.stopAnimation();
                progressView.setVisibility(View.INVISIBLE);
            }
        });

    }
/*
    private void fetchpassbook1() throws JSONException {
        final CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.startAnimation();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid", sharedPreferences.getString("userid",null));
        params.put("bankid", sharedPreferences.getString("bankid",null));
        params.put("accountnumber", sharedPreferences.getString("accountnumber",null));
        params.put("ifsccode", sharedPreferences.getString("ifsccode",null));
        params.put("phone", sharedPreferences.getString("phone",null));
        String url = Config.coreUrl+"Bank/addAccount";
        JSONObject object1,object = null;
        JSONArray array;
        SendStringRequest s1 = new SendStringRequest(url,params);
        object = s1.execute();
        object1 = (JSONObject) object.get("data");
        ownername.setText("Name = "+object1.getString("ownername"));
        accountstatus.setText("Account Status = "+object1.getString("accountstatus"));
        phone.setText("Phone = "+object1.getString("phone"));
        accountnumber.setText("Account Number = "+object1.getString("accountnumber"));
        accountbalance.setText("Balance = "+object1.getString("balance"));
        accountcreated.setText("Created Date = "+object1.getString("createddate"));
        array = new JSONArray(object1.getString("passbook"));
        progressView.stopAnimation();
        empty.setVisibility(View.INVISIBLE);
        progressView.setVisibility(View.INVISIBLE);
        Gson gson = new Gson();
        String p = "";
        for (int i = 0; i < array.length(); i++) {
            PassbookObject passBook = gson.fromJson(array.getJSONObject(i).toString(), PassbookObject.class);
            p = p + passBook.date +"||"+ passBook.particulars +"||"+ passBook.debit +"||"+ passBook.credit +"||"+ passBook.balance + "\n";
        }
        passbookdata.setText(p);
        if (array.length() == 0) {
            empty.setVisibility(View.VISIBLE);
            empty.setText("No results found!");
            progressView.stopAnimation();
            progressView.setVisibility(View.INVISIBLE);

        } else {
            progressView.stopAnimation();
            progressView.setVisibility(View.INVISIBLE);
            //userAccountAdapter.notifyDataSetChanged();
            //reportedchildsview.setAdapter(reportedChildAdapter);
        }
    }
*/
}
