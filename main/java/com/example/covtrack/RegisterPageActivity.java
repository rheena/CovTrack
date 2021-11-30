package com.example.covtrack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterPageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailLogin";



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registrationpage);
        }
}
