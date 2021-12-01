package com.example.covtrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "EmailLogin";
    EditText regFirstName, regLastName, regEmail, regPhone, regPassword;
    Button regBtn;

    FirebaseDatabase rootNode;
    DatabaseReference recordsDbRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registrationpage);

            regFirstName = findViewById(R.id.editTextTextPersonName);
            regLastName = findViewById(R.id.editTextTextPersonName2);
            regEmail = findViewById(R.id.editTextTextEmailAddress);
            regPhone = findViewById(R.id.editTextPhone);
            regPassword = findViewById(R.id.editTextTextPassword);
            regBtn = findViewById(R.id.registerbtn);

            recordsDbRef = FirebaseDatabase.getInstance().getReference().child("Patient");

            //Save data in Firebase on button click
            regBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertPatientData();
                }
            });

        }

        private void insertPatientData(){

            //Get the values
            String fname = regFirstName.getText().toString();
            String lname = regLastName.getText().toString();
            String email = regEmail.getText().toString();
            String phone = regPhone.getText().toString();
            String password = regPassword.getText().toString();

            UserHelperClass helperClass = new UserHelperClass(fname, lname, email, phone, password);

            recordsDbRef.push().setValue(helperClass);
        }
}
