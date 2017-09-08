package com.codeitnow.smartbank.data.remote;

/**
 * Created by Rahul Malhotra on 8/31/2017.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://rahuldaphnis96.000webhostapp.com/SmartBank/SmartBank/index.php/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
