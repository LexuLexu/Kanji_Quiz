package com.example.quiztest1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    private Toast myToast;

    private int friendsCount;
    private ArrayList<String> friendsList;

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

        initialise_variables();

        dark_mode();

        load_bottom_bar();

        set_onClickListeners();

        get_friendsCount();
        get_friendsList();
        get_friends_data();

        update_friends_ui();


    }

    private void set_onClickListeners() {
        ImageView removeFriend1 = findViewById(R.id.removeFriend1);
        removeFriend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_friend_info(v);
            }
        });
        removeFriend1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove_friend(v);
                return false;
            }
        });
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

    public void get_friends_data() {

        for (String friendId : friendsList) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference friendRef = database.getReference("users").child(friendId);

            friendRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (friend1Name == null) {
                            friend1Name         = snapshot.child("userName").getValue(String.class);
                            friend1Score        = snapshot.child("score").getValue(Integer.class);
                            friend1EndlessMax   = snapshot.child("endlessMax").getValue(Integer.class);
                            friend1Level = (friend1Score - (friend1Score % 100)) / 100;
                        }

                        else if (friend2Name == null) {
                            friend2Name         = snapshot.child("userName").getValue(String.class);
                            friend2Score        = snapshot.child("score").getValue(Integer.class);
                            friend2EndlessMax   = snapshot.child("endlessMax").getValue(Integer.class);
                            friend2Level = (friend2Score - (friend2Score % 100)) / 100;
                        }

                        else if (friend3Name == null) {
                            friend3Name         = snapshot.child("userName").getValue(String.class);
                            friend3Score        = snapshot.child("score").getValue(Integer.class);
                            friend3EndlessMax   = snapshot.child("endlessMax").getValue(Integer.class);
                            friend3Level = (friend3Score - (friend3Score % 100)) / 100;
                        }

                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    myToast.setText("Failed to read database");
                }
            });

        }

    }

    public void addFriendButton (View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        EditText uidInput = findViewById(R.id.SearchInput);
        String friendUid = uidInput.getText().toString();

        if (!friendUid.equals("") && !friendUid.equals(userUid) && friendUid.length() == 28) {
            add_to_friends_list(friendUid);
        }
        else {
            myToast.setText("Please enter a valid user ID");
        }

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
            Toast.makeText(getApplicationContext(), "You already have the maximum number of friends", Toast.LENGTH_SHORT).show();
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

    public void get_friendsList () {

        friendsList = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(userUid).child("friends");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot friendsSnapshot : snapshot.getChildren()) {
                    friendsList.add(friendsSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myToast.setText("Error: failed to read database");
                myToast.show();
            }
        });

    }

    public void update_friends_ui () {

        Handler handler = new Handler();
        int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){

                TextView level1 = findViewById(R.id.friend1Level);
                TextView name1 = findViewById(R.id.friend1Name);
                TextView score1 = findViewById(R.id.friend1Score);
                TextView endless1 = findViewById(R.id.friend1EndlessMax);

                name1.setText("" + friend1Name);
                level1.setText("" + friend1Level);
                score1.setText("" + friend1Score);
                endless1.setText("" + friend1EndlessMax);

                CardView friend1Card = findViewById(R.id.friendCard1);
                TextView noFriends = findViewById(R.id.noFriendsText);

                if (friendsCount < 1) {
                    noFriends.setVisibility(View.VISIBLE);
                    friend1Card.setVisibility(View.INVISIBLE);
                }
                else {
                    noFriends.setVisibility(View.INVISIBLE);
                    friend1Card.setVisibility(View.VISIBLE);
                }

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void initialise_variables() {

        myToast = Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_SHORT);

        friend1Name = "";
        friend1Score = 0;
        friend1EndlessMax = 0;
        friend1Level = 0;

        friend2Name = "";
        friend2Score = 0;
        friend2EndlessMax = 0;
        friend2Level = 0;

        friend3Name = "";
        friend3Score = 0;
        friend3EndlessMax = 0;
        friend3Level = 0;

    }

    public void remove_friend_info (View view) {
        myToast.setText("Hold down the button to remove this friend from your friends list");
        myToast.show();
    }

    public void remove_friend(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users").child(userUid).child("friends");

        ImageView button1 = findViewById(R.id.removeFriend1);
        CardView friend1Card = findViewById(R.id.friendCard1);
        TextView noFriendsText = findViewById(R.id.noFriendsText);

        if (view == button1 && friendsList.size() > 0) {
            userRef.child(friendsList.get(0)).removeValue();
            friendsList.remove(0);
            friendsCount -= 1;

            friend1Card.setVisibility(View.INVISIBLE);
            noFriendsText.setVisibility(View.VISIBLE);

            myToast.setText("Friend removed");
            myToast.show();
        }

        else {
            myToast.setText("An error has occurred. Please try again later.");
            myToast.show();
        }

    }

}
