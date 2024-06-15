package com.example.focus_on.authorisation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.focus_on.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText newPasswordEditText;
    EditText repeatNewPasswordEdittext;
    Button passwordConfirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        defineViews();
        listeners();
    }

    private void defineViews(){
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        repeatNewPasswordEdittext = findViewById(R.id.repeatNewPasswordEditText);
        passwordConfirmButton = findViewById(R.id.confirmNewPasswordButton);
    }

    private void listeners() {
        passwordConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}