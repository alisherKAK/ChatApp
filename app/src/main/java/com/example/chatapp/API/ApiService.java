package com.example.chatapp.API;

import com.example.chatapp.API.account.AccountAPI;
import com.example.chatapp.API.dialog.DialogAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static ApiService instance;
    private static final String BASE_URL = "http://10.0.2.2:8000";
    private Retrofit retrofit;

    private ApiService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    public AccountAPI getAccountAPI(){
        return retrofit.create(AccountAPI.class);
    }
    public DialogAPI getDialogAPI(){
        return retrofit.create(DialogAPI.class);
    }
}
