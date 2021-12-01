package com.example.covtrack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WelcomePageActivity extends AppCompatActivity {
    private Button button;

    private FirebaseAuth mAuth;
//    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "EmailLogin";
    EditText loginEmail, loginPassword;
    Button loginBtn;
    TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

//Connecting log in to Patients page
        button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(WelcomePageActivity.this,PatientsPageActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        loginEmail = findViewById(R.id.EmailLoginInput);
        loginPassword = findViewById(R.id.PasswordLoginInput);
        loginBtn = findViewById(R.id.loginButton);
        signUpLink = findViewById(R.id.registerLink);


        loginBtn.setOnClickListener(view -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                loginEmail.setError("Email is required!");
            }

            if (TextUtils.isEmpty(password)){
                loginPassword.setError("Password is required");
            }

//            if (password.length() < 6){
//                loginPassword.setError("Password must be 6 characters long.");
//            }


             mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                 if (task.isSuccessful()){
                     Log.d(TAG, "loginWithEmail:success");
                     Toast.makeText(WelcomePageActivity.this, "Log in successful!", Toast.LENGTH_SHORT).show();
                     FirebaseUser user = mAuth.getCurrentUser();
                     updateUI(user);
                     startActivity(new Intent(getApplicationContext(), PatientsPageActivity.class));
                 } else {
                     //If login fails log the error and send toast to user
                     Log.w(TAG, "LoginWithEmail:failed", task.getException());
                     Toast.makeText(WelcomePageActivity.this, "Log in failed!", Toast.LENGTH_SHORT).show();
                     updateUI(null);
                 }
             });
        });


        signUpLink.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomePageActivity.this, RegisterPageActivity.class);
            startActivity(intent);
        });
    }

    //Validate email
    private Boolean validateEmail(){
        String val = loginEmail.getText().toString();
        String noWhitespace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            loginEmail.setError("Field cannot be empty!");
            return false;
        }
        else if (!val.matches(noWhitespace)) {
            loginEmail.setError("White space not allowed!");
            return false;
        }
        else {
            loginEmail.setError(null);
            return true;
        }
    }

    //Validate password
    private Boolean validatePassword(){
        String val = loginPassword.getText().toString();

        if (val.isEmpty()) {
            loginPassword.setError("Field cannot be empty!");
            return false;
        }
        else {
            loginPassword.setError(null);
            return true;
        }
    }

    //validate login info
    public void loginUser(View view) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }
        else {
            isUser();
        }
    }

    private void isUser() {

        String loginUserEnteredEmail = loginEmail.getText().toString().trim();
        String loginUserEnteredPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query checkloginUser = reference.orderByChild("Email").equalTo(loginUserEnteredPassword);

        checkloginUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String passwordFromDB = snapshot.child(loginUserEnteredEmail).child("Password").getValue(String.class);

                    if (passwordFromDB.equals(loginUserEnteredPassword)) {

                        String fnameFromDB = snapshot.child(loginUserEnteredEmail).child("Fname").getValue(String.class);
                        String lnameFromDB = snapshot.child(loginUserEnteredEmail).child("Lname").getValue(String.class);
                        String phoneFromDB = snapshot.child(loginUserEnteredEmail).child("Phone").getValue(String.class);
                        String emailFromDB = snapshot.child(loginUserEnteredEmail).child("Email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfileChangeRequest.class);

                        //intent.putExtra( name: "Fname",fnameFromDB);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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


