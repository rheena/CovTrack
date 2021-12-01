package com.example.covtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<PatientData> list;

    public MyAdapter(Context context, ArrayList<PatientData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        PatientData user = list.get(position);
        holder.firstname.setText(user.getFirstname());
        holder.lastname.setText(user.getLastname());
        holder.age.setText(user.getAge());
        holder.result.setText(user.getResult());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView firstname, lastname, age, result;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstname = itemView.findViewById(R.id.tvfirstname);
            lastname = itemView.findViewById(R.id.tvlastname);
            age = itemView.findViewById(R.id.tvage);
            result = itemView.findViewById(R.id.tvresults);
        }
    }
}
