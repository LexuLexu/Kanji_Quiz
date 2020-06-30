package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Level;

public class LevelChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choice);

        CardView setNameCard = findViewById(R.id.setNameCard);
        View noNameCover = findViewById(R.id.noNameCover);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String name = user.getDisplayName();

            if (name.equals("")) {
                setNameCard.setVisibility(View.VISIBLE);
                noNameCover.setVisibility(View.VISIBLE);
            }
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(2).setChecked(false);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case(R.id.ic_profile):
                        Intent intent = new Intent(LevelChoiceActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.ic_friends):
                        Intent intent2 = new Intent(LevelChoiceActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case(R.id.ic_leaderboard):
                        Intent intent3 = new Intent(LevelChoiceActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case(R.id.ic_settings):
                        Intent intent4 = new Intent(LevelChoiceActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });
    }

    public void go_to_n5English (View v) {
        startActivity(new Intent (getApplicationContext(), N5EnglishActivity.class));
    }

    public void go_to_n5Kana (View v) {
        startActivity(new Intent (getApplicationContext(), N5KanaActivity.class));
    }

    public void go_to_profile (View v) {
        startActivity(new Intent (getApplicationContext(), ProfileActivity.class));
    }
    public void notAvailable (View v) {
        Toast.makeText(LevelChoiceActivity.this, "This level will be added in a future version", Toast.LENGTH_SHORT).show();
    }

}
