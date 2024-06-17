package com.example.focus_on.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focus_on.MainActivity;
import com.example.focus_on.R;
import com.example.focus_on.authorisation.SignInActivity;
import com.example.focus_on.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText currentPasswordEditText;
    EditText newPasswordEditText;
    Button changePasswordButton;
    String currentPassword;
    String newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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

        Query queryPassword = FirebaseDatabase.getInstance().getReference().child("accounts").orderByChild("password").equalTo(currentPassword);
        queryPassword.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot:snapshot.getChildren()) {
                    User u = userSnapshot.getValue(User.class);
                    if (!currentPassword.equals(u.getPassword())) {
                        currentPasswordEditText.setError("Incorrect password! Please try again");
                    } else {
                        changePasswordButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}