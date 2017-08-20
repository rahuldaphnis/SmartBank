package com.codeitnow.smartbank.SmartBankUtilities;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Rahul Malhotra on 8/19/2017.
 */

public class VolleySingleton {
    private static VolleySingleton instance = null;
    private RequestQueue requestQueue;
    private VolleySingleton() {
        requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }
    public static VolleySingleton getInstance() {
        if(instance == null) {
            instance = new VolleySingleton();
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

}
