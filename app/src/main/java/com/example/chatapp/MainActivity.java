package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatapp.API.ApiService;
import com.example.chatapp.API.account.AccountAPI;
import com.example.chatapp.API.account.AccountLoginBody;
import com.example.chatapp.API.account.AccountLoginResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText loginInput = findViewById(R.id.loginTextInput);
                TextInputEditText passwordInput = findViewById(R.id.passwordTextInput);

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

                if(isAllInputFilled){
                    AccountAPI api = ApiService.getInstance().getAccountAPI();

                    AccountLoginBody body = new AccountLoginBody();
                    body.login = loginInput.getText().toString();
                    body.password = passwordInput.getText().toString();

                    Call<AccountLoginResponse> call = api.loginUser(body);
                    call.enqueue(new Callback<AccountLoginResponse>() {
                        @Override
                        public void onResponse(Call<AccountLoginResponse> call, Response<AccountLoginResponse> response) {
                            if(response.isSuccessful()){
                                Log.d("Login", response.body().status.toString());
                                Log.d("Id", response.body().id.toString());

                                Account.id = response.body().id;

                                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountLoginResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}