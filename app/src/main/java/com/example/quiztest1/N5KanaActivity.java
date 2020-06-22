package com.example.quiztest1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;


public class N5KanaActivity extends AppCompatActivity {

    private String[] kanji_array;
    private String[] kana_array;

    private String question_number;
    private String question_word;

    private TextView questionView;

    private TextView scoreNumber;
    private int score;

    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;

    private Button[] button_array;

    private Button correctButton;

    private ProgressBar scoreBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n5_english);

        questionView = findViewById(R.id.QuestionView);

        scoreNumber = findViewById(R.id.ScoreNumber);
        scoreBar = findViewById(R.id.scoreBar);

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
        kana_array = getResources().getStringArray(R.array.N5_Kana);
        getnewQuestion(questionView);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2).setChecked(false);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case(R.id.ic_profile):
                        Intent intent = new Intent(N5KanaActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.ic_friends):
                        Intent intent2 = new Intent(N5KanaActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case(R.id.ic_leaderboard):
                        Intent intent3 = new Intent(N5KanaActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case(R.id.ic_settings):
                        Intent intent4 = new Intent(N5KanaActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;

            }

        });
    }

    public void getnewQuestion (View view) {

        answer_question(view);

        get_random_button();

        Random rnd = new Random();

        int arrayIndex = rnd.nextInt(kanji_array.length);

        question_number = kanji_array[arrayIndex];

        questionView.setText(question_number);

        correctButton = get_random_button();
        question_word = kana_array[arrayIndex];


        for (Button button : button_array) {

            button.setText(kana_array[rnd.nextInt(kana_array.length)]);

        }

        correctButton.setText(question_word);

        for (Button button : button_array) {

            while (button != correctButton && button.getText() == question_word) {

                button.setText(kana_array[rnd.nextInt(kana_array.length)]);

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
        scoreNumber.setTextColor(this.getResources().getColor(R.color.correct));
        score++;
        scoreNumber.setText(Integer.toString(score));
        updateScoreBar(10);
    }

    public void incorrectAnswer () {
        scoreNumber.setTextColor(this.getResources().getColor(R.color.incorrect));
        Toast.makeText(N5KanaActivity.this, "Correct answer: " + question_word, Toast.LENGTH_SHORT).show();
    }

    public void go_to_questions (View view) {
        Intent levelChoiceIntent = new Intent(N5KanaActivity.this, LevelChoiceActivity.class);
        startActivity(levelChoiceIntent);
    }

    public void updateScoreBar(int score) {
        int currentScore = scoreBar.getProgress();
        scoreBar.setProgress(currentScore += score, true);
    }
}
