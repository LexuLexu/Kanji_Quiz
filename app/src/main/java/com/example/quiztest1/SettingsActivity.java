package com.example.quiztest1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //SharedPreferences settingsPref = getApplicationContext().getSharedPreferences("Settings", 0);
        //setTheme(settingsPref.getBoolean("nightMode", false)? R.style.Theme_QuizTest1_Dark : R.style.Theme_QuizTest1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4).setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case(R.id.ic_profile):
                        Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.ic_friends):
                        Intent intent2 = new Intent(SettingsActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case(R.id.ic_leaderboard):
                        Intent intent3 = new Intent(SettingsActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case(R.id.ic_settings):
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

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        });
    }

    public void go_to_questions (View view) {

        Intent questionsIntent = new Intent(SettingsActivity.this, EndlessActivity.class);
        startActivity(questionsIntent);

    }

}
