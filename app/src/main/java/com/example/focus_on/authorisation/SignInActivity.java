package com.example.focus_on.authorisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.focus_on.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    EditText eMailOrPhoneNumberEditText;
    EditText passwordEditText;
    Button signInButton;
    TextView createAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInDefineViews();
        signInListeners();
    }

    private void signInDefineViews() {
        eMailOrPhoneNumberEditText = findViewById(R.id.signInPhoneOrMail);
        passwordEditText = findViewById(R.id.signInPassword);
        signInButton = findViewById(R.id.signInButton);
        createAccountTextView = findViewById(R.id.createAccountTextView);
    }

    private void signInListeners() {
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isEverythingOK = true;
                String eMailOrPhoneNumber = eMailOrPhoneNumberEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (eMailOrPhoneNumber.isEmpty()) {
                    isEverythingOK = false;
                    eMailOrPhoneNumberEditText.setError("This field is left empty");
                } else if (password.isEmpty()) {
                    isEverythingOK = false;
                    passwordEditText.setError("This field is left empty");
                }

                if (isEverythingOK) {
                    Query queryEmail = FirebaseDatabase.getInstance().getReference().child("accounts").orderByChild("eMail").equalTo(eMailOrPhoneNumber);
                    queryEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()) {

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}