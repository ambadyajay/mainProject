package com.eshopiee.project.mainproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RestaurantInfoHolder extends RecyclerView.ViewHolder
{
    View fView;
    public RestaurantInfoHolder(View nameView)
    {
        super(nameView);
        fView = nameView;
    }

    public void setName(String name)
    {
        TextView shopename = (TextView)fView.findViewById(R.id.shopname);
        shopename.setText(name);
    }

}