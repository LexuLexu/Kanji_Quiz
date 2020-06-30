package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderboardActivity extends AppCompatActivity {

    private String userName;
    private String userScore;
    private String uid;

    private HashMap<String, Integer> userList;
    private LinkedHashMap<String,Integer> sortedMap;

    private ArrayList<String> sortedUsers;
    private ArrayList<Integer> sortedScores;

    private ArrayList<TextView> sbPosList;
    private ArrayList<TextView> sbNameList;
    private ArrayList<TextView> sbScoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userName = user.getDisplayName();
        uid = user.getUid();

        sortedUsers = new ArrayList<>();
        sortedScores = new ArrayList<>();

        sbPosList = new ArrayList<>();
        sbNameList = new ArrayList<>();
        sbScoreList = new ArrayList<>();

        sortedMap = new LinkedHashMap<>();

        userList = new HashMap<>();
        userList.put("John Sandman", 0);

        gatherTextViews();
        getUsers();

        Button coverButton = findViewById(R.id.leaderButton);

        coverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_leaderboard(v);
            }
        });

        TextView userNameDisplay = findViewById(R.id.userName);
        userNameDisplay.setText(userName);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(3).setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case(R.id.ic_profile):
                        Intent intent = new Intent(LeaderboardActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.ic_friends):
                        Intent intent2 = new Intent(LeaderboardActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case(R.id.ic_leaderboard):
                        //Intent intent3 = new Intent(LeaderboardActivity.this, LeaderboardActivity.class);
                        //startActivity(intent3);
                        break;

                    case(R.id.ic_settings):
                        Intent intent4 = new Intent(LeaderboardActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });

    }

    public void go_to_questions (View view) {

        Intent levelChoiceIntent = new Intent(LeaderboardActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);

    }

    public void getUsers () {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        ValueEventListener scoreListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String nameSnap = snapshot.child("userName").getValue(String.class);
                    Integer scoreSnap = snapshot.child("score").getValue(Integer.class);

                    userList.put(nameSnap,scoreSnap);

                    System.out.println(snapshot.child("userName").getValue(String.class) + " added to Map\n" +
                    snapshot.child("score").getValue(Integer.class) + " added to Map");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("LeaderboardActivity", "EventListener Cancelled");
            }
        };

        usersRef.addValueEventListener(scoreListener);

    }

    public void sortUsers (List<HashMap.Entry<String, Integer>> list) {

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : list) {
            sortedUsers.add((entry.getKey()));
            sortedScores.add((entry.getValue()));

            if (entry.getKey().equals(userName)) {
                TextView userPos = findViewById(R.id.userPos);
                userPos.setText(Integer.toString((sortedUsers.indexOf(userName)+1)));

                TextView userScore = findViewById(R.id.userScore);
                userScore.setText(Integer.toString(entry.getValue()));
            }


        }
    }

    public void outputToLeaderBoard() {

        for (int i = 0; i < sortedUsers.size(); i++) {

            TextView sbPos = sbPosList.get(i);
            TextView sbName = sbNameList.get(i);
            TextView sbScore = sbScoreList.get(i);

            sbPos.setText(String.valueOf(i+1));
            sbName.setText(sortedUsers.get(i));
            sbScore.setText(sortedScores.get(i).toString());

        }

    }

    public void gatherTextViews(){

        TextView sbPos1 = findViewById(R.id.sbPos1);
        TextView sbPos2 = findViewById(R.id.sbPos2);
        TextView sbPos3 = findViewById(R.id.sbPos3);
        TextView sbPos4 = findViewById(R.id.sbPos4);
        TextView sbPos5 = findViewById(R.id.sbPos5);
        TextView sbPos6 = findViewById(R.id.sbPos6);
        TextView sbPos7 = findViewById(R.id.sbPos7);
        TextView sbPos8 = findViewById(R.id.sbPos8);
        TextView sbPos9 = findViewById(R.id.sbPos9);
        TextView sbPos10 = findViewById(R.id.sbPos10);

        sbPosList.add(sbPos1);
        sbPosList.add(sbPos2);
        sbPosList.add(sbPos3);
        sbPosList.add(sbPos4);
        sbPosList.add(sbPos5);
        sbPosList.add(sbPos6);
        sbPosList.add(sbPos7);
        sbPosList.add(sbPos8);
        sbPosList.add(sbPos9);
        sbPosList.add(sbPos10);

        TextView sbName1 = findViewById(R.id.sbUser1);
        TextView sbName2 = findViewById(R.id.sbUser2);
        TextView sbName3 = findViewById(R.id.sbUser3);
        TextView sbName4 = findViewById(R.id.sbUser4);
        TextView sbName5 = findViewById(R.id.sbUser5);
        TextView sbName6 = findViewById(R.id.sbUser6);
        TextView sbName7 = findViewById(R.id.sbUser7);
        TextView sbName8 = findViewById(R.id.sbUser8);
        TextView sbName9 = findViewById(R.id.sbUser9);
        TextView sbName10 = findViewById(R.id.sbUser10);

        sbNameList.add(sbName1);
        sbNameList.add(sbName2);
        sbNameList.add(sbName3);
        sbNameList.add(sbName4);
        sbNameList.add(sbName5);
        sbNameList.add(sbName6);
        sbNameList.add(sbName7);
        sbNameList.add(sbName8);
        sbNameList.add(sbName9);
        sbNameList.add(sbName10);

        TextView sbScore1 = findViewById(R.id.sbScore1);
        TextView sbScore2 = findViewById(R.id.sbScore2);
        TextView sbScore3 = findViewById(R.id.sbScore3);
        TextView sbScore4 = findViewById(R.id.sbScore4);
        TextView sbScore5 = findViewById(R.id.sbScore5);
        TextView sbScore6 = findViewById(R.id.sbScore6);
        TextView sbScore7 = findViewById(R.id.sbScore7);
        TextView sbScore8 = findViewById(R.id.sbScore8);
        TextView sbScore9 = findViewById(R.id.sbScore9);
        TextView sbScore10 = findViewById(R.id.sbScore10);

        sbScoreList.add(sbScore1);
        sbScoreList.add(sbScore2);
        sbScoreList.add(sbScore3);
        sbScoreList.add(sbScore4);
        sbScoreList.add(sbScore5);
        sbScoreList.add(sbScore6);
        sbScoreList.add(sbScore7);
        sbScoreList.add(sbScore8);
        sbScoreList.add(sbScore9);
        sbScoreList.add(sbScore10);

    }

    public void load_leaderboard (View v) {

        System.out.println(userList);

        View leaderboardCover = findViewById(R.id.leaderboardCover);
        Button coverButton = findViewById(R.id.leaderButton);

        leaderboardCover.setVisibility(View.INVISIBLE);
        coverButton.setVisibility(View.INVISIBLE);

        List<HashMap.Entry<String, Integer>> list = new LinkedList<>(userList.entrySet());
        sortUsers(list);
        outputToLeaderBoard();

        for (TextView view : sbPosList) {
            if (view.getText().equals("pos")) {
                view.setText("");
            }
        }
        for (TextView view : sbNameList) {
            if (view.getText().equals("name")) {
                view.setText("");
            }
        }
        for (TextView view : sbScoreList) {
            if (view.getText().equals("score")) {
                view.setText("");
            }
        }
    }
}
