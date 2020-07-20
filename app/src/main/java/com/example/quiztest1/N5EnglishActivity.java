package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static java.util.logging.Logger.global;

public class N5EnglishActivity extends AppCompatActivity {

    public Toast myToast;

    private int questionNumber;

    private String[] kanji_array;
    private String[] words_array;

    private String question_number;
    private String question_word;

    private TextView questionView;

    private int score;
    public int newScore;

    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;

    private Button[] button_array;

    private Button correctButton;

    private ProgressBar scoreBar, scoreBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n5_english);

        myToast = Toast.makeText(N5EnglishActivity.this, "", Toast.LENGTH_SHORT);

        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        TextView question = findViewById(R.id.questionNumber);
        if (Global.darkMode == true) {
            mainLayout.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            question.setTextColor(getColor(R.color.colorAccent));

        }
        else {
            mainLayout.setBackgroundColor(getColor(R.color.background));
        }

        questionNumber = 0;
        score = 0;
        newScore = 0;

        questionView = findViewById(R.id.QuestionView);

        scoreBar = findViewById(R.id.scoreBar);
        scoreBar2 = findViewById(R.id.scoreBar2);

        answer1 = findViewById(R.id.button1);
        answer2 = findViewById(R.id.button2);
        answer3 = findViewById(R.id.button3);
        answer4 = findViewById(R.id.button4);

        button_array = new Button[4];

        button_array[0] = answer1;
        button_array[1] = answer2;
        button_array[2] = answer3;
        button_array[3] = answer4;

        kanji_array = getResources().getStringArray(R.array.N5_Kanji);
        words_array = getResources().getStringArray(R.array.N5_English);

        getnewQuestion(questionView);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2).setChecked(false);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case(R.id.ic_profile):
                        Intent intent = new Intent(N5EnglishActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.ic_friends):
                        Intent intent2 = new Intent(N5EnglishActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case(R.id.ic_leaderboard):
                        Intent intent3 = new Intent(N5EnglishActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case(R.id.ic_settings):
                        Intent intent4 = new Intent(N5EnglishActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });
    }

    public void getnewQuestion (View view) {

        answer_question(view);

        questionNumber++;

        if (questionNumber > 10) {

            CardView scorePromptCard = findViewById(R.id.scorePromptCard);
            scorePromptCard.setVisibility(View.VISIBLE);

            Button okButton = findViewById(R.id.okButton);
            okButton.setVisibility(View.INVISIBLE);

            TextView scorePromptScore = findViewById(R.id.scorePromptScore);
            scorePromptScore.setText(Integer.toString(score));

            answer1.setClickable(false);
            answer2.setClickable(false);
            answer3.setClickable(false);
            answer4.setClickable(false);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersRef = database.getReference("users");
            DatabaseReference currentUserRef = usersRef.child(uid);

            if (score == 100) {
                currentUserRef.child("perfectN5").setValue(true);
            }

            currentUserRef.child("score").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (dataSnapshot.getValue() != null) {
                        int userPrevScore = dataSnapshot.getValue(int.class);
                        newScore = userPrevScore + score;
                    }
                    else {
                        newScore = score;
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    myToast.setText("Error: failed to read database");
                    myToast.show();
                }
            });

            Runnable r = new Runnable() {
                @Override
                public void run(){
                    okButton.setVisibility(View.VISIBLE);
                }
            };

            Handler h = new Handler();
            h.postDelayed(r, 1000);
        }

        else {

            TextView titleText = findViewById(R.id.questionNumber);
            titleText.setText("Question " + questionNumber);

            get_random_button();

            Random rnd = new Random();

            int arrayIndex = rnd.nextInt(kanji_array.length);

            question_number = kanji_array[arrayIndex];

            questionView.setText(question_number);

            correctButton = get_random_button();
            question_word = words_array[arrayIndex];


            for (Button button : button_array) {

                button.setText(words_array[rnd.nextInt(words_array.length)]);

            }

            correctButton.setText(question_word);

            for (Button button : button_array) {

                while (button != correctButton && button.getText() == question_word) {

                    button.setText(words_array[rnd.nextInt(words_array.length)]);

                }

            }
        }

    }

    public Button get_random_button() {

        Random rnd = new Random();

        Button chosenButton = button_array[rnd.nextInt(button_array.length)];

        System.out.println(chosenButton);

        return chosenButton;
    }

    public void answer_question(View v) {

        switch (v.getId()) {
            case R.id.button1:
                if (answer1.getText() == question_word) {
                    correctAnswer();
                }
                else {
                    incorrectAnswer();
                }
                break;

            case R.id.button2:
                if (answer2.getText() == question_word) {
                    correctAnswer();
                }
                else {
                    incorrectAnswer();
                }
                break;

            case R.id.button3:
                if (answer3.getText() == question_word) {
                    correctAnswer();
                }
                else {
                    incorrectAnswer();
                }
                break;

            case R.id.button4:
                if (answer4.getText() == question_word) {
                    correctAnswer();
                }
                else {
                    incorrectAnswer();
                }
                break;

        }

    }

    public void correctAnswer() {
        score+=10;
        updateScoreBar(10);

        if (!Global.soundMuted) {
            final MediaPlayer correctNoise = MediaPlayer.create(this, R.raw.right_answer);
            correctNoise.seekTo(0);
            correctNoise.start();
        }
    }

    public void incorrectAnswer () {
        myToast.setText("Correct answer: " + question_word);
        myToast.show();

        if (!Global.soundMuted) {
            final MediaPlayer incorrectNoise = MediaPlayer.create(this, R.raw.wrong_answer);
            incorrectNoise.seekTo(0);
            incorrectNoise.start();
        }
    }

    public void go_to_questions (View view) {
        Intent levelChoiceIntent = new Intent(N5EnglishActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);
    }

    public void endQuizButton (View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String userName = user.getDisplayName();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        usersRef.child(uid).child("score").setValue(newScore);
        usersRef.child(uid).child("userName").setValue(userName);

        view.setVisibility(View.INVISIBLE);

        Intent levelChoiceIntent = new Intent(N5EnglishActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);

    }

    public void updateScoreBar(int score) {
        int currentScore = scoreBar.getProgress();
        int newScore = currentScore += score;
        scoreBar.setProgress(newScore, true);
        scoreBar2.setProgress(newScore, true);
    }
}
