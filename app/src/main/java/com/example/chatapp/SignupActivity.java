package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatapp.API.ApiService;
import com.example.chatapp.API.account.AccountAPI;
import com.example.chatapp.API.account.AccountSignUpBody;
import com.example.chatapp.API.account.AccountSignUpResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText loginInput = findViewById(R.id.loginTextInput);
                TextInputEditText passwordInput = findViewById(R.id.passwordTextInput);
                TextInputEditText nickInput = findViewById(R.id.nickTextInput);

                boolean isAllInputFilled = true;

                if(loginInput.getText().length() <= 0){
                    TextView loginErrorText = findViewById(R.id.loginErrorText);
                    loginErrorText.setVisibility(View.VISIBLE);

                    isAllInputFilled = false;
                }

                if(passwordInput.getText().length() <= 0){
                    TextView passwordErrorText = findViewById(R.id.passwordErrorText);
                    passwordErrorText.setVisibility(View.VISIBLE);

                    isAllInputFilled = false;
                }

                if(nickInput.getText().length() <= 0){
                    TextView nickErrorText = findViewById(R.id.nickErrorText);
                    nickErrorText.setVisibility(View.VISIBLE);

                    isAllInputFilled = false;
                }

                if(isAllInputFilled) {
                    AccountAPI api = ApiService.getInstance().getAccountAPI();

                    AccountSignUpBody body = new AccountSignUpBody();
                    body.login = loginInput.getText().toString();
                    body.password = passwordInput.getText().toString();
                    body.nick = nickInput.getText().toString();

                    Call<AccountSignUpResponse> call = api.signUpUser(body);
                    call.enqueue(new Callback<AccountSignUpResponse>() {
                        @Override
                        public void onResponse(Call<AccountSignUpResponse> call, Response<AccountSignUpResponse> response) {
                            if(response.isSuccessful()){
                                Log.d("Status", response.body().status.toString());
                                if(response.body().status == 200){
                                    finish();
                                }
                                else if(response.body().status == 500) {
                                    TextView nickTakenText = findViewById(R.id.nickTakenText);
                                    nickTakenText.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountSignUpResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}