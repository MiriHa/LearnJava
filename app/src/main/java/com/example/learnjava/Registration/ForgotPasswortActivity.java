package com.example.learnjava.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.learnjava.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswortActivity extends AppCompatActivity {

    FirebaseAuth auth;

    EditText emailInput;
    Button resetPassword;
    ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passwort);

        // Set view
//        emailInput = findViewById(R.id.forgot_password_email);
//        resetPassword = findViewById(R.id.forgot_password_submit);
//        homeButton = findViewById(R.id.forgot_password_login);

        // Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Reset Password
//        resetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                auth.sendPasswordResetEmail(emailInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
////                            Toast.makeText(ForgotPasswordActivity.this, "Password has been successfully sent to your E-mail", Toast.LENGTH_LONG).show();
//                        } else {
////                            Toast.makeText(ForgotPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });

        // Return to LogIn screen
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
//                startActivity(intent);
            }
        });
    }
}
