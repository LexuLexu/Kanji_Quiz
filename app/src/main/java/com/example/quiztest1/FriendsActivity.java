package com.example.quiztest1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendsActivity extends AppCompatActivity {

    private String searchRefString;

    private int friendsCount;

    private String friend1Name;
    private int friend1Score;
    private int friend1EndlessMax;
    private int friend1Level;

    private String friend2Name;
    private int friend2Score;
    private int friend2EndlessMax;
    private int friend2Level;

    private String friend3Name;
    private int friend3Score;
    private int friend3EndlessMax;
    private int friend3Level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        dark_mode();

        load_bottom_bar();

        get_friendsCount();
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

    public void dark_mode() {
        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        TextView text1 = findViewById(R.id.friendsText);
        if (Global.darkMode == true) {
            mainLayout.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            text1.setTextColor(getColor(R.color.colorAccent));
        }
        else {
            mainLayout.setBackgroundColor(getColor(R.color.background));
        }
    }

    public void find_user_data(String friendUid) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        searchRefString = "";

        for (int i = 0; i <= friendsCount; i++) {

            searchRefString = "friend" + (i + 1);
            DatabaseReference searchRef = database.getReference("users").child("friends").child("friend" + (i + 1));

            ValueEventListener userDataListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String nameSnap = dataSnapshot.child("userName").getValue(String.class);
                    Integer scoreSnap = dataSnapshot.child("score").getValue(Integer.class);
                    Integer endlessSnap = dataSnapshot.child("endlessMax").getValue(Integer.class);

                    Integer currentLevelProgressScore = scoreSnap % 100;
                    Integer level = (scoreSnap - currentLevelProgressScore) / 100;

                    switch (searchRefString) {
                        case "friend1":
                            friend1Name = nameSnap;
                            friend1Score = scoreSnap;
                            friend1EndlessMax = endlessSnap;
                            friend1Level = level;
                            break;

                        case "friend2":
                            friend2Name = nameSnap;
                            friend2Score = scoreSnap;
                            friend2EndlessMax = endlessSnap;
                            friend2Level = level;
                            break;

                        case "friend3":
                            friend3Name = nameSnap;
                            friend3Score = scoreSnap;
                            friend3EndlessMax = endlessSnap;
                            friend3Level = level;
                            break;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.v("FriendsActivity", "EventListener Cancelled");
                }
            };
            searchRef.addValueEventListener(userDataListener);
        }
    }

    public void addFriendButton (View view) {

        EditText uidInput = findViewById(R.id.SearchInput);
        String friendUid = uidInput.getText().toString();

        add_to_friends_list(friendUid);

    }

    public void add_to_friends_list (String friendUid) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(userUid);

        if (friendsCount < 3) {
            userRef.child("friends").child("friend" + (friendsCount + 1)).setValue(friendUid);
        }
        else {
            Toast.makeText(getApplicationContext(), "You already have the maximum number of friends.", Toast.LENGTH_SHORT).show();
        }

    }

    public void get_friendsCount () {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(userUid);

        userRef.child("friends").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendsCount = (int) dataSnapshot.getChildrenCount();
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.v("FriendsActivity", "EventListener Cancelled");
                friendsCount = 0;
            }
        });
    }

}
