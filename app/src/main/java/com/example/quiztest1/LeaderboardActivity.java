package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

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
import com.google.android.material.chip.Chip;
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

    private List<HashMap.Entry<String, Integer>> list;

    private HashMap<String, Integer> userList;
    private HashMap<String, Integer> endlessUserList;

    private ArrayList<String> sortedUsers;
    private ArrayList<Integer> sortedScores;

    private ArrayList<String> sortedEndlessUsers;
    private ArrayList<Integer> sortedEndlessScores;

    private ArrayList<TextView> sbPosList;
    private ArrayList<TextView> sbNameList;
    private ArrayList<TextView> sbScoreList;

    private ArrayList<TextView> endPosList;
    private ArrayList<TextView> endNameList;
    private ArrayList<TextView> endScoreList;

    private String userPosNum;
    private String userScoreNum;
    private String endlessUserPosNum;
    private String endlessUserScoreNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        TextView text1 = findViewById(R.id.leaderboardText);
        if (Global.darkMode == true) {
            mainLayout.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            text1.setTextColor(getColor(R.color.colorAccent));
            View coverView = findViewById(R.id.leaderboardCover);
            coverView.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            coverView.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimaryDark));
        }
        else {
            mainLayout.setBackgroundColor(getColor(R.color.background));
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userName = user.getDisplayName();
        uid = user.getUid();

        sortedUsers = new ArrayList<>();
        sortedScores = new ArrayList<>();

        sortedEndlessUsers = new ArrayList<>();
        sortedEndlessScores = new ArrayList<>();

        sbPosList = new ArrayList<>();
        sbNameList = new ArrayList<>();
        sbScoreList = new ArrayList<>();

        endPosList = new ArrayList<>();
        endNameList = new ArrayList<>();
        endScoreList = new ArrayList<>();

        userList = new HashMap<>();
        endlessUserList = new HashMap<>();

        create_leaderboards();

        Button coverButton = findViewById(R.id.leaderButton);
        coverButton.setVisibility(View.VISIBLE);
        coverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_leaderboards(v);
                CardView endlessCard = findViewById(R.id.endlessCard);
                endlessCard.setVisibility(View.INVISIBLE);
                coverButton.setVisibility(View.INVISIBLE);
            }
        });


        Chip totalChip = findViewById(R.id.totalChip);
        Chip endlessChip = findViewById(R.id.endlessChip);

        totalChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View leaderboardCover = findViewById(R.id.leaderboardCover);

                if (leaderboardCover.getVisibility() == View.INVISIBLE) {

                    CardView totalCard = findViewById(R.id.leaderboardCard);
                    CardView endlessCard = findViewById(R.id.endlessCard);
                    totalCard.setVisibility(View.VISIBLE);
                    endlessCard.setVisibility(View.INVISIBLE);

                    TextView userPos = findViewById(R.id.userPos);
                    userPos.setText(userPosNum);
                    TextView userScore = findViewById(R.id.userScore);
                    userScore.setText(userScoreNum);

                    System.out.println("Switching to total view");

                    if (totalCard.getVisibility() == View.VISIBLE) {
                        System.out.println("totalCard is visible");
                    }
                    if (endlessCard.getVisibility() == View.VISIBLE) {
                        System.out.println("endlessCard is visible");
                    }
                }
            }
        });

        endlessChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View leaderboardCover = findViewById(R.id.leaderboardCover);

                if (leaderboardCover.getVisibility() == View.INVISIBLE) {

                    CardView totalCard = findViewById(R.id.leaderboardCard);
                    CardView endlessCard = findViewById(R.id.endlessCard);
                    totalCard.setVisibility(View.INVISIBLE);
                    endlessCard.setVisibility(View.VISIBLE);

                    TextView userPos = findViewById(R.id.userPos);
                    userPos.setText(endlessUserPosNum);
                    TextView userScore = findViewById(R.id.userScore);
                    userScore.setText(endlessUserScoreNum);

                    System.out.println("Switching to endless view");

                    if (totalCard.getVisibility() == View.VISIBLE) {
                        System.out.println("totalCard is visible");
                    }
                    if (endlessCard.getVisibility() == View.VISIBLE) {
                        System.out.println("endlessCard is visible");
                    }
                }
            }
        });


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

    public void create_leaderboards() {

        gatherTextViews();
        gatherEndlessTextViews();

        getUsers();

        TextView userNameDisplay = findViewById(R.id.userName);
        userNameDisplay.setText(userName);
    }

    public void getUsers () {

        userList.put("Jon Sandman", 0);
        endlessUserList.put("Jon Sandman", 0);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        ValueEventListener scoreListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String nameSnap = snapshot.child("userName").getValue(String.class);
                    Integer scoreSnap = snapshot.child("score").getValue(Integer.class);
                    Integer endlessSnap = snapshot.child("endlessMax").getValue(Integer.class);

                    if (nameSnap == null) {
                        nameSnap = "";
                    }
                    if (scoreSnap == null) {
                        scoreSnap = 0;
                    }
                    if (endlessSnap == null) {
                        endlessSnap = 0;
                    }

                    userList.put(nameSnap, scoreSnap);
                    endlessUserList.put(nameSnap, endlessSnap);

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
                userPosNum = Integer.toString(sortedUsers.indexOf(userName)+1);
                TextView userPos = findViewById(R.id.userPos);
                userPos.setText(userPosNum);

                userScoreNum = Integer.toString(entry.getValue());
                TextView userScore = findViewById(R.id.userScore);
                userScore.setText(userScoreNum);
            }
        }
    }

    public void sortEndlessUsers (List<HashMap.Entry<String, Integer>> list) {

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : list) {
            sortedEndlessUsers.add((entry.getKey()));
            sortedEndlessScores.add((entry.getValue()));

            if (entry.getKey().equals(userName)) {
                endlessUserPosNum = Integer.toString(sortedEndlessUsers.indexOf(userName)+1);
                endlessUserScoreNum = Integer.toString(entry.getValue());
            }
        }
    }

    public void clearLeaderBoard() {

        for (int i = 0; i < 10; i++) {

            TextView sbPos = sbPosList.get(i);
            TextView sbName = sbNameList.get(i);
            TextView sbScore = sbScoreList.get(i);

            sbPos.setText("");
            sbName.setText("");
            sbScore.setText("");
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

    public void outputToEndlessBoard() {

        for (int i = 0; i < sortedEndlessUsers.size(); i++) {

            TextView endPos = endPosList.get(i);
            TextView endName = endNameList.get(i);
            TextView endScore = endScoreList.get(i);

            endPos.setText(String.valueOf(i+1));
            endName.setText(sortedEndlessUsers.get(i));
            endScore.setText(sortedEndlessScores.get(i).toString());
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

    public void gatherEndlessTextViews(){

        TextView endPos1 = findViewById(R.id.endPos1);
        TextView endPos2 = findViewById(R.id.endPos2);
        TextView endPos3 = findViewById(R.id.endPos3);
        TextView endPos4 = findViewById(R.id.endPos4);
        TextView endPos5 = findViewById(R.id.endPos5);
        TextView endPos6 = findViewById(R.id.endPos6);
        TextView endPos7 = findViewById(R.id.endPos7);
        TextView endPos8 = findViewById(R.id.endPos8);
        TextView endPos9 = findViewById(R.id.endPos9);
        TextView endPos10 = findViewById(R.id.endPos10);

        endPosList.add(endPos1);
        endPosList.add(endPos2);
        endPosList.add(endPos3);
        endPosList.add(endPos4);
        endPosList.add(endPos5);
        endPosList.add(endPos6);
        endPosList.add(endPos7);
        endPosList.add(endPos8);
        endPosList.add(endPos9);
        endPosList.add(endPos10);

        TextView endName1 = findViewById(R.id.endUser1);
        TextView endName2 = findViewById(R.id.endUser2);
        TextView endName3 = findViewById(R.id.endUser3);
        TextView endName4 = findViewById(R.id.endUser4);
        TextView endName5 = findViewById(R.id.endUser5);
        TextView endName6 = findViewById(R.id.endUser6);
        TextView endName7 = findViewById(R.id.endUser7);
        TextView endName8 = findViewById(R.id.endUser8);
        TextView endName9 = findViewById(R.id.endUser9);
        TextView endName10 = findViewById(R.id.endUser10);

        endNameList.add(endName1);
        endNameList.add(endName2);
        endNameList.add(endName3);
        endNameList.add(endName4);
        endNameList.add(endName5);
        endNameList.add(endName6);
        endNameList.add(endName7);
        endNameList.add(endName8);
        endNameList.add(endName9);
        endNameList.add(endName10);

        TextView endScore1 = findViewById(R.id.endScore1);
        TextView endScore2 = findViewById(R.id.endScore2);
        TextView endScore3 = findViewById(R.id.endScore3);
        TextView endScore4 = findViewById(R.id.endScore4);
        TextView endScore5 = findViewById(R.id.endScore5);
        TextView endScore6 = findViewById(R.id.endScore6);
        TextView endScore7 = findViewById(R.id.endScore7);
        TextView endScore8 = findViewById(R.id.endScore8);
        TextView endScore9 = findViewById(R.id.endScore9);
        TextView endScore10 = findViewById(R.id.endScore10);

        endScoreList.add(endScore1);
        endScoreList.add(endScore2);
        endScoreList.add(endScore3);
        endScoreList.add(endScore4);
        endScoreList.add(endScore5);
        endScoreList.add(endScore6);
        endScoreList.add(endScore7);
        endScoreList.add(endScore8);
        endScoreList.add(endScore9);
        endScoreList.add(endScore10);

    }



    public void load_leaderboards (View v) {

        System.out.println(userList);
        System.out.println(endlessUserList);

        View leaderboardCover = findViewById(R.id.leaderboardCover);
        leaderboardCover.setVisibility(View.INVISIBLE);

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
        List<HashMap.Entry<String, Integer>> endlessList = new LinkedList<>(endlessUserList.entrySet());
        sortEndlessUsers(endlessList);
        outputToEndlessBoard();

        for (TextView view : endPosList) {
            if (view.getText().equals("pos")) {
                view.setText("");
            }
        }
        for (TextView view : endNameList) {
            if (view.getText().equals("name")) {
                view.setText("");
            }
        }
        for (TextView view : endScoreList) {
            if (view.getText().equals("score")) {
                view.setText("");
            }
        }
    }
}
