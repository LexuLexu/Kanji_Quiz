package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    public Toast myToast;

    protected EditText nameViewText;

    private int userScore;
    private int userLevel;
    private int currentLevelProgressScore;
    private int endlessMax;

    private boolean perfectN5;
    private boolean perfectN4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myToast = Toast.makeText(ProfileActivity.this, "", Toast.LENGTH_SHORT);

        userScore = 0;
        userLevel = 0;
        currentLevelProgressScore = 0;
        endlessMax = 0;

        perfectN5 = false;
        perfectN4 = false;

        get_scores();

        dark_mode();

        nameViewText = (EditText) findViewById(R.id.nameView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();

            if (!name.equals(null)) {
                nameViewText.setText(name);
            }
        }

        nameViewText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(s.toString())
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    myToast.setText("Profile updated");
                                    myToast.show();

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference usersRef = database.getReference("users");

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    String uid = user.getUid();
                                    String userName = user.getDisplayName();

                                    usersRef.child(uid).child("userName").setValue(userName);
                                }
                            }
                        });

            }
        });

        load_bottom_bar();

        Handler handler = new Handler();
        int delay = 200; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){

                update_levelCard();
                update_goals();

                System.out.println("ui updated");
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void get_scores() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        DatabaseReference currentUserRef = usersRef.child(uid);

        currentUserRef.child("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    userScore = dataSnapshot.getValue(Integer.class);

                    currentLevelProgressScore = userScore % 100;
                    userLevel = (userScore - currentLevelProgressScore) / 100;
                    System.out.println("Scores updated");
                }
                else {
                    userScore = 0;
                    userLevel = 0;
                    currentLevelProgressScore = 0;
                    System.out.println("No score found");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                myToast.setText("Error: failed to read database");
                myToast.show();
            }
        });

        currentUserRef.child("endlessMax").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    endlessMax = dataSnapshot.getValue(Integer.class);
                    System.out.println("endlessMax updated");
            }
                else {
                    endlessMax = 0;
                    System.out.println("No endlessMax found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                myToast.setText("Error: failed to read database");
                myToast.show();
            }
        });

        currentUserRef.child("perfectN5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    perfectN5 = dataSnapshot.getValue(Boolean.class);
                }
                else {
                    perfectN5 = false;
                    System.out.println("No perfectN5 found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                myToast.setText("Error: failed to read database");
                myToast.show();
            }
        });

        currentUserRef.child("perfectN4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    perfectN4 = dataSnapshot.getValue(Boolean.class);
                }
                else {
                    perfectN4 = false;
                    System.out.println("No perfectN4 found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                myToast.setText("Error: failed to read database");
                myToast.show();
            }
        });
    }

    public void update_levelCard() {
        TextView levelText = findViewById(R.id.levelText);
        ProgressBar levelBar = findViewById(R.id.levelProgressBar);

        System.out.println(userLevel);
        System.out.println(currentLevelProgressScore);

        levelText.setText("Level " + Integer.toString(userLevel));
        levelBar.setProgress(currentLevelProgressScore, true);
    }

    public void update_goals() {

        CheckBox n5Box = findViewById(R.id.N5checkBox);
        CheckBox n4Box = findViewById(R.id.N4checkBox);
        CheckBox endlessBox = findViewById(R.id.endlessCheckBox);

        if (endlessMax >= 15) {
            endlessBox.setChecked(true);
        }

        if (perfectN5 == true) {
            n5Box.setChecked(true);
        }

        if (perfectN4 == true) {
            n4Box.setChecked(true);
        }

    }


    public void go_to_questions (View v) {

        Intent levelChoiceIntent = new Intent(ProfileActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);

    }

    public void load_bottom_bar () {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(0).setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case(R.id.ic_profile):
                        //Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                        //startActivity(intent);
                        break;

                    case(R.id.ic_friends):
                        Intent intent2 = new Intent(ProfileActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case(R.id.ic_leaderboard):
                        Intent intent3 = new Intent(ProfileActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case(R.id.ic_settings):
                        Intent intent4 = new Intent(ProfileActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });
    }

    public void dark_mode() {
        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        if (Global.darkMode == true) {
            mainLayout.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            nameViewText = findViewById(R.id.nameView);
            nameViewText.setTextColor(getColor(R.color.colorAccent));
        }
        else {
            mainLayout.setBackgroundColor(getColor(R.color.background));
        }
    }

}
