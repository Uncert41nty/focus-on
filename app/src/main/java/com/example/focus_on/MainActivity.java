package com.example.focus_on;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.focus_on.authorisation.Auth;
import com.example.focus_on.authorisation.SignInActivity;
import com.example.focus_on.blocked.apps.BlockedAppsFragment;
import com.example.focus_on.databinding.ActivityMainBinding;
import com.example.focus_on.discipline.timer.FocusFragment;
import com.example.focus_on.goals.GoalsFragment;
import com.example.focus_on.notes.NotesFragment;
import com.example.focus_on.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    AlertDialog dialog;
    Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FocusFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.focus) {
                replaceFragment(new FocusFragment());
            } else if (itemId == R.id.goals) {
                underDevelopmentAlert();
                replaceFragment(new FocusFragment());
            } else if (itemId == R.id.blockedApps) {
                underDevelopmentAlert();
                replaceFragment(new FocusFragment());
            } else if (itemId == R.id.notes) {
                replaceFragment(new NotesFragment());
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

        auth = new Auth(getApplicationContext());
        if (auth.getUser() == null){
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void underDevelopmentAlert() {
        AlertDialog.Builder notAvailableAlert = new AlertDialog.Builder(MainActivity.this);
        String alertText = "This feature is under development at the moment";
        notAvailableAlert.setTitle(alertText);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);

        dialog = notAvailableAlert.create();
        dialog.show();
    }

}