package com.eshopiee.project.mainproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_list_category extends AppCompatActivity {

    private Button Res_btn,Fur_btn,Med_btn,Fash_btn,Shoes_btn,Grocery_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list_admin);

        Res_btn = (Button)findViewById(R.id.Res_btn);
        Fur_btn = (Button)findViewById(R.id.Fur_btn);
        Med_btn = (Button)findViewById(R.id.Med_btn);
        Fash_btn=(Button)findViewById(R.id.Fashion_btn);
        Shoes_btn=(Button)findViewById(R.id.shoe_btn);
        Grocery_btn=(Button)findViewById(R.id.grocery_btn);


        Res_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(admin_list_category.this,Res_Admin.class));
            }
        });

        Fur_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_list_category.this,Fur_Admin.class));
            }
        });

        Med_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_list_category.this, pharmacy_admin.class));
            }
        });

        Fash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_list_category.this,fashion_admin.class));

            }
        });

        Shoes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_list_category.this,shoe_admin.class));

            }
        });

        Grocery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_list_category.this,grocery_admin.class));

            }
        });

    }


}
