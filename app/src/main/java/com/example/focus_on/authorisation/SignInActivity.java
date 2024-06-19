package com.example.focus_on.authorisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.focus_on.MainActivity;
import com.example.focus_on.R;
import com.example.focus_on.user.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    EditText eMailOrPhoneNumberEditText;
    EditText passwordEditText;
    Button signInButton;
    RelativeLayout createAccountLayout;
    TextView forgotPasswordTextView;
    Auth auth;
    private final static int LAUNCH_FORGOT_PASSWORD_ACTIVITY = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = new Auth(getApplicationContext());
        signInDefineViews();
        signInListeners();
    }

    private void signInDefineViews() {
        eMailOrPhoneNumberEditText = findViewById(R.id.signInPhoneOrMail);
        passwordEditText = findViewById(R.id.signInPassword);
        signInButton = findViewById(R.id.signInButton);
        createAccountLayout = findViewById(R.id.createAccountLayout);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
    }

    private void signInListeners() {
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivityForResult(intent, LAUNCH_FORGOT_PASSWORD_ACTIVITY);
                finish();
            }
        });

        createAccountLayout.setOnClickListener(new View.OnClickListener() {
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
                    eMailOrPhoneNumberEditText.setError(getResources().getText(R.string.field_is_empty));
                } else if (password.isEmpty()) {
                    isEverythingOK = false;
                    passwordEditText.setError(getResources().getText(R.string.field_is_empty));
                }

                if (isEverythingOK) {


                    //Проверка почты и после пароля
                    if (eMailOrPhoneNumber.contains("@")) {
                        Query queryEmail = FirebaseDatabase.getInstance().getReference().child("accounts").orderByChild("eMail").equalTo(eMailOrPhoneNumber);
                        queryEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.hasChildren()) {
                                    eMailOrPhoneNumberEditText.setError(getResources().getText(R.string.error_entered_data));
                                } else {
                                    for (DataSnapshot userSnapshot:snapshot.getChildren()) {
                                        User u = userSnapshot.getValue(User.class);
                                        if (!password.equals(u.getPassword())) {
                                            passwordEditText.setError(getResources().getText(R.string.incorrect_password));
                                        } else {
                                            auth.setUsername(u.getUserName());
                                            auth.setUser(u);
                                            auth.setKey(u.getKey());
                                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        //Проверка Номера телефона и после пароля
                        Query queryPhoneNumber = FirebaseDatabase.getInstance().getReference().child("accounts").orderByChild("phoneNumber").equalTo(eMailOrPhoneNumber);
                        queryPhoneNumber.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.hasChildren()) {
                                    eMailOrPhoneNumberEditText.setError(getResources().getText(R.string.error_entered_data));
                                } else {
                                    for (DataSnapshot userSnapshot:snapshot.getChildren()) {
                                        User u = userSnapshot.getValue(User.class);
                                        if (!password.equals(u.getPassword())) {
                                            passwordEditText.setError(getResources().getText(R.string.incorrect_password));
                                        } else {
                                            auth.setUsername(u.getUserName());
                                            auth.setUser(u);
                                            auth.setKey(u.getKey());
                                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });
    }
}