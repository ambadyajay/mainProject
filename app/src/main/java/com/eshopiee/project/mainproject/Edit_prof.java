package com.eshopiee.project.mainproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Edit_prof extends AppCompatActivity {

    private EditText user_id,user_contact,user_name;
    private Button save_btn;


    private FirebaseAuth uAuth;
    private DatabaseReference uRef;
    String cUserid;
    final static int gallery_pic =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prof);

        uAuth = FirebaseAuth.getInstance();
        cUserid = uAuth.getCurrentUser().getUid();
        uRef = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(cUserid);

        user_id = (EditText) findViewById(R.id.user_id);
        user_name = (EditText) findViewById(R.id.user_name_value);
        user_contact = (EditText) findViewById(R.id.user_contact);
        save_btn = (Button) findViewById(R.id.save_btn);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInfo();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallery_pic && requestCode == RESULT_OK && data != null)
        {
            Uri Imageuri = data.getData();
        }
    }

    private void SaveInfo()
    {
        String userid = user_id.getText().toString();
        String username = user_name.getText().toString();
        String userconatct = user_contact.getText().toString();

        if (TextUtils.isEmpty(userid)){
            Toast.makeText(this, "Enter a user Name", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Enter a user id", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(userconatct)){
            Toast.makeText(this, "Enter a user contact", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap UserMap = new HashMap();
            UserMap.put("UserId", userid);
            UserMap.put("UserName", username);
            UserMap.put("UserContact", userconatct);
            uRef.updateChildren(UserMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if (task.isSuccessful())
                    {
                        Registraionclose();
                        Toast.makeText(Edit_prof.this, "Registerd Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(Edit_prof.this, "Something went Wrong"+message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void Registraionclose() {
        Intent intent = new Intent(Edit_prof.this,profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
