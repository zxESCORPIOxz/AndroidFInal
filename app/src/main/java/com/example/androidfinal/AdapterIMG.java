package com.example.androidfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterIMG extends RecyclerView.Adapter<AdapterIMG.RecyclerHolder> implements View.OnClickListener{
    LayoutInflater inflater;
    ArrayList<String> model;
    View.OnClickListener listener;
    Context context;

    public AdapterIMG(Context context, ArrayList<String> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context=context;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public AdapterIMG.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.itemimg, parent,false);
        v.setOnClickListener(this);
        return new AdapterIMG.RecyclerHolder(v);
    }
    public  void setOnListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIMG.RecyclerHolder holder, int position) {
        Glide.with(context).load(model.get(position)).into(holder.imgBtn);
    }

    @Override
    public int getItemCount() {
        return model .size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private ImageView imgBtn;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            imgBtn=itemView.findViewById(R.id.imgRC);
        }
    }
}
