package com.example.covtrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientsPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button addPatientBtn;

    FirebaseDatabase rootNode;
    DatabaseReference recordsDbRef;
    private Button button;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_patientspage);

            addPatientBtn = findViewById(R.id.addpatientbtn);

            //Connecting view patient to add patients page
            button = (Button) findViewById(R.id.loginButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(PatientsPageActivity.this,AddPatientActivity.class);


                    Log.e("Testing", "Got here");
                    startActivity(intent);
                }
            });
        }
}
