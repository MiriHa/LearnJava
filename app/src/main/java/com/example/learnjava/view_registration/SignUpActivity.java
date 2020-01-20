package com.example.learnjava.view_registration;

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

import com.example.learnjava.controller.Controller;
import com.example.learnjava.view_sections.MainActivity;
import com.example.learnjava.R;
import com.example.learnjava.controller.SharedPrefrencesManager;
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

    Boolean isUserForTheFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        myProgressController = (Controller) getApplicationContext();

        userEmailInput = findViewById(R.id.login_email);
        userNameInput = findViewById(R.id.login_name);
        userPasswordInput1 = findViewById(R.id.login_password);
        userPasswordInput2 = findViewById(R.id.signin_password_2);
        signInProgress = findViewById(R.id.login_progress);
        signIn = findViewById(R.id.login_submit);
        toLogIn = findViewById(R.id.login_signup);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null)
            isUserForTheFirstTime = (boolean) b.get("user_first_time");

        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

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
                        // SharedPrefrencesManager.saveSharedSetting(SignUpActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
                        Log.i("M_SIGNUP_SCTIVITY", "paswords matching");
                        String userName = userNameInput.getText().toString();
                        String email = userEmailInput.getText().toString();;

                        // Check if fields are not empty
                        if (email.isEmpty() || userName.isEmpty()) {
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

        myProgressController.initializeModelUser(this, username, email);
        SharedPrefrencesManager.saveSharedSetting(SignUpActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");


        ref.child("users").child(userId).child("Username").setValue(username);
        ref.child("users").child(userId).child("Email").setValue(email);

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
    }


    }
