package com.example.focus_on.discipline.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.focus_on.R;
import com.google.android.material.tabs.TabLayout;

public class FocusFragment extends Fragment {
    TabLayout modeTabs;
    View v;
    ViewPager2 viewPager2;
    ModeTabsFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_focus, container, false);
        defineViews();
        return v;

    }

    private void defineViews() {
        modeTabs = v.findViewById(R.id.focusModes);
    }
}