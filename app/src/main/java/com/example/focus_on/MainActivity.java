package com.example.focus_on;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.focus_on.blocked.apps.BlockedAppsFragment;
import com.example.focus_on.databinding.ActivityMainBinding;
import com.example.focus_on.discipline.timer.FocusFragment;
import com.example.focus_on.goals.GoalsFragment;
import com.example.focus_on.notes.NotesFragment;
import com.example.focus_on.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FocusFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.focus:
                    replaceFragment(new FocusFragment());
                    break;
                case R.id.goals:
                    replaceFragment(new GoalsFragment());
                    break;
                case R.id.blockedApps:
                    replaceFragment(new BlockedAppsFragment());
                    break;
                case R.id.notes:
                    replaceFragment(new NotesFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}