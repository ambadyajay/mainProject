package com.eshopiee.project.mainproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class adminlogin extends AppCompatActivity implements View.OnClickListener {

    private TextView adminid;
    private TextView adminpswd;
    private Button adminlog;
    // private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        adminid = findViewById(R.id.adminid);
        adminpswd = findViewById(R.id.adminpswd);
        adminlog = findViewById(R.id.adminlogbtn);
        //  mAuth = FirebaseAuth.getInstance();

        adminlog.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        String email = adminid.getText().toString().trim();
        String password =  adminpswd.getText().toString().trim();



        if (email.isEmpty()){
            adminid.setError("Enter a valid email address");
            adminid.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            adminid.setError("Enter valid email id");
            adminid.requestFocus();
            return;
        }
        if (password.isEmpty()){
            adminpswd.setError("Enter your password");
            adminpswd.requestFocus();
            return;
        }
        if (password.length()<6){
            adminpswd.setError("Enter a valid password");
            adminpswd.requestFocus();
            return;

        }

        if (adminid.getText().toString().equals("Admin123@gmail.com") && adminpswd.getText().toString().equals("admin123"))
        {
            adminlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent admin = new Intent(getApplicationContext(), admin_list_category.class);
                    startActivity(admin);
                    finish();
                }
            });
        }
        else

        {
            Toast.makeText(getApplicationContext(), "Access Denied", Toast.LENGTH_LONG).show();

        }


    }
}
