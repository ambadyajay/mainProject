package com.eshopiee.project.mainproject;

import android.support.annotation.NonNull;

public class StoreInfo implements Comparable<StoreInfo>{
    public String name;
    public double latitude;
    public double longitude;
    private double distance;

    public double getDistance(){ return distance;}

    public void setDistance(double distance){this.distance=distance;}

    public StoreInfo(){
    }
    public StoreInfo(String name  , double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int compareTo(@NonNull StoreInfo o) {

        double compareDistance=o.getDistance();
        return (int) (this.distance-compareDistance);
    }
}