package com.suveraapp.adapter;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suveraapp.R;
import com.suveraapp.objects.UpdateResults;

import java.util.ArrayList;

public class MyDrugAdapter extends RecyclerView.Adapter<MyDrugAdapter.DrugHolder> {

    private LayoutInflater mInflater;
    private ArrayList<UpdateResults> updResults;


    public MyDrugAdapter(Context context, ArrayList<UpdateResults> currentList) {
        mInflater = LayoutInflater.from(context);
        updResults = currentList;
    }

    @Override
    public DrugHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.drug_item, parent, false);
        DrugHolder holder = new DrugHolder(view);
        view.getBackground().setAlpha(77);
        return holder;
    }

    @Override
    public void onBindViewHolder(DrugHolder holder, int position) {
        UpdateResults updateResults = updResults.get(position);
        String[] name = updateResults.getName().split(" ");
        holder.mDrug.setText(name[0]);
        holder.mDosage.setText("x" + updateResults.getDosage());
    }

    @Override
    public int getItemCount() {
        return updResults.size();
    }

    public static class DrugHolder extends RecyclerView.ViewHolder {

        TextView mDrug, mDosage;

        public DrugHolder(View itemView) {
            super(itemView);
            mDrug = (TextView) itemView.findViewById(R.id.rc_drug);
            mDosage = (TextView) itemView.findViewById(R.id.rc_dosage);
        }
    }

    }
