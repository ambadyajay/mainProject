package com.eshopiee.project.mainproject;

import android.content.Context;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link nearme.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link nearme#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nearme extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap mMap;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private ChildEventListener mChild;
    private static final int userloccode = 99;
    private DatabaseReference mRef;
    private DatabaseReference rRef;
    private DatabaseReference gRef;
    private DatabaseReference sRef;
    private DatabaseReference faRef;
    private StoreInfo storeInfo;
    ArrayList<String> list;
    ArrayList<StoreInfo> storeinfoList;
    private LocationManager locationManager;
    private DatabaseReference fRef;
    private Marker curLocMark;
    Marker marker;
    FirebaseDatabase database;
    View mview;
    MapView mapView;
    private OnFragmentInteractionListener mListener;

    public nearme() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nearme.
     */
    // TODO: Rename and change types and number of parameters
    public static nearme newInstance(String param1, String param2) {
        nearme fragment = new nearme();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            CheckPermission();
        }
        ChildEventListener mchild;
        rRef = FirebaseDatabase.getInstance().getReference("Location").child("Restaurant");
        fRef = FirebaseDatabase.getInstance().getReference("Location").child("Furniture");
        mRef = FirebaseDatabase.getInstance().getReference("Location").child("Pharmacy");
        gRef = FirebaseDatabase.getInstance().getReference("Location").child("Grocery");
        sRef = FirebaseDatabase.getInstance().getReference("Location").child("Shoe");
        faRef = FirebaseDatabase.getInstance().getReference("Location").child("Fashion");


        rRef.push().setValue(marker);
        fRef.push().setValue(marker);
        mRef.push().setValue(marker);
        gRef.push().setValue(marker);
        faRef.push().setValue(marker);
        sRef.push().setValue(marker);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_nearme, container, false);
        return mview;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView=(MapView)mview.findViewById(R.id.map);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        storeInfo=new StoreInfo();
        storeinfoList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        final float distance[] = new float[24];
        storeinfoList = new ArrayList<>();

        //Distance bw two points
        locationManager = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        final double startlatitude = location.getLatitude();
        final double startlongitude = location.getLongitude();



