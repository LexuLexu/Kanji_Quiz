package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(2).setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case (R.id.ic_profile):
                        Intent intent = new Intent(AboutActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case (R.id.ic_friends):
                        Intent intent2 = new Intent(AboutActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case (R.id.ic_leaderboard):
                        Intent intent3 = new Intent(AboutActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case (R.id.ic_settings):
                        Intent intent4 = new Intent(AboutActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });
    }

    public void go_to_questions (View view) {
        Intent levelChoiceIntent = new Intent(AboutActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);
    }
}
