package com.example.quiztest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void logIn (View view) {
        startActivity (new Intent (LoginActivity.this, ProfileActivity.class));
    }

    public void registerButton (View view) {
        startActivity (new Intent (LoginActivity.this, RegisterActivity.class));
    }
}
