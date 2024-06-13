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

import java.util.Random;

public class TimerActivity extends AppCompatActivity {
    TextView countDownTextView;
    TextView phrasesTextView;
    TextView breakTimeBetweenSessions;
    Button pauseButton;
    Button resumeButton;
    Button quitButton;
    AlertDialog dialog;
    AlertDialog dialogConfirm;
    private CountDownTimer countDownTimer;
    long receivedTimeInMillis;
    long receivedBreakTimeInMillis;
    int pauseAttempts = 3;
    TextView sessionCountTextView;
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent timerIntent = getIntent();

        int receivedTimeInSeconds = timerIntent.getIntExtra("focusTime", 0);
        receivedTimeInMillis = receivedTimeInSeconds * 1000L;
        int breakTimeInSeconds = timerIntent.getIntExtra("breakTime", 0);
        receivedBreakTimeInMillis = breakTimeInSeconds * 1000L;
        int sessionCount = timerIntent.getIntExtra("sessionCount",0);
        int currentSession = timerIntent.getIntExtra("currentSession", 0);

        String currentSessionText = "Session " + currentSession + "/" + sessionCount;

        defineViews();
        setPhrasesTextView();
        sessionCountTextView.setText(currentSessionText);
        startCountDown(receivedTimeInMillis);
        breakTimeText(receivedBreakTimeInMillis);
    }

    private void setPhrasesTextView() {
        String[] phrasesArray = {
                "“Your time is limited, don't spend it living someone else's life.” - Steve Jobs.",
                "“Time takes everything, whether you like it or not.” — Stephen King",
                "“I am not a product of my circumstances. I am the product of my decisions. - Stephen Covey",
                "“The best revenge is great success” - Frank Sinatra.",
                "Time is the greatest of innovators. - Francis Bacon",
                "Time wasted is existence; time used profitably is life. - E. Jung",
                "He who does not know the value of time is not born for glory.\n" +
                        "L. Vauvenargues",
                "Time does not wait and does not forgive a single lost moment.\n" +
                        "N. Garin-Mikhailovsky",
                "Time is a mirage, it shortens in moments of happiness and stretches out in hours of suffering.\n" +
                        "R. Aldington",
                "Time is space for developing abilities...\n" +
                        "K. Marx",
                "Don't count the days, make the most of them. | Muhammad Ali",
                "Work hard, dream big.",
                "Hard work beats talent when talent doesn't work. | Unknown",
                "Do the hard work first. The easy work will take care of itself. | Dale Carnegie",
                "Don't look at your watch; do what they do. Do not stop. | Sam Levenson",
                "Do one thing every day that you dread. | Eleanor Roosevelt",
                "Undisciplined people are slaves to mood, desire and passion."
        };
        rand = new Random();
        int phrases = rand.nextInt(phrasesArray.length);
        phrasesTextView.setText(phrases);
    }

    private void defineViews() {
        countDownTextView = findViewById(R.id.countDownTextView);
        phrasesTextView = findViewById(R.id.phrasesTextView);
        pauseButton = findViewById(R.id.sessionPauseButton);
        resumeButton = findViewById(R.id.continueSessionButton);
        breakTimeBetweenSessions = findViewById(R.id.breakTimeBetweenSessions);
        quitButton = findViewById(R.id.sessionQuitButton);
        sessionCountTextView = findViewById(R.id.sessionCountTextView);
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
                pauseAlert.setNegativeButton("No, continue session", null);

                dialog = pauseAlert.create();
                dialog.show();

            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeCountdown();
                resumeButton.setVisibility(View.GONE);
                if (pauseAttempts>0) {
                    pauseButton.setVisibility(View.VISIBLE);
                }
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder quitAlert = new AlertDialog.Builder(TimerActivity.this);
                quitAlert.setTitle("Are you sure you want to break sessions?");
                quitAlert.setPositiveButton("Quit sessions", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                });

                dialogConfirm = quitAlert.create();
                dialogConfirm.show();
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