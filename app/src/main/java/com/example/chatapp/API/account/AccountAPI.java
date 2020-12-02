package com.example.chatapp.API.account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountAPI {
    @POST("/account/register")
    Call<AccountSignUpResponse> signUpUser(@Body AccountSignUpBody body);

    @POST("/account/login")
    Call<AccountLoginResponse> loginUser(@Body AccountLoginBody body);
}
