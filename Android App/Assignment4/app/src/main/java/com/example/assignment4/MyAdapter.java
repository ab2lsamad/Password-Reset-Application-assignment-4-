package com.example.assignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Data> itemlist;
    RecyclerViewOnClickListener listener;

    public MyAdapter(List<Data> itemlist, RecyclerViewOnClickListener listener) {
        this.itemlist = itemlist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.item_layout,parent,false);
        MyViewHolder viewholder = new MyViewHolder(view,listener);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Data item = itemlist.get(position);
        holder.name.setText(item.getName());
        holder.date.setText(item.getDate());
        String str = item.getBody();
        if (str.length() >= 14)
             holder.body.setText(item.getBody().substring(0,15)+".....");
        else
            holder.body.setText(item.getBody());
        holder.status.setText(item.getApp_status());
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView icon;
        TextView name,body,date,status;
        RecyclerViewOnClickListener listener;

        public MyViewHolder(@NonNull View itemView, RecyclerViewOnClickListener listener) {
            super(itemView);
            icon = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView_name);
            body = itemView.findViewById(R.id.textView_body);
            date = itemView.findViewById(R.id.textView_date);
            status = itemView.findViewById(R.id.textView_status);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    public interface RecyclerViewOnClickListener{
        void onItemClick(int postion);
    }
}
