package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;


public class EndlessActivity extends AppCompatActivity {

    private String[] numbers_array;
    private String[] words_array;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endless);

        questionView = findViewById(R.id.QuestionView);

        scoreNumber = findViewById(R.id.ScoreNumber);

        answer1 = findViewById(R.id.button1);
        answer2 = findViewById(R.id.button2);
        answer3 = findViewById(R.id.button3);
        answer4 = findViewById(R.id.button4);

        button_array = new Button[4];

        button_array[0] = answer1;
        button_array[1] = answer2;
        button_array[2] = answer3;
        button_array[3] = answer4;

        numbers_array = getResources().getStringArray(R.array.Numbers);
        words_array = getResources().getStringArray(R.array.Words);
        getnewQuestion(questionView);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2).setChecked(false);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case(R.id.ic_profile):
                        Intent intent = new Intent(EndlessActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;

                    case(R.id.ic_friends):
                        Intent intent2 = new Intent(EndlessActivity.this, FriendsActivity.class);
                        startActivity(intent2);
                        break;

                    case(R.id.ic_leaderboard):
                        Intent intent3 = new Intent(EndlessActivity.this, LeaderboardActivity.class);
                        startActivity(intent3);
                        break;

                    case(R.id.ic_settings):
                        Intent intent4 = new Intent(EndlessActivity.this, SettingsActivity.class);
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

        int arrayIndex = rnd.nextInt(numbers_array.length);

        question_number = numbers_array[arrayIndex];

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
    }

    public void incorrectAnswer () {
        scoreNumber.setTextColor(this.getResources().getColor(R.color.incorrect));
    }

    /*public void go_to_questions (View view) {

        Intent questionsIntent = new Intent(EndlessActivity.this, EndlessActivity.class);
        startActivity(questionsIntent);

    } */
}
