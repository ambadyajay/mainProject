package com.eshopiee.project.mainproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userid;
    private EditText userpswed;
    private Button login_btn;
    private Button signup_btn;
    public TextView admintxt;
    private CheckBox mcheckbox;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor meditor;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("SignIn Activity");

        mAuth = FirebaseAuth.getInstance();

        userid =findViewById(R.id.userid);
        userpswed = findViewById(R.id.userpswd);
        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);
        admintxt=findViewById(R.id.admin);
        mcheckbox=findViewById(R.id.rem_chk_box);

        signup_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);

        mPreferences=getSharedPreferences("DATA BASE", Context.MODE_PRIVATE);
        meditor=mPreferences.edit();
        checkSharedPrefernces();

        admintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentadmin=new Intent(getApplicationContext(), adminlogin.class);
                startActivity(intentadmin);
                finish();
            }
        });


    }
    private void checkSharedPrefernces(){
        String checkbox=mPreferences.getString(getString(R.string.checkbox),"False") ;
        String name=mPreferences.getString(getString(R.string.name),"") ;
        String password=mPreferences.getString(getString(R.string.password),"") ;

        userid.setText(name);
        userpswed.setText(password);
        if(checkbox.equals("True")){
            mcheckbox.setChecked(true);
        }else{
            mcheckbox.setChecked(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                userlogin();
                break;
            case R.id.signup_btn:
                Intent intent = new Intent(getApplicationContext(),signupActivity.class);
                finish();
                startActivity(intent);
                break;
        }
    }

    private void userlogin(){

        String email = userid.getText().toString().trim();
        String password =  userpswed.getText().toString().trim();

        if (email.isEmpty()){
            userid.setError("Enter a valid email address");
            userid.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userid.setError("Enter valid email id");
            userid.requestFocus();
            return;
        }
        if (password.isEmpty()){
            userpswed.setError("Enter your password");
            userpswed.requestFocus();
            return;
        }
        if (password.length()<6){
            userpswed.setError("Enter a valid password");
            userpswed.requestFocus();
            return;

        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    login_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mcheckbox.isChecked()){
                                meditor.putString(getString(R.string.checkbox),"True");

                                String name=userid.getText().toString();
                                meditor.putString(getString(R.string.name),name);


                                String password=userpswed.getText().toString();
                                meditor.putString(getString(R.string.password),password);
                                meditor.commit();
                            }
                            else
                            {
                                meditor.putString(getString(R.string.checkbox),"False");
                                meditor.commit();


                                meditor.putString(getString(R.string.name),"");
                                meditor.commit();

                                meditor.putString(getString(R.string.password),"");
                                meditor.commit();
                            }
                            Intent intent = new Intent(getApplicationContext(),homescreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             startActivity(intent);

                        }
                    });
                 //

                }
                else {
                    Toast.makeText(getApplicationContext(),"Login Unsussesfull",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
