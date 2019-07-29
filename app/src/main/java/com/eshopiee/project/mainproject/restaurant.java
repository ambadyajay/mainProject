package com.eshopiee.project.mainproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class restaurant extends AppCompatActivity implements LocationListener  {

    private RecyclerView Res_Rec;
    private DatabaseReference rRef;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    StoreInfo storeInfo;
    LocationManager locationManager;
    ArrayList<StoreInfo> userInfoList;
    RecyclerView.LayoutManager RlayoutManager;
    RecyclerView.Adapter Radapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);


        rRef = FirebaseDatabase.getInstance().getReference().child("Location").child("Restaurant");
        rRef.keepSynced(true);

        Res_Rec = (RecyclerView) findViewById(R.id.Res_Rec);
        Res_Rec.setHasFixedSize(true);
        Res_Rec.setLayoutManager(new LinearLayoutManager(this));

        storeInfo = new StoreInfo();
        userInfoList = new ArrayList<>();

        final float distance[] = new float[24];

        //to fid distance between two points

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        final double startlatitude = location.getLatitude();
        final double startlongitude = location.getLongitude();
        Location.distanceBetween(startlatitude, startlongitude, storeInfo.getLatitude(), storeInfo.getLongitude(), distance);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Location").child("Restaurant");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.store_info, R.id.userinfo, list);

    }
    @Override
    protected void onStart() {
        super.onStart();
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        final float distance[] = new float[24];
        final double startlatitude = location.getLatitude();
        final double startlongitude = location.getLongitude();
        rRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    storeInfo = ds.getValue(StoreInfo.class);
                    Location.distanceBetween(storeInfo.latitude, storeInfo.longitude, startlatitude, startlongitude, distance);
                    storeInfo.setDistance(distance[0]);
                    userInfoList.add(storeInfo);
                }
                Collections.sort(userInfoList, new Comparator<StoreInfo>() {
                    @Override
                    public int compare(StoreInfo o1, StoreInfo o2) {
                        return (int)(o1.getDistance()*1000 - o2.getDistance()*1000);
                    }
                });

                //to display the array list distance
                for(int i = 0; i < userInfoList.size(); i++) {
                    if (userInfoList.get(i).getDistance() < 5000f) {
                        list.add(userInfoList.get(i).getName());
                    }
                }
                Res_Rec.setHasFixedSize(true);
                RlayoutManager = new LinearLayoutManager(getApplicationContext());
                Radapter = new Restaurant_store_adapter(restaurant.this,list);
                Res_Rec.setLayoutManager(RlayoutManager);
                Res_Rec.setAdapter(Radapter);
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
