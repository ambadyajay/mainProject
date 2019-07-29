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

class pharmacy_store_adapter extends RecyclerView.Adapter<pharmacy_store_adapter.ViewHolder>  {

    ArrayList<String>list;
    Context mContext;



    public pharmacy_store_adapter( Context context,ArrayList<String> sName) {
        this.list = sName;
        this.mContext = context;

    }


    @NonNull
    @Override
    public pharmacy_store_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pharmacy_card, viewGroup ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pharmacy_store_adapter.ViewHolder viewHolder, int i) {
        viewHolder.shopeName.setText(list.get(i));
        viewHolder.shopeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext ,Pharmacy_common.class);
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
