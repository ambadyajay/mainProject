package com.eshopiee.project.mainproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class fashion_data extends AppCompatActivity implements LocationListener {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<String> list;
    ArrayAdapter <String> adapter;
    StoreInfo storeInfo;
    LocationManager locationManager;
    ArrayList<StoreInfo> storeInfoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_data);
        storeInfo = new StoreInfo();
        final float distance[]=new float[24];
        storeInfoArrayList=new ArrayList<>();

        //Distance between 2 points

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        final double startlatitude = location.getLatitude();
        final double startlongitude = location.getLongitude();

        Location.distanceBetween(startlatitude, startlongitude, storeInfo.getLatitude(), storeInfo.getLongitude(), distance);

        listView= (ListView)findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Location").child("Fashion");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.store_info,R.id.userinfo, list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    storeInfo = ds.getValue(StoreInfo.class);
                    Location.distanceBetween(storeInfo.latitude, storeInfo.longitude, startlatitude, startlongitude, distance);

                    storeInfo.setDistance(distance[0]);
                    storeInfoArrayList.add(storeInfo);
                }
                Collections.sort(storeInfoArrayList,new Comparator<StoreInfo>(){


                    @Override
                    public int compare(StoreInfo o1, StoreInfo o2) {
                        return (int) (o1.getDistance() * 1000 - o2.getDistance() * 1000);
                    }
                });
                for(int i=0;i<storeInfoArrayList.size();i++){
                    list.add(storeInfoArrayList.get(i).getName() + "distance:" +storeInfoArrayList.get(i).getDistance());
                }
                listView.setAdapter(adapter);
                Log.v("Store",storeInfoArrayList.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
