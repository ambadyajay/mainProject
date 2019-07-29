package com.eshopiee.project.mainproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class furniturecommon extends AppCompatActivity  {

        LinearLayout rehma;
        TextView gcName;
        String name;
        ImageButton call;
        ImageButton direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniturecommon);


        rehma= findViewById(R.id.rehma_btn);
        gcName = findViewById(R.id.gcname);
        name = getIntent().getStringExtra("extra");
        gcName.setText(name);

        rehma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent furnitureintent=new Intent(getApplicationContext(),ArActivity.class);
                startActivity(furnitureintent);


            }
        });
        call = findViewById(R.id.callbtn);
        direction=findViewById(R.id.directionbtn);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9497016753"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=10.052070,76.381921"));
                startActivity(intent);
            }
        });




    }


}
