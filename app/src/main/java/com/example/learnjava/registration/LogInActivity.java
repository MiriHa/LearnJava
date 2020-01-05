package com.example.learnjava.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.MainActivity;
import com.example.learnjava.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference ref;

    EditText userNameInput, userPasswordInput;
    Button logInSubmit;
    TextView toSignUp;

    ProgressBar logInProgres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameInput = findViewById(R.id.login_email);
        userPasswordInput = findViewById(R.id.login_password);
        logInSubmit = findViewById(R.id.login_submit);
        toSignUp = findViewById(R.id.login_signUp);

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        // If user is already logged in, get to HomeScreen
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
        }

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        logInSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if email field is empty
                String email = userNameInput.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), R.string.toast_fieldRequired, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if password field is empty
                final String password = userPasswordInput.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), R.string.toast_fieldRequired, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Set progress visibility
                //logInProgres.setVisibility(View.VISIBLE);

                signInUser(email, password);
            }
        });


    }

    public void signInUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             //   logInProgress.setVisibility(View.INVISIBLE);
                // Handle LogIn failure
                if (!task.isSuccessful()) {
                    // Check if password was too short
                    if (password.length() < 6) {
                        userPasswordInput.setError(getString(R.string.error_wrongPassword));
                    } else {
                        Toast.makeText(LogInActivity.this, getString(R.string.toast_loginFailed), Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Handle LogIn success
                    Intent userInfo = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(userInfo);
                    finish();
                }
            }
        });
    }

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }

}
