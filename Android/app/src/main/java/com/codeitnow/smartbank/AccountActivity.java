package com.codeitnow.smartbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeitnow.smartbank.SmartBankUtilities.Config;
import com.codeitnow.smartbank.SmartBankUtilities.VolleySingleton;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String phoneNumberFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initialize();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                String accountKitId = account.getId();
                PhoneNumber phoneNumberac = account.getPhoneNumber();
                //String phoneNumber = formatPhoneNumber(account.getPhoneNumber().toString());
                String phoneNumber = phoneNumberac.toString();
                phoneNumberFinal = phoneNumber;
                addUser();
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                Toast.makeText(AccountActivity.this, accountKitError.getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialize() {
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
    }

    private void addUser() {
        String url = Config.coreUrl + "User/adduser";
        final ProgressDialog pDialog = new ProgressDialog(AccountActivity.this);
        pDialog.setMessage("Logging In...");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String success = object.getString("success");
                    String userid = object.getString("userid");
                    pDialog.hide();
                    if (success.equals("1")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("phone", phoneNumberFinal);
                        editor.putString("userid", userid);
                        editor.commit();
                        Toast.makeText(AccountActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        AccountActivity.this.finish();
                        startActivity(new Intent(AccountActivity.this, Choice.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Cannot Log In. Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AccountActivity.this, "Error please check your network connection and try again later", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("phone", phoneNumberFinal);
                return params;
//                        return super.getParams();
            }
        };
        VolleySingleton.getInstance().getRequestQueue().add(stringRequest);
    }

}
