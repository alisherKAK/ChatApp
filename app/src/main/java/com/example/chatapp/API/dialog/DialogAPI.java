package com.example.chatapp.API.dialog;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DialogAPI {
    @POST("/dialog/send")
    Call<DialogResponse> sendDialog(@Body DialogSendBody body);

    @GET("/dialog/get")
    Call<DialogResponse> getDialogs();
}
