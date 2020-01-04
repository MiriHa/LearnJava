package com.example.learnjava.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.learnjava.Controller;
import com.example.learnjava.MainActivity;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelUserProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends AppCompatActivity {

   Controller myProgressController;
    FirebaseAuth auth;
    DatabaseReference ref;

    EditText userNameInput, userEmailInput, userPasswordInput1, userPasswordInput2;
    ProgressBar signInProgress;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        myProgressController = (Controller) getApplicationContext();

        userEmailInput = findViewById(R.id.signin_email);
        userNameInput = findViewById(R.id.singin_name);
        userPasswordInput1 = findViewById(R.id.signin_password_1);
        userPasswordInput2 = findViewById(R.id.signin_password_2);
        signInProgress = findViewById(R.id.signin_progress);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPasswordInput1.getText().toString().equals(userPasswordInput2.getText().toString())) {
                    Utils.saveSharedSetting(SignUpActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");

                    String userName = userNameInput.getText().toString();
                    String email = userEmailInput.getText().toString();
                    String password = userPasswordInput1.getText().toString();

                    // Check if fields are not empty
                    if (email.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                        Toast.makeText(SignUpActivity.this, getString(R.string.toast_fieldRequired), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Set progress visibility
                    signInProgress.setVisibility(View.VISIBLE);

                    createUser(email, password, userName);
                }
                else {
                    Toast.makeText(SignUpActivity.this, getString(R.string.toast_signin_password), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createUser(final String email, String password, final String username) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Go back to LogIn Screen and upload user information to Realtime Database
                if (task.isSuccessful()) {
                    // Gets newly created user information
                    String userID = auth.getUid();
                    final ModelUserProgress user = new ModelUserProgress(userID, username, email);

                    // Upload user to Realtime Database
                    ref.child("users").child(userID).setValue(user).addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinSuccessful), Toast.LENGTH_LONG).show();
                            //myProgressController.initializeModelUser(user);
                        }
                    }).addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinFailed), Toast.LENGTH_LONG).show();
                        }
                    });

                    //Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinSuccessful), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    // Reset all input fields
                    Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinFailed), Toast.LENGTH_LONG).show();
                    userNameInput.setText("");
                    userEmailInput.setText("");
                    userPasswordInput1.setText("");
                    userPasswordInput2.setText("");
                }
            }
        });
    }





//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.saveSharedSetting(getActivity(), LogInActivity.PREF_USER_FIRST_TIME, "false");
//                Intent introIntent = new Intent(getContext(), LogInActivity.class);
//                startActivity(introIntent);
//            }
//        });
//
//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utils.saveSharedSetting(getActivity(), LogInActivity.PREF_USER_FIRST_TIME, "false");
//                Intent introIntent = new Intent(getContext(), SignUpActivity.class);
//                startActivity(introIntent);
//            }
//        });
    }
