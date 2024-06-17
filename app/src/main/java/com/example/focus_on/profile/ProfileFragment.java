package com.example.focus_on.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.focus_on.R;
import com.example.focus_on.authorisation.Auth;

public class ProfileFragment extends Fragment {
    View v;
    RelativeLayout changePasswordLayout;
    RelativeLayout appLanguageLayout;
    RelativeLayout aboutLayout;
    Button signOutButton;
    private final static int LAUNCH_ABOUT_ACTIVITY = 66;
    private final static int LAUNCH_CHANGE_PASSWORD_ACTIVITY = 77;
    Auth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        auth = new Auth(getContext());

        defineViews();
        listeners();

        return v;
    }

    private void defineViews() {
        changePasswordLayout = v.findViewById(R.id.changePasswordLayout);
        appLanguageLayout = v.findViewById(R.id.changeAppLanguageLayout);
        aboutLayout = v.findViewById(R.id.aboutLayout);
        signOutButton = v.findViewById(R.id.signOutButton);
    }

    private void listeners() {
        changePasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePasswordIntent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivityForResult(changePasswordIntent, LAUNCH_CHANGE_PASSWORD_ACTIVITY);
            }
        });

        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivityForResult(aboutIntent, LAUNCH_ABOUT_ACTIVITY);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.logout();
            }
        });
    }
}