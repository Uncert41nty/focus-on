package com.example.focus_on.profile;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.focus_on.R;
import com.example.focus_on.authorisation.Auth;
import com.example.focus_on.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText currentPasswordEditText;
    EditText newPasswordEditText;
    Button changePasswordButton;
    String currentPassword;
    String newPassword;
    DatabaseReference db;
    DatabaseReference passwordReference;
    Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        auth = new Auth(getApplicationContext());

        db = FirebaseDatabase.getInstance().getReference();
        passwordReference = db.child("accounts");

        defineViews();
        passwordChange();
    }

    private void defineViews(){
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);
    }

    private void passwordChange(){
        currentPassword = currentPasswordEditText.getText().toString();
        newPassword = newPasswordEditText.getText().toString();

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPasswordEditText.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, getResources().getText(R.string.field_is_empty), Toast.LENGTH_SHORT).show();
                } else if (currentPasswordEditText.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, getResources().getText(R.string.field_is_empty), Toast.LENGTH_SHORT).show();
                } else if (!currentPassword.equals(auth.getUser().getPassword())) {
                    Toast.makeText(ChangePasswordActivity.this, getResources().getText(R.string.current_password_does_not_match), Toast.LENGTH_SHORT).show();
                } else {
                    passwordReference.child(auth.getKey()).child("password").setValue(newPassword);
                }

            }
        });




    }
}