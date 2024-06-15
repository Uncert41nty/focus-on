package com.example.focus_on.authorisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.focus_on.R;
import com.example.focus_on.user.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    EditText eMailEditText;
    EditText phoneNumberEditText;
    EditText userNameEditText;
    EditText passwordEditText;
    EditText repeatPasswordEditText;
    Button signUpButton;
    RelativeLayout logInLayout;
    Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = new Auth(getApplicationContext());
        signUpDefineViews();
        signUpListeners();
    }

    private void signUpDefineViews() {
        eMailEditText = findViewById(R.id.signUpEMail);
        phoneNumberEditText = findViewById(R.id.signUpPhoneNumber);
        userNameEditText = findViewById(R.id.signUpUserName);
        passwordEditText = findViewById(R.id.signUpPassword);
        repeatPasswordEditText = findViewById(R.id.signUpRepeatPassword);
        signUpButton = findViewById(R.id.signUpButton);
        logInLayout = findViewById(R.id.logInLayout);
    }

    private void signUpListeners() {
        logInLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isEverythingOK = true;

                String userName = userNameEditText.getText().toString();
                String eMail = eMailEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String repeatPassword = repeatPasswordEditText.getText().toString();

                if (userName.isEmpty()) {
                    isEverythingOK = false;
                    userNameEditText.setError("This field is left empty");
                } else if (eMail.isEmpty()) {
                    isEverythingOK = false;
                    eMailEditText.setError("This field is left empty");
                } else if (password.isEmpty()) {
                    isEverythingOK = false;
                    passwordEditText.setError("This field is left empty");
                } else if (repeatPassword.isEmpty()) {
                    isEverythingOK = false;
                    repeatPasswordEditText.setError("This field is left empty");
                } else if (password.length() < 8) {
                    isEverythingOK = false;
                    passwordEditText.setError("The password must consist of at least 8 characters");
                } else if (!password.matches("\\w+")) {
                    isEverythingOK = false;
                    passwordEditText.setError("The password must consist only of Latin letters, underscores or numbers.");
                } else if (!repeatPassword.equals(password)) {
                    isEverythingOK = false;
                    repeatPasswordEditText.setError("Passwords don't match");
                }

                if (isEverythingOK) {
                    Query queryEmail = FirebaseDatabase.getInstance().getReference().child("accounts").orderByChild("eMail").equalTo(eMail);
                    queryEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()) {
                                eMailEditText.setError("Account with this e-mail already exists!");
                            } else {
                                Query queryPhoneNumber = FirebaseDatabase.getInstance().getReference().child("accounts").orderByChild("phoneNumber").equalTo(phoneNumber);
                                queryPhoneNumber.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.hasChildren()) {
                                            phoneNumberEditText.setError("Account with this Phone Number already exists!");
                                        } else {
                                            DatabaseReference accounts = FirebaseDatabase.getInstance().getReference().child("accounts");
                                            String key = accounts.push().getKey();
                                            User user = new User(eMail, phoneNumber, userName, password, key);
                                            accounts.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    auth.setUsername(userName);
                                                    auth.setUser(user);
                                                    auth.setKey(key);
                                                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                                    startActivity(intent);
                                                    finish();
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

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}