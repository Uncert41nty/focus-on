package com.example.focus_on.authorisation;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focus_on.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText eMailEditText;
    Button resetPasswordButton;
    Button backButton;
    FirebaseAuth firebaseAuth;
    String stringEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        defineViews();
        listeners();
    }

    private void defineViews(){
        eMailEditText = findViewById(R.id.eMailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        backButton = findViewById(R.id.backButton);
    }

    private void listeners() {
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringEmail = eMailEditText.getText().toString().trim();
                if (TextUtils.isEmpty(stringEmail)) {
                    resetPasswordFunction();
                } else {
                    eMailEditText.setError("E-mail field left empty");
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void resetPasswordFunction(){
        firebaseAuth.sendPasswordResetEmail(stringEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPasswordActivity.this, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                resetPasswordButton.setVisibility(View.GONE);
                backButton.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPasswordActivity.this, "Error :- " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}