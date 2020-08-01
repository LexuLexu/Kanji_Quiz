package com.example.quiztest1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput, confPasswordInput;
    private String email, password, confPassword;
    private FirebaseAuth mAuth;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        open_profile_if_user();

        registerButton = findViewById(R.id.completeRegistrationButton);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confPasswordInput = findViewById(R.id.confPasswordInput);

        set_register();

    }

    public boolean checkUserDetails() {

        email = emailInput.getText().toString().trim();
        password = passwordInput.getText().toString().trim();
        confPassword = confPasswordInput.getText().toString().trim();

        if (password.equals(confPassword) && password.length() > 6 && !password.equals(email)) {
            return true;
        }
        else {
            Toast.makeText(RegisterActivity.this, "Password must be longer than 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void toggleHidePassword (View view) {

        ImageView eye = findViewById(R.id.passwordHideEye);

        if (passwordInput.getTransformationMethod() != null) {
            passwordInput.setTransformationMethod(null);
            eye.setImageResource(R.drawable.visibility_off_24px);
        }
        else {
            passwordInput.setTransformationMethod(new PasswordTransformationMethod());
            eye.setImageResource(R.drawable.visibility_24px);
        }
    }

    public void toggleHideConfPassword (View view) {

        ImageView eye2 = findViewById(R.id.passwordHideEye2);

        if (confPasswordInput.getTransformationMethod() != null) {
            confPasswordInput.setTransformationMethod(null);
            eye2.setImageResource(R.drawable.visibility_off_24px);
        }
        else {
            confPasswordInput.setTransformationMethod(new PasswordTransformationMethod());
            eye2.setImageResource(R.drawable.visibility_24px);
        }
    }

    public void open_profile_if_user() {
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        }
    }

    public void set_register() {
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkUserDetails()) {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    public void go_to_login (View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

}
