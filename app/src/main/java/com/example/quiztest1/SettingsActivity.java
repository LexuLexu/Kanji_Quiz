package com.example.quiztest1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        if (Global.darkMode == true) {
            mainLayout.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        }
        else {
            mainLayout.setBackgroundColor(getColor(R.color.background));
        }

        Switch muteSwitch = findViewById(R.id.muteSoundsSwitch);
        if (Global.soundMuted == true) {
            muteSwitch.setChecked(true);
        }
        else {
            muteSwitch.setChecked(false);
        }

        Switch darkModeSwitch = findViewById(R.id.nightModeSwitch);
        if (Global.darkMode == true) {
            darkModeSwitch.setChecked(true);
        }
        else {
            darkModeSwitch.setChecked(false);
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(4).setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case (R.id.ic_profile):
                        Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case (R.id.ic_friends):
                        Intent intent2 = new Intent(SettingsActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case (R.id.ic_leaderboard):
                        Intent intent3 = new Intent(SettingsActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case (R.id.ic_settings):
                        //Intent intent4 = new Intent(SettingsActivity.this, SettingsActivity.class);
                        //startActivity(intent4);
                        break;

                }
                return false;

            }

        });

        //SharedPreferences.Editor settingsEditor = settingsPref.edit();

        //Switch toggleSwitch = findViewById(R.id.nightModeSwitch);

        //toggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

        //    @Override
        //    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //        settingsEditor.putBoolean("nightMode", isChecked);
        //        settingsEditor.commit();
        //    }

        //});

        TextView logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        });

        TextView freqText = findViewById(R.id.frequencyAmount);
        SeekBar freqBar = findViewById(R.id.frequencyBar);
        freqBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String[] freqArray = getResources().getStringArray(R.array.Frequency);
                freqText.setText(freqArray[progress]);

                /** switch (progress) {

                    case 0:
                        freqText.setText("Daily");
                        break;
                    case 1:
                        freqText.setText("Every 2 days");
                        break;
                    case 2:
                        freqText.setText("Every 3 days");
                        break;
                    case 3:
                        freqText.setText("Every 4 days");
                        break;
                    case 4:
                        freqText.setText("Every 5 days");
                        break;
                    case 5:
                        freqText.setText("Every 6 days");
                        break;
                    case 6:
                        freqText.setText("Weekly");
                        break;
                    case 7:
                        freqText.setText("Monthly");
                        break;

                } **/

            }
        });
    }

    public void mute_sounds (View view) {
        if (Global.soundMuted == true) {
            Global.soundMuted = false;
            System.out.println("Sound unmuted.");
        }
        else {
            Global.soundMuted = true;
            System.out.println("Sound muted.");
        }
    }

    public void toggle_dark_mode (View view) {
        if (Global.darkMode == true) {
            Global.darkMode = false;
            System.out.println("Dark mode disabled.");
        }
        else {
            Global.darkMode = true;
            System.out.println("Dark mode enabled.");
        }
    }

    public void go_to_questions (View view) {
        Intent levelChoiceIntent = new Intent(SettingsActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);
    }

    public void go_to_about (View view) {
        startActivity(new Intent (SettingsActivity.this, AboutActivity.class));
    }

    public void go_to_FAQ (View view) {
        startActivity(new Intent (SettingsActivity.this, FAQActivity.class));
    }

}
