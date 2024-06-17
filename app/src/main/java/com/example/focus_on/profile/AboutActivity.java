package com.example.focus_on.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.focus_on.R;

public class AboutActivity extends AppCompatActivity {
    Button aboutBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutBackButton = findViewById(R.id.aboutBackButton);
        aboutBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackIntent = new Intent();
                setResult(Activity.RESULT_OK, goBackIntent);
                finish();
            }
        });
    }
}