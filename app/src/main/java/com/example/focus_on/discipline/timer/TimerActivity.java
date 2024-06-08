package com.example.focus_on.discipline.timer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.focus_on.R;

public class TimerActivity extends AppCompatActivity {
    TextView countDownTextView;
    TextView phrasesTextView;
    TextView breakTimeBetweenSessions;
    Button pauseButton;
    Button resumeButton;
    AlertDialog dialog;
    private CountDownTimer countDownTimer;
    long receivedTimeInMillis;
    long receivedBreakTimeInMillis;
    int pauseAttempts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent timerIntent = getIntent();

        int receivedTimeInSeconds = timerIntent.getIntExtra("focusTime", 0);
        receivedTimeInMillis = receivedTimeInSeconds;
        int breakTimeInSeconds = timerIntent.getIntExtra("breakTime", 0);
        receivedBreakTimeInMillis = breakTimeInSeconds;


        defineViews();
        startCountDown(receivedTimeInMillis);
        breakTimeText(receivedBreakTimeInMillis);
    }

    private void defineViews() {
        countDownTextView = findViewById(R.id.countDownTextView);
        phrasesTextView = findViewById(R.id.phrasesTextView);
        pauseButton = findViewById(R.id.sessionPauseButton);
        resumeButton = findViewById(R.id.continueSessionButton);
        breakTimeBetweenSessions = findViewById(R.id.breakTimeBetweenSessions);
    }

    private void pauseCountdown() {
        if (pauseAttempts > 0) {
            countDownTimer.cancel();
            pauseAttempts--;
        }
    }

    private void resumeCountdown() {
        startCountDown(receivedTimeInMillis);
    }

    private void updateCountdownText() {
        int hours = (int) (receivedTimeInMillis / 3600000);
        int minutes = (int) (receivedTimeInMillis % 3600000) / 60000;
        int secs = (int) (receivedTimeInMillis % 60000) / 1000;

        String focusTime = String.format("%02d:%02d:%02d", hours, minutes, secs);
        countDownTextView.setText(focusTime);
    }

    private void breakTimeText(long receivedBreakTimeInMillis) {
        int hours = (int) (this.receivedBreakTimeInMillis / 3600000);
        int minutes = (int) (this.receivedBreakTimeInMillis % 3600000) / 60000;
        int secs = (int) (this.receivedBreakTimeInMillis % 60000) / 1000;

        String breakTimeDisplay = String.format("%02d:%02d:%02d", hours, minutes, secs);
        breakTimeBetweenSessions.setText("Break between sessions " + breakTimeDisplay);
    }

    private void startCountDown(long millis) {
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder pauseAlert = new AlertDialog.Builder(TimerActivity.this);
                String pauseText = "You have " + pauseAttempts + "/3 pause attempts left";

                pauseAlert.setMessage(pauseText);
                pauseAlert.setTitle("You shouldn't break your discipline");
                pauseAlert.setPositiveButton("Pause session", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pauseCountdown();
                        pauseButton.setVisibility(View.GONE);
                        resumeButton.setVisibility(View.VISIBLE);
                    }
                });
                pauseAlert.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        AlertDialog.Builder quitAlert = new AlertDialog.Builder(TimerActivity.this);
                        quitAlert.setTitle("Are you sure you want to break your session and today's plan?");
                        quitAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setResult(Activity.RESULT_CANCELED);
                                finish();
                            }
                        });
                    }
                });
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeCountdown();
                resumeButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.VISIBLE);
            }
        });
        countDownTimer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                receivedTimeInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                setResult(Activity.RESULT_OK);
                finish();
            }
        }.start();
    }


}