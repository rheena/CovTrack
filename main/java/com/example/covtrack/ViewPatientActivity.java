package com.example.covtrack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class ViewPatientActivity extends AppCompatActivity {

        RecyclerView recyclerView;
        DatabaseReference reference;
        MyAdapter myAdapter;
        ArrayList<PatientData> list;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_viewpatient);

            recyclerView = findViewById(R.id.patientlist);
            reference = FirebaseDatabase.getInstance().getReference();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            list = new ArrayList<>();
            myAdapter = new MyAdapter(this, list);
            recyclerView.setAdapter(myAdapter);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        PatientData user = dataSnapshot.getValue(PatientData.class);
                        list.add(user);
                    }
                    myAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
