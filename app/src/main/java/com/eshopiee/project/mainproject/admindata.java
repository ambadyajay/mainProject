package com.eshopiee.project.mainproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admindata extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private Button btnsave,shwbtn;
    private EditText Latitude;
    private  EditText Longitude;
    private  EditText Name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_admindata);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Location");
        Name = (EditText)findViewById(R.id.name);
        Latitude = (EditText)findViewById(R.id.Latitude);
        Longitude = (EditText)findViewById(R.id.Longitude);
        btnsave = (Button)findViewById(R.id.btnsave);
        shwbtn = (Button)findViewById(R.id.shw_btn);
        btnsave.setOnClickListener(this);

        shwbtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
              //  startActivity(new Intent(admindata.this,funiture_data.class));
                Intent locvalue=new Intent(getApplicationContext(), funiture_data.class);
                startActivity(locvalue);

            }
        });

    }

    private void saveUserInformation(){
        String name = Name.getText().toString().trim();
        double latitude = Double.parseDouble(Latitude.getText().toString().trim());
        double longitude = Double.parseDouble(Longitude.getText().toString().trim());
        StoreInfo storeInformation =new StoreInfo(name,latitude,longitude);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("Location").push().getKey();

        mDatabase.child(key).setValue(storeInformation);
        Toast.makeText(this,"Store Added",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view == btnsave){
            saveUserInformation();
            Name.getText().clear();
            Longitude.getText().clear();
            Latitude.getText().clear();
        }

    }

}