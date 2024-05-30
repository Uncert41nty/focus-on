package com.example.focus_on;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.focus_on.authorisation.SignInActivity;

public class WelcomeActivity extends AppCompatActivity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeDefineView();
        welcomeListener();
    }

    private void welcomeDefineView() {
        startButton = findViewById(R.id.startButton);
    }

    private void welcomeListener() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}