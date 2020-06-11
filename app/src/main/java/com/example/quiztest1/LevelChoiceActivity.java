package com.example.quiztest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LevelChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_choice);
    }

    public void go_to_questions (View v) {

        //Intent levelChoiceIntent = new Intent(LevelChoiceActivity.this, LevelChoiceActivity.class);
        //startActivity(levelChoiceIntent);

    }

    public void go_to_quiz (View v) {

        startActivity(new Intent (getApplicationContext(), EndlessActivity.class));

    }
}