//RESTAURANT
        rRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   storeInfo = ds.getValue(StoreInfo.class);

                    Location.distanceBetween(storeInfo.latitude, storeInfo.longitude, startlatitude, startlongitude, distance);
                    storeInfo.setDistance(distance[0]);
                   storeinfoList.add(storeInfo);

                    Collections.sort(storeinfoList, new Comparator<StoreInfo>() {
                        @Override
                        public int compare(StoreInfo o1, StoreInfo o2) {
                            return (int) (o1.getDistance() * 1000 - o2.getDistance() * 1000);
                        }
                    });
                    for (int i = 0; i < storeinfoList.size(); i++) {
                        if (storeinfoList.get(i).getDistance() < 5000f) {
                            LatLng location = new LatLng(storeinfoList.get(i).latitude, storeinfoList.get(i).longitude);
                            mMap.addMarker(new MarkerOptions().position(location).title(storeinfoList.get(i).name)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //FURNITURE
        fRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   storeInfo = ds.getValue(StoreInfo.class);

                    Location.distanceBetween(storeInfo.latitude, storeInfo.longitude, startlatitude, startlongitude, distance);
                    storeInfo.setDistance(distance[0]);
                    storeinfoList.add(storeInfo);

                    Collections.sort(storeinfoList, new Comparator<StoreInfo>() {
                        @Override
                        public int compare(StoreInfo o1, StoreInfo o2) {
                            return (int) (o1.getDistance() * 1000 - o2.getDistance() * 1000);
                        }
                    });
                    for (int i = 0; i < storeinfoList.size(); i++) {
                        if (storeinfoList.get(i).getDistance() < 5000f) {
                            LatLng location = new LatLng(storeinfoList.get(i).latitude, storeinfoList.get(i).longitude);
                            mMap.addMarker(new MarkerOptions().position(location).title(storeinfoList.get(i).name)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //PHARMACY
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    storeInfo = ds.getValue(StoreInfo.class);

                    Location.distanceBetween(storeInfo.latitude, storeInfo.longitude, startlatitude, startlongitude, distance);
                    storeInfo.setDistance(distance[0]);
                    storeinfoList.add(storeInfo);

                    Collections.sort(storeinfoList, new Comparator<StoreInfo>() {
                        @Override
                        public int compare(StoreInfo o1, StoreInfo o2) {
                            return (int) (o1.getDistance() * 1000 - o2.getDistance() * 1000);
                        }
                    });
                    for (int i = 0; i < storeinfoList.size(); i++) {
                        if (storeinfoList.get(i).getDistance() < 5000f) {
                            LatLng location = new LatLng(storeinfoList.get(i).latitude, storeinfoList.get(i).longitude);
                            mMap.addMarker(new MarkerOptions().position(location).title(storeinfoList.get(i).name)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //GROCERY
        gRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    storeInfo = ds.getValue(StoreInfo.class);

                    Location.distanceBetween(storeInfo.latitude, storeInfo.longitude, startlatitude, startlongitude, distance);
                    storeInfo.setDistance(distance[0]);
                    storeinfoList.add(storeInfo);

                    Collections.sort(storeinfoList, new Comparator<StoreInfo>() {
                        @Override
                        public int compare(StoreInfo o1, StoreInfo o2) {
                            return (int) (o1.getDistance() * 1000 - o2.getDistance() * 1000);
                        }
                    });
                    for (int i = 0; i < storeinfoList.size(); i++) {
                        if (storeinfoList.get(i).getDistance() < 5000f) {
                            LatLng location = new LatLng(storeinfoList.get(i).latitude, storeinfoList.get(i).longitude);
                            mMap.addMarker(new MarkerOptions().position(location).title(storeinfoList.get(i).name)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //SHOES
        sRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    storeInfo = ds.getValue(StoreInfo.class);

                    Location.distanceBetween(storeInfo.latitude, storeInfo.longitude, startlatitude, startlongitude, distance);
                    storeInfo.setDistance(distance[0]);
                    storeinfoList.add(storeInfo);

                    Collections.sort(storeinfoList, new Comparator<StoreInfo>() {
                        @Override
                        public int compare(StoreInfo o1, StoreInfo o2) {
                            return (int) (o1.getDistance() * 1000 - o2.getDistance() * 1000);
                        }
                    });
                    for (int i = 0; i < storeinfoList.size(); i++) {
                        if (storeinfoList.get(i).getDistance() < 5000f) {
                            LatLng location = new LatLng(storeinfoList.get(i).latitude, storeinfoList.get(i).longitude);
                            mMap.addMarker(new MarkerOptions().position(location).title(storeinfoList.get(i).name)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //FASHION
        faRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    storeInfo = ds.getValue(StoreInfo.class);

                    Location.distanceBetween(storeInfo.latitude, storeInfo.longitude, startlatitude, startlongitude, distance);
                    storeInfo.setDistance(distance[0]);
                    storeinfoList.add(storeInfo);

                    Collections.sort(storeinfoList, new Comparator<StoreInfo>() {
                        @Override
                        public int compare(StoreInfo o1, StoreInfo o2) {
                            return (int) (o1.getDistance() * 1000 - o2.getDistance() * 1000);
                        }
                    });
                    for (int i = 0; i < storeinfoList.size(); i++) {
                        if (storeinfoList.get(i).getDistance() < 5000f) {
                            LatLng location = new LatLng(storeinfoList.get(i).latitude, storeinfoList.get(i).longitude);
                            mMap.addMarker(new MarkerOptions().position(location).title(storeinfoList.get(i).name)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            BuildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    public boolean CheckPermission()
    {
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, userloccode);
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, userloccode);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case userloccode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION )== PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            BuildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    protected synchronized void BuildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        lastLocation = location;
        if (curLocMark != null)
        {
            curLocMark.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You Are Here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        curLocMark = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));

        if (googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

