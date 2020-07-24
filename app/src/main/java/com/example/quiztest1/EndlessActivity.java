package com.example.quiztest1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.Random;

public class EndlessActivity extends AppCompatActivity {

    public Toast myToast;

    private int questionNumber;

    private int lives;

    private int userPrevScore;

    private String[] kanji_array;
    private String[] answer_array;

    private String[] N5_kanji_array;
    private String[] N5_words_array;
    private String[] N5_kana_array;
    private String[] N4_kanji_array;
    private String[] N4_words_array;
    private String[] N4_kana_array;

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
        setContentView(R.layout.activity_endless);

        myToast = Toast.makeText(EndlessActivity.this, "", Toast.LENGTH_SHORT);

        dark_mode();

        lives = 1;

        questionNumber = 0;
        score = 0;

        userPrevScore = 0;

        questionView = findViewById(R.id.QuestionView);

        answer1 = findViewById(R.id.button1);
        answer2 = findViewById(R.id.button2);
        answer3 = findViewById(R.id.button3);
        answer4 = findViewById(R.id.button4);

        button_array = new Button[4];

        button_array[0] = answer1;
        button_array[1] = answer2;
        button_array[2] = answer3;
        button_array[3] = answer4;

        N5_kanji_array = getResources().getStringArray(R.array.N5_Kanji);
        N5_words_array = getResources().getStringArray(R.array.N5_English);
        N5_kana_array = getResources().getStringArray(R.array.N5_Kana);

        N4_kanji_array = getResources().getStringArray(R.array.N4_Kanji);
        N4_words_array = getResources().getStringArray(R.array.N4_English);
        N4_kana_array = getResources().getStringArray(R.array.N4_Kana);

        getnewQuestion(questionView);

        load_bottom_bar();
    }

    public void getnewQuestion (View view) {

        answer_question(view);

        questionNumber++;

        if (lives <= 0) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersRef = database.getReference("users");

            if (score > userPrevScore) {
                usersRef.child(uid).child("endlessMax").setValue(score);
                System.out.println("Database updated");
            }

            else { System.out.println("score = " + score + "\nuserPrevScore = " + userPrevScore); }

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

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersRef = database.getReference("users");
            DatabaseReference currentUserRef = usersRef.child(uid);

            currentUserRef.child("endlessMax").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (dataSnapshot.getValue() != null) {
                        int databaseScore = dataSnapshot.getValue(int.class);
                        userPrevScore = databaseScore;
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    myToast.setText("Error: failed to read database");
                    myToast.show();
                }
            });

            TextView titleText = findViewById(R.id.questionNumber);
            titleText.setText("Question " + questionNumber);

            get_random_button();

            Random rnd = new Random();

            int kanjiLevel = rnd.nextInt(2);
            int words_kana = rnd.nextInt(2);

            // 0 = N5 , 1 = N4

            if (kanjiLevel == 0) {
                kanji_array = N5_kanji_array;
                if (words_kana == 0) {
                    answer_array = N5_words_array;
                }
                else { answer_array = N5_kana_array; }
            }

            else {
                kanji_array = N4_kanji_array;
                if (words_kana == 0) {
                    answer_array = N4_words_array;
                }
                else { answer_array = N4_kana_array; }
            }



            int arrayIndex = rnd.nextInt(kanji_array.length);

            question_number = kanji_array[arrayIndex];

            questionView.setText(question_number);

            correctButton = get_random_button();
            question_word = answer_array[arrayIndex];


            for (Button button : button_array) {

                button.setText(answer_array[rnd.nextInt(answer_array.length)]);

            }

            correctButton.setText(question_word);

            for (Button button : button_array) {

                while (button != correctButton && button.getText() == question_word) {

                    button.setText(answer_array[rnd.nextInt(answer_array.length)]);

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
        score+=1;

        if (!Global.soundMuted) {
            final MediaPlayer correctNoise = MediaPlayer.create(this, R.raw.right_answer);
            correctNoise.seekTo(0);
            correctNoise.start();
        }
    }

    public void incorrectAnswer () {
        myToast.setText("Correct answer: " + question_word);
        myToast.show();

        lives-= 1;

        ImageView heart = findViewById(R.id.lives_heart_1);
        heart.setImageResource(R.drawable.favorite_border_24px);

        if (!Global.soundMuted) {
            final MediaPlayer incorrectNoise = MediaPlayer.create(this, R.raw.wrong_answer);
            incorrectNoise.seekTo(0);
            incorrectNoise.start();
        }
    }

    public void go_to_questions (View view) {
        Intent levelChoiceIntent = new Intent(EndlessActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);
    }

    public void endQuizButton (View view) {

        view.setVisibility(View.INVISIBLE);

        Intent levelChoiceIntent = new Intent(EndlessActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);
    }

    public void load_bottom_bar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.getItem(2).setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case (R.id.ic_profile):
                        Intent intent = new Intent(EndlessActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case (R.id.ic_friends):
                        Intent intent2 = new Intent(EndlessActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case (R.id.ic_leaderboard):
                        Intent intent3 = new Intent(EndlessActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case (R.id.ic_settings):
                        Intent intent4 = new Intent(EndlessActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });
    }

    public void dark_mode() {
        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        TextView question = findViewById(R.id.questionNumber);
        if (Global.darkMode == true) {
            mainLayout.setBackgroundColor(getColor(R.color.colorPrimaryDark));
            question.setTextColor(getColor(R.color.colorAccent));
        }
        else {
            mainLayout.setBackgroundColor(getColor(R.color.background));
        }
    }
}
