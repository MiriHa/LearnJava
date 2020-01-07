package com.example.learnjava.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.Controller;
import com.example.learnjava.MainActivity;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelUserProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

   Controller myProgressController;
    FirebaseAuth auth;
    DatabaseReference ref;

    EditText userNameInput, userEmailInput, userPasswordInput1, userPasswordInput2;
    ProgressBar signInProgress;
    Button signIn;
    TextView toLogIn;

    Boolean isuserForTheFirstTime;

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
        signIn = findViewById(R.id.signin_submit);
        toLogIn = findViewById(R.id.signUp_login);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null)
            isuserForTheFirstTime = (boolean) b.get("user_first_time");

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

//        final ModelUserProgress user = new ModelUserProgress("PiROHHa635hi1hX0kCVq6y4OBrb2", "test", "t@yahoo.de");
//        ref.child("users").child("PiROHHa635hi1hX0kCVq6y4OBrb2").setValue(user);

        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                intent.putExtra("user_first_time",false);
                startActivity(intent);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = userPasswordInput1.getText().toString();
                String password2 = userPasswordInput2.getText().toString();
                Log.i("M_SIGNUP_SCTIVITY", "pasword1: " + password + "password2: " + password2);
                if(password.length() >= 6 || password2.length() >= 6) {
                    if (password.equals(password2)) {
                        // Utils.saveSharedSetting(SignUpActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
                        Log.i("M_SIGNUP_SCTIVITY", "paswords matching");
                        String userName = userNameInput.getText().toString();
                        String email = userEmailInput.getText().toString();;

                        // Check if fields are not empty
                        if (email.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                            Toast.makeText(SignUpActivity.this, getString(R.string.toast_fieldRequired), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Set progress visibility
                        signInProgress.setVisibility(View.VISIBLE);

                        createUser(email, password, userName);
                    } else {
                        Toast.makeText(SignUpActivity.this, getString(R.string.toast_signin_password), Toast.LENGTH_SHORT).show();
                        Log.i("M_SIGNUP_SCTIVITY", "passwords not matching");
                    }
                }else{
                    Toast.makeText(SignUpActivity.this, getString(R.string.toast_signin_password_short), Toast.LENGTH_SHORT).show();
                    Log.i("M_SIGNUP_SCTIVITY", "password to short");
                }
            }
        });
    }

    public void createUser(final String email, String password, final String username){
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("M_SIGNUP_SCTIVITY","creatUser: success");
                    FirebaseUser userFB = auth.getCurrentUser();
                    saveUserToFirebase(username, email);
                    Intent userInfo = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(userInfo);
                    finish();
                }else{
                    Log.i("M_SIGNUP_SCTIVITY","creatUser: failure");
                    Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinFailed), Toast.LENGTH_LONG).show();
                    signInProgress.setVisibility(View.INVISIBLE);
                    userNameInput.setText("");
                    userEmailInput.setText("");
                    userPasswordInput1.setText("");
                    userPasswordInput2.setText("");
                }
            }
        });
    }

    public void saveUserToFirebase(String username, String email){
        String userId = auth.getCurrentUser().getUid();
        Log.i("M_SIGNUP_ACTIVITY","saveUserToFirebase id: " + userId);
        final ModelUserProgress user = new ModelUserProgress(userId, username, email);
        myProgressController.initializeModelUser(this, user);
        Utils.saveSharedSetting(SignUpActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");

        ref.child("users").child(userId).setValue(user);
//                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinSuccessful), Toast.LENGTH_LONG).show();
//                Utils.saveSharedSetting(SignUpActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
//                Log.i("M_SIGNUP_SCTIVITY","save User to database succesfull");
//            }
//        }).addOnFailureListener(this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinFailed), Toast.LENGTH_LONG).show();
//                signInProgress.setVisibility(View.INVISIBLE);
//                Log.i("M_SIGNUP_SCTIVITY","saveing User failed");
//            }
//        });
    }

//    public void createUser(final String email, String password, final String username) {
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Log.i("M_SIGNUP_SCTIVITY","Create a user");
//                // Go back to LogIn Screen and upload user information to Realtime Database
//                if (task.isSuccessful()) {
//                    // Gets newly created user information
//                    FirebaseUser userFB = auth.getCurrentUser();
//                    String userID = auth.getUid();
//                    final ModelUserProgress user = new ModelUserProgress(userID, username, email);
//
//                    // Upload user to Realtime Database
//                    ref.child("users").child(userID).setValue(user).addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinSuccessful), Toast.LENGTH_LONG).show();
//                            Utils.saveSharedSetting(SignUpActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
//                            Log.i("M_SIGNUP_SCTIVITY","signup succsesfull");
//                        }
//                    }).addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinFailed), Toast.LENGTH_LONG).show();
//                            signInProgress.setVisibility(View.INVISIBLE);
//                            Log.i("M_SIGNUP_SCTIVITY","signup failed");
//                        }
//                    });
//
//                    //Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinSuccessful), Toast.LENGTH_LONG).show();
//                    finish();
//                } else {
//                    // Reset all input fields
//                    Toast.makeText(SignUpActivity.this, getString(R.string.toast_signinFailed), Toast.LENGTH_LONG).show();
//                    Log.i("M_SIGNUP_SCTIVITY","signup failed, reset");
//                    signInProgress.setVisibility(View.INVISIBLE);
//                    userNameInput.setText("");
//                    userEmailInput.setText("");
//                    userPasswordInput1.setText("");
//                    userPasswordInput2.setText("");
//                }
//            }
//        });
//    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
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
