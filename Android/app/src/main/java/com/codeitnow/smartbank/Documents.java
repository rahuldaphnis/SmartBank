package com.codeitnow.smartbank;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.codeitnow.smartbank.SmartBankObjects.PassbookObject;
import com.codeitnow.smartbank.SmartBankUtilities.Config;
import com.codeitnow.smartbank.SmartBankUtilities.VolleySingleton;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Documents extends AppCompatActivity {

    String type="0";
    SharedPreferences sharedPreferences;
    TextView empty;
    ImageButton newaadhar, newpan, oldaadhar, oldpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        initialize();
    }

    public void initialize() {
        empty = (TextView) findViewById(R.id.empty);
        newaadhar = (ImageButton) findViewById(R.id.newaadhar);
        oldaadhar = (ImageButton) findViewById(R.id.oldaadhar);
        newpan = (ImageButton) findViewById(R.id.newpan);
        oldpan = (ImageButton) findViewById(R.id.oldpan);
        sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);

        newaadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        newpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        oldaadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            progressView.stopAnimation();
                            empty.setVisibility(View.INVISIBLE);
                            progressView.setVisibility(View.INVISIBLE);
                            if(object.getString("success").equals("1")) {
                                Toast.makeText(Documents.this, "Document Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Documents.this, "Document Not Added", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Documents.this, "Error please check your network connection and try again later", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("userid", sharedPreferences.getString("userid",null));
                        params.put("bankid", sharedPreferences.getString("bankid",null));
                        params.put("accountnumber", sharedPreferences.getString("accountnumber",null));
                        params.put("type", "1");
                        params.put("documentid", "1");
                        return params;
//                        return super.getParams();
                    }
                };
                VolleySingleton.getInstance().getRequestQueue().add(stringRequest);

          }
        });

        oldpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
