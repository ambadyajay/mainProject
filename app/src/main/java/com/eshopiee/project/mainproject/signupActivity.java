package com.eshopiee.project.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class signupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userid;
    private EditText userpswd1;
    private EditText userpswd;
    private Button register_btn;
    private TextView existing_id;
    private EditText usercontact;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Signup Activity");


        userid =(EditText) findViewById(R.id.userid);
        userpswd = (EditText)findViewById(R.id.userpswd);
        userpswd1 = (EditText) findViewById(R.id.userpswd1);
        register_btn = (Button) findViewById(R.id.register_btn);
        existing_id = (TextView) findViewById(R.id.existing_id);
        usercontact = (EditText) findViewById(R.id.usercontact);
        register_btn.setOnClickListener(this);
        existing_id.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                UserRegister();
                break;

            case R.id.existing_id:
                Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                finish();
                startActivity(intent);
                break;
        }
    }



    private void UserRegister() {
        final String email = userid.getText().toString().trim();
        String password = userpswd.getText().toString().trim();
        String password1 = userpswd1.getText().toString().trim();

        if (email.isEmpty()) {
            userid.setError("Enter a valid email id");
            userid.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userid.setError("Enter valid email id");
            userid.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            userid.setError("Enter your email id");
            userid.requestFocus();
            return;
        }
        if (!password1.equals(password)){
            userpswd1.setError("Passwords Dont'Match ");
            userpswd1.requestFocus();
            return;
        }
        if (password.length() < 6) {
            userpswd.setError("Minimum length should be 6");
            userpswd.requestFocus();
            return;
        }
        if (usercontact.length() != 10){
            usercontact.setError("Invalid Phone Number");
            usercontact.requestFocus();
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();

                    register_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intenthome=new Intent(signupActivity.this,loginActivity.class);
                            startActivity(intenthome);
                            finish();
                        }
                    });

                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "User Already Exists", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }


        });
    }
}

