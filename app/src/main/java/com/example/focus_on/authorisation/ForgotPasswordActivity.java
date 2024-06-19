package com.example.focus_on.authorisation;

import android.app.Activity;
import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText eMailEditText;
    Button resetPasswordButton;
    Button backButton;
    FirebaseAuth firebaseAuth;
    DatabaseReference db;
    DatabaseReference forgotPasswordReference;
    String stringEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        forgotPasswordReference = db.child("accounts");

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
                if (!TextUtils.isEmpty(stringEmail)) {
                    resetPasswordFunction();
                } else {
                    eMailEditText.setError(getResources().getText(R.string.field_is_empty));
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
        Query queryEmail = FirebaseDatabase.getInstance().getReference().child("accounts").orderByChild("eMail").equalTo(stringEmail);
        queryEmail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChildren()) {
                    eMailEditText.setError(getResources().getText(R.string.error_entered_data));
                } else {
                    firebaseAuth.sendPasswordResetEmail(stringEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ForgotPasswordActivity.this, getResources().getText(R.string.password_reset_link), Toast.LENGTH_SHORT).show();
                            setResult(Activity.RESULT_OK);
                            resetPasswordButton.setVisibility(View.GONE);
                            backButton.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ForgotPasswordActivity.this, getResources().getText(R.string.error) + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}