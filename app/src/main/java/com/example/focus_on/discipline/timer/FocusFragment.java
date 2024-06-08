package com.example.focus_on.discipline.timer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.focus_on.R;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class FocusFragment extends Fragment {
    View v;
    TextView successSessionsCount;
    TextView brokenSessionsCount;
    TextView goalSessionsCount;
    TextView focusTimeTextView;
    TextView breakTimeTextView;
    TextView sessionsTextView;
    SeekBar focusTimeSeekBar;
    SeekBar breakTimeSeekBar;
    Button sessionPlusButton;
    Button sessionMinusButton;
    Button sessionsStartButton;
    int sessionCount = 0;
    int breakTime = 0;
    int focusTime = 0;
    int sessionsToDisplay = 0;
    private final static int LAUNCH_SESSIONS_TIMER = 88;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_focus, container, false);

        defineViews();
        mainFuncListeners();
        progress();

        updateFocusTimeTextView(0);
        updateBreakTimeTextView(0);

        return v;
    }

    private void defineViews() {
        successSessionsCount = v.findViewById(R.id.successSessionsCount);
        brokenSessionsCount= v.findViewById(R.id.brokenSessionsCount);
        goalSessionsCount = v.findViewById(R.id.goalSessionsCount);
        focusTimeTextView = v.findViewById(R.id.focusTimeTextView);
        breakTimeTextView = v.findViewById(R.id.breakTimeTextView);
        sessionsTextView = v.findViewById(R.id.sessionsTextView);
        focusTimeSeekBar = v.findViewById(R.id.focusTimeSeekBar);
        breakTimeSeekBar = v.findViewById(R.id.breakTimeSeekBar);
        sessionPlusButton = v.findViewById(R.id.sessionPlusButton);
        sessionMinusButton = v.findViewById(R.id.sessionMinusButton);
        sessionsStartButton = v.findViewById(R.id.sessionsStartButton);
    }

    private void mainFuncListeners() {
        focusTimeSeekBar.setMax(7200);
        breakTimeSeekBar.setMax(1800);
        focusTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int focusTimeProgress, boolean b) {
                updateFocusTimeTextView(focusTimeProgress);
                focusTime = focusTimeProgress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        breakTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int breakTimeProgress, boolean b) {
                updateBreakTimeTextView(breakTimeProgress);
                breakTime = breakTimeProgress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sessionPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionCount++;
                sessionsToDisplay = sessionCount;
                sessionsTextView.setText(Integer.toString(sessionCount));
            }
        });

        sessionMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionCount--;
                sessionsToDisplay = sessionCount;
                sessionsTextView.setText(Integer.toString(sessionCount));
            }
        });

        sessionsStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimerActivity.class);
                intent.putExtra("focusTime", focusTime);
                intent.putExtra("breakTime", breakTime);
                intent.putExtra("sessionCount", sessionsToDisplay);
                startActivityForResult(intent, LAUNCH_SESSIONS_TIMER);
            }
        });
    }

    private void updateFocusTimeTextView(int seconds){
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String focusTime = String.format("%02d:%02d:%02d", hours, minutes, secs);
        focusTimeTextView.setText(focusTime);
    }

    private void updateBreakTimeTextView(int seconds){
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String breakTime = String.format("%02d:%02d:%02d", hours, minutes, secs);
        breakTimeTextView.setText(breakTime);
    }

    private void progress() {

    }

}