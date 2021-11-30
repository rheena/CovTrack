package com.example.covtrack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomePageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "EmailLogin";
    EditText loginEmail, loginPassword;
    Button loginBtn;
    TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        mAuth = FirebaseAuth.getInstance();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        loginEmail = findViewById(R.id.EmailLoginInput);
        loginPassword = findViewById(R.id.PasswordLoginInput);
        loginBtn = findViewById(R.id.loginButton);
        signUpLink = findViewById(R.id.registerLink);


        loginBtn.setOnClickListener(view -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginBtn.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                loginEmail.setError("Email is required!");
            }

            if (TextUtils.isEmpty(password)){
                loginPassword.setError("Password is required");
            }

//            if (password.length() < 6){
//                loginPassword.setError("Password must be 6 characters long.");
//            }


             mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         Log.d(TAG, "loginWithEmail:success");
                         Toast.makeText(WelcomePageActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                         FirebaseUser user = mAuth.getCurrentUser();
                         updateUI(user);
                     } else {
                         //If login fails log the error and send toast to user
                         Log.w(TAG, "LoginWithEmail:failed", task.getException());
                         Toast.makeText(WelcomePageActivity.this, "Log in failed!", Toast.LENGTH_SHORT).show();
                         updateUI(null);
                     }
                 }
             });
        });


        signUpLink.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomePageActivity.this, RegisterPageActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    private void updateUI(FirebaseUser user) {

    }
}


