package com.eshopiee.project.mainproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RetrivedData extends AppCompatActivity {

    TextView lattitude,longitude,name;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrived_data);

        lattitude = (TextView)findViewById(R.id.lattitude);
        longitude = (TextView) findViewById(R.id.longitude);
        name = (TextView)findViewById(R.id.Name);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("Location").push().getKey();

        reference = FirebaseDatabase.getInstance().getReference().child("Location").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void  onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Lattitude = dataSnapshot.child("latitude").getValue().toString();
                String Longitude = dataSnapshot.child("longitude").getValue().toString();
                String Name = dataSnapshot.child("name").getValue().toString();

                lattitude.setText(Lattitude);
                longitude.setText(Longitude);
                name.setText(Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
