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

/**
 * The type Profile activity.
 */
public class ProfileActivity extends AppCompatActivity {

    public Toast myToast;

    protected EditText nameViewText;

    private int userScore;
    private int userLevel;
    private int currentLevelProgressScore;
    private int endlessMax;

    private boolean perfectN5;
    private boolean perfectN4;
    private boolean scored50N5;
    private boolean scored50N4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myToast = Toast.makeText(ProfileActivity.this, "", Toast.LENGTH_SHORT);

        initialise_variables();

        get_scores();

        dark_mode();

        get_userName();
        get_uid();

        load_bottom_bar();

        update_ui();
    }

    /**
     * Gets scores.
     */
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

        currentUserRef.child("scored50N5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    scored50N5 = dataSnapshot.getValue(Boolean.class);
                }
                else {
                    scored50N5 = false;
                    System.out.println("No scored50N5 found");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                myToast.setText("Error: failed to read database");
                myToast.show();
            }
        });

        currentUserRef.child("scored50N4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    scored50N4 = dataSnapshot.getValue(Boolean.class);
                }
                else {
                    scored50N4 = false;
                    System.out.println("No scored50N4 found");
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

    /**
     * Update level card.
     */
    public void update_levelCard() {
        TextView levelText = findViewById(R.id.levelText);
        ProgressBar levelBar = findViewById(R.id.levelProgressBar);

        levelText.setText("Level " + Integer.toString(userLevel));
        levelBar.setProgress(currentLevelProgressScore, true);
    }

    /**
     * Update goals.
     */
    public void update_goals() {

        CheckBox halfN5Box = findViewById(R.id.N5checkBox);
        CheckBox halfN4Box = findViewById(R.id.N4checkBox);
        CheckBox n5Box = findViewById(R.id.N5checkBoxGoal2);
        CheckBox n4Box = findViewById(R.id.N4checkBoxGoal2);
        CheckBox endlessBox15 = findViewById(R.id.endlessCheckBox);
        CheckBox endlessBox30 = findViewById(R.id.endlessCheckBoxGoal2);

        if (endlessMax >= 15) {
            endlessBox15.setChecked(true);
        }

        if (endlessMax >= 30) {
            endlessBox30.setChecked(true);
        }

        if (perfectN5 == true) {
            n5Box.setChecked(true);
        }

        if (perfectN4 == true) {
            n4Box.setChecked(true);
        }

        if (scored50N5 == true) {
            halfN5Box.setChecked(true);
        }

        if (scored50N4 == true) {
            halfN4Box.setChecked(true);
        }

    }


    /**
     * Go to questions.
     *
     * @param v the v
     */
    public void go_to_questions (View v) {

        Intent levelChoiceIntent = new Intent(ProfileActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);

    }

    /**
     * Load bottom bar.
     */
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

    /**
     * Dark mode.
     */
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

    /**
     * Initialise variables.
     */
    public void initialise_variables() {
        userScore = 0;
        userLevel = 0;
        currentLevelProgressScore = 0;
        endlessMax = 0;

        perfectN5 = false;
        perfectN4 = false;
    }

    /**
     * Gets user name.
     */
    public void get_userName() {

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
    }

    /**
     * Update ui.
     */
    public void update_ui() {
        Handler handler = new Handler();
        int delay = 200; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){

                update_levelCard();
                update_goals();

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    /**
     * Get uid.
     */
    public void get_uid(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String uid = user.getUid();
            TextView uidText = findViewById(R.id.uidText);
            uidText.setText("User ID:\n" + uid);
        }

    }

}
