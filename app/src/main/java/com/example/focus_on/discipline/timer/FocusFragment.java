package com.example.focus_on.discipline.timer;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.focus_on.MainActivity;
import com.example.focus_on.R;

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
    int sessionCount = 1;
    int sessionsToDisplay = 0;
    int currentSession = 1;
    private final static int LAUNCH_SESSIONS_TIMER = 88;
    private CountDownTimer countDownTimer;
    long millis;
    TextView breakTimeCountDownTextView;
    TextView sessionTimeInfoTextView;
    Button quitBreakButton;
    Button startNextSessionButton;
    LinearLayout mainFocusLinearLayout;
    LinearLayout breakTimeLinerLayout;
    AlertDialog dialog;
    AlertDialog errorDialog;
    int focusTimeInfoForTextView;
    int breakTimeProgressGlobal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_focus, container, false);

        defineViews();
        mainFuncListeners();

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
        breakTimeCountDownTextView = v.findViewById(R.id.breakTimeCountDownTextView);
        sessionTimeInfoTextView = v.findViewById(R.id.sessionTimeInfoTextView);
        quitBreakButton = v.findViewById(R.id.quitBreakButton);
        startNextSessionButton = v.findViewById(R.id.startNextSessionButton);
        mainFocusLinearLayout = v.findViewById(R.id.mainFocusLinearLayout);
        breakTimeLinerLayout = v.findViewById(R.id.breakLinerLayout);
    }

    private void mainFuncListeners() {
        focusTimeSeekBar.setMax(7200);
        breakTimeSeekBar.setMax(1800);
        focusTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int focusTimeProgress, boolean b) {
                updateFocusTimeTextView(focusTimeProgress);
                focusTimeInfoForTextView = focusTimeProgress;
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
                millis = breakTimeProgress * 1000L;
                breakTimeProgressGlobal = breakTimeProgress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sessionPlusButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                if (sessionCount<5)
                sessionCount++;
                sessionsToDisplay = sessionCount;
                sessionsTextView.setText(Integer.toString(sessionCount));
            }
        });

        sessionMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionCount>1){
                    sessionCount--;
                    sessionsToDisplay = sessionCount;
                    sessionsTextView.setText(Integer.toString(sessionCount));
                }
            }
        });
        sessionsStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (focusTimeSeekBar.getProgress() != 0){
                    if (breakTimeSeekBar.getProgress() != 0){
                        intentFunc();
                    }
                } else {
                    emptySeekBar();
                }
            }
        });
    }

    private void intentFunc(){
        Intent intent = new Intent(getActivity(), TimerActivity.class);
        intent.putExtra("focusTime", focusTimeSeekBar.getProgress());
        intent.putExtra("breakTime", breakTimeSeekBar.getProgress());
        intent.putExtra("sessionCount", sessionsToDisplay);
        intent.putExtra("currentSession", currentSession);
        startActivityForResult(intent, LAUNCH_SESSIONS_TIMER);
    }

    private void emptySeekBar() {
        AlertDialog.Builder emptySeekBarAlert = new AlertDialog.Builder(getActivity());
        String alertText = "You didn't set session and break time";
        emptySeekBarAlert.setTitle(alertText);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                errorDialog.dismiss();
            }
        }, 2000);

        errorDialog = emptySeekBarAlert.create();
        errorDialog.show();
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

    private void updateBreakTimeCountdownText() {
        int hours = (int) (millis / 3600000);
        int minutes = (int) (millis % 3600000) / 60000;
        int secs = (int) (millis % 60000) / 1000;

        String breakTimeOnCountdown = String.format("%02d:%02d:%02d", hours, minutes, secs);
        breakTimeCountDownTextView.setText(breakTimeOnCountdown);
    }

    private String getFocusTimeInfo(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SESSIONS_TIMER) {
            if (resultCode == Activity.RESULT_OK){
                currentSession++;
                if (currentSession>sessionCount){
                    mainFocusLinearLayout.setVisibility(View.VISIBLE);
                    breakTimeLinerLayout.setVisibility(GONE);
                    currentSession=1;
                } else {
                    mainFocusLinearLayout.setVisibility(GONE);
                    breakTimeLinerLayout.setVisibility(View.VISIBLE);
                    millis = breakTimeProgressGlobal * 1000L;
                    countDownTimer = new CountDownTimer(millis, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            millis = millisUntilFinished;
                            updateBreakTimeCountdownText();
                            sessionTimeInfoTextView.setText(getFocusTimeInfo(focusTimeInfoForTextView));
                        }

                        @Override
                        public void onFinish() {
                            intentFunc();
                        }
                    }.start();

                    startNextSessionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                             countDownTimer.onFinish();
                             countDownTimer.cancel();
                        }
                    });

                    quitBreakButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder quitBreakAlert = new AlertDialog.Builder(getActivity());
                            quitBreakAlert.setTitle("Are you sure you want to quit sessions?");
                            quitBreakAlert.setPositiveButton("Yes, Quit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    countDownTimer.cancel();
                                    currentSession=1;
                                    mainFocusLinearLayout.setVisibility(View.VISIBLE);
                                    breakTimeLinerLayout.setVisibility(View.GONE);
                                }
                            });
                            quitBreakAlert.setNegativeButton("No, continue", null);

                            dialog = quitBreakAlert.create();
                            dialog.show();
                        }
                    });
                }
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                currentSession=1;
                mainFocusLinearLayout.setVisibility(View.VISIBLE);
                breakTimeLinerLayout.setVisibility(GONE);
            }
        }
    }

}