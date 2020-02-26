package com.lmu.learnjava.view_registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.learnjava.controller.Controller;
import com.lmu.learnjava.view_sections.MainActivity;
import com.lmu.learnjava.R;
import com.lmu.learnjava.controller.SharedPrefrencesManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference ref;

    Controller progressController;
    Context context = this;

    EditText userNameInput, userPasswordInput;
    Button logInSubmit;
    TextView toSignUp;

    boolean isUserFirstTime;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressController = (Controller) getApplicationContext();

        if(Boolean.valueOf(SharedPrefrencesManager.readSharedSetting(this, "PERSISTENCE_ENABLED","true"))) {
            //Enable Offline Data Cache -> maye in iniziale database only when app new installed it will work?
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            Log.i("M_LOGIN_ACTIVITY","setPersistence Enabled");
            SharedPrefrencesManager.saveSharedSetting(this, "PERSISTENCE_ENABLED", "false");

        }

        SharedPrefrencesManager.saveSharedSetting(this, "CUE_OPEN","false");

        isUserFirstTime = Boolean.valueOf(SharedPrefrencesManager.readSharedSetting(LogInActivity.this, PREF_USER_FIRST_TIME, "true"));
        Log.i("M_LogIn_ACTIVITY", "isUserforthefirstTime: " + isUserFirstTime);

        if(!isUserFirstTime) {
            SharedPrefrencesManager.setTrigger(this, true, 2);
            Log.i("M_TRIGGER_CUES", "LogInActivity: onCreate, set Cue Trigger true");
        }

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            if(b.get("user_first_time")!= null) {
                Log.i("M_LOGINACITIVITY", "b! = null " + b.get("user_first_time"));
                isUserFirstTime = (boolean) b.get("user_first_time");
            }
        }


        if (isUserFirstTime) {
            signUp();
        }else {
            auth = FirebaseAuth.getInstance();
            ref = FirebaseDatabase.getInstance().getReference();

            // If user is already logged in, get to HomeScreen
            if (auth.getCurrentUser() != null) {
                if(SharedPrefrencesManager.readLogs(this) != null) {
                    progressController.setFirebase();
                    progressController.pushLogs(this);
                }
                Log.i("M_LOGIN_ACTIVITY","currentUSer: "+auth.getCurrentUser().toString());
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
            }
        }



        userNameInput = findViewById(R.id.login_email);
        userPasswordInput = findViewById(R.id.login_password);
        logInSubmit = findViewById(R.id.login_submit);
        toSignUp = findViewById(R.id.login_signup);


        toSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                    intent.putExtra("user_first_time", isUserFirstTime);
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

                    signInUser(email, password);
                }
            });

        }


        public void signUp() {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            intent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);
            startActivity(intent);
            Log.i("M_LOGIN_ACTIVITY", "change to SignUp activity");
        }

        public void signInUser (String email,final String password){
            Log.i("M_LOGIN_ACTIVITY","signInUser method");
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
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
                        progressController.fetchProgressFromFireBase(context);
                        SharedPrefrencesManager.saveSharedSetting(LogInActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
                        Intent userInfo = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(userInfo);
                        finish();
                    }
                }
            });
        }


        @Override
        protected void onStart () {
            super.onStart();
            if(!isUserFirstTime)
                SharedPrefrencesManager.setTrigger(this, true, 2);

        }

    }

