package com.example.quiztest1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        TextView text1 = findViewById(R.id.friendsText);
        if (Global.darkMode == true) {
            mainLayout.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            text1.setTextColor(getColor(R.color.colorAccent));
        }
        else {
            mainLayout.setBackgroundColor(getColor(R.color.background));
        }

        load_bottom_bar();
    }

    public void go_to_questions (View view) {

        Intent levelChoiceIntent = new Intent(FriendsActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);

    }

    public void load_bottom_bar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(1).setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case(R.id.ic_profile):
                        Intent intent = new Intent(FriendsActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.ic_friends):
                        //Intent intent2 = new Intent(FriendsActivity.this, FriendsActivity.class);
                        //startActivity(intent2);
                        break;

                    case(R.id.ic_leaderboard):
                        Intent intent3 = new Intent(FriendsActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case(R.id.ic_settings):
                        Intent intent4 = new Intent(FriendsActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });
    }

}
