package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatapp.API.ApiService;
import com.example.chatapp.API.dialog.DialogAPI;
import com.example.chatapp.API.dialog.DialogResponse;
import com.example.chatapp.API.dialog.DialogSendBody;
import com.example.chatapp.API.dialog.DialogEntity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogActivity extends AppCompatActivity {

    private String currentNick = Account.id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        LinearLayout layout = findViewById(R.id.linearLayout);

        updateDialog();

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText messageTextInput = findViewById(R.id.messageTextInput);

                DialogAPI api = ApiService.getInstance().getDialogAPI();
                DialogSendBody body = new DialogSendBody();
                body.from = currentNick;
                body.message = messageTextInput.getText().toString();

                Call<DialogResponse> call = api.sendDialog(body);
                call.enqueue(new Callback<DialogResponse>() {
                    @Override
                    public void onResponse(Call<DialogResponse> call, Response<DialogResponse> response) {
                        if(response.isSuccessful()){
                            messageTextInput.setText("");
                            updateDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<DialogResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private CardView createMessageCardView(DialogEntity dialog){
        CardView card = new CardView(this);

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        if(dialog.from.equals(this.currentNick)){
            cardParams.gravity = Gravity.RIGHT;
        }
        else{
            cardParams.gravity = Gravity.LEFT;
        }
        cardParams.setMargins(16, 16, 0, 0);

        card.setLayoutParams(cardParams);
        card.setRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, this.getResources().getDisplayMetrics()));

        TextView text = new TextView(this);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        text.setLayoutParams(textParams);
        text.setMaxWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, this.getResources().getDisplayMetrics()));
        text.setTextSize(20f);
        text.setTextColor(Color.WHITE);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        text.setPadding(5, 5, 5, 5);

        if(dialog.from.equals(this.currentNick)) {
            text.setBackgroundColor(Color.parseColor("#FF03DAC5"));
        }
        else{
            text.setBackgroundColor(Color.parseColor("#FF018786"));
        }

        text.setText(dialog.from + "\n" + dialog.message);

        card.addView(text);

        return card;
    }

    private void addToLayout(List<DialogEntity> dialogs){
        LinearLayout layout = findViewById(R.id.linearLayout);
        layout.removeAllViews();

        for(DialogEntity dialog: dialogs){
            CardView card = createMessageCardView(dialog);
            layout.addView(card);
        }
    }

    private void updateDialog(){
        DialogAPI api = ApiService.getInstance().getDialogAPI();

        Call<DialogResponse> call = api.getDialogs();
        call.enqueue(new Callback<DialogResponse>() {
            @Override
            public void onResponse(Call<DialogResponse> call, Response<DialogResponse> response) {
                if(response.isSuccessful()){
                    addToLayout(response.body().dialogs);
                }
            }

            @Override
            public void onFailure(Call<DialogResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}