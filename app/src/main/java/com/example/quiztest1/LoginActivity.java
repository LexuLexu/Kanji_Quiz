package com.example.quiztest1;

import androidx.annotation.DrawableRes;
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

/**
 * The type Login activity.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInput, passwordInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent (getApplicationContext(), ProfileActivity.class));
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Email or password required", Toast.LENGTH_SHORT).show();
                }

                else {

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }

        });
    }

    /**
     * Register button.
     *
     * @param view the view
     */
    public void registerButton (View view) {
        startActivity (new Intent (LoginActivity.this, RegisterActivity.class));
    }

    /**
     * Toggle hide password.
     *
     * @param view the view
     */
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

    /**
     * Reset password.
     *
     * @param view the view
     */
    public void resetPassword (View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText emailInput = findViewById(R.id.emailInput);
        String email = emailInput.getText().toString();

        if (!email.equals("")) {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("LoginActivity", "Password reset email sent.");
                                Toast.makeText(LoginActivity.this, "Password reset email sent", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
        else {
            Toast.makeText(LoginActivity.this, "Please enter your email address", Toast.LENGTH_LONG).show();
        }
    }


}