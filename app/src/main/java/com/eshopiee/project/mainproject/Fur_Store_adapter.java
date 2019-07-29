package com.eshopiee.project.mainproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder>  {

    ArrayList<String>list;
    Context mContext;



    public StoreAdapter(Context context ,ArrayList<String> sName) {
        this.list = sName;
        this.mContext = context;
    }

    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.furniture_card, viewGroup ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder viewHolder, int i) {
        viewHolder.shopeName.setText(list.get(i));
        viewHolder.shopeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext ,furniturecommon.class);
                intent.putExtra("extra",list.get(i));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shopeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopeName = itemView.findViewById(R.id.shopname);

        }
    }

}
