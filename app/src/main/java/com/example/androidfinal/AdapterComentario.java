package com.example.androidfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterComentario extends RecyclerView.Adapter<AdapterComentario.RecyclerHolder> implements View.OnClickListener{
    LayoutInflater inflater;
    ArrayList<Comentario> model;
    View.OnClickListener listener;
    Context context;

    public AdapterComentario(Context context, ArrayList<Comentario> model) {
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
    public AdapterComentario.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.itemcomentario, parent,false);
        v.setOnClickListener(this);
        return new AdapterComentario.RecyclerHolder(v);
    }
    public  void setOnListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComentario.RecyclerHolder holder, int position) {
        SharedPreferences sp = context.getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        int c1= Color.parseColor(sp.getString("Color1", "#3552f2"));
        int c2=Color.parseColor(sp.getString("Color2", "#162d59"));
        int c3=Color.parseColor(sp.getString("Color3", "#f2c53d"));
        int c4=Color.parseColor(sp.getString("Color4", "#f2b05e"));
        int c5=Color.parseColor(sp.getString("Color5", "#f27830"));
        int f = Integer.parseInt(sp.getString("Fuente", "2"));
        int tema= Integer.parseInt(sp.getString("Theme", "1"));
        if(tema==1){
            holder.lyt.setBackgroundResource(R.drawable.redn5_1);
            holder.txtcorreo.setBackgroundResource(R.drawable.redn2_1);
            holder.txtuser.setBackgroundResource(R.drawable.redn4_1);
        }else {
            holder.lyt.setBackgroundResource(R.drawable.redn5_2);
            holder.txtcorreo.setBackgroundResource(R.drawable.redn2_2);
            holder.txtuser.setBackgroundResource(R.drawable.redn4_2);
        }
        holder.txtuser.setText(model.get(position).getUsername());
        holder.txtuser.setTextColor(Color.WHITE);
        holder.txtuser.setTextSize((f*2)+13);

        holder.txtcorreo.setText(model.get(position).getCorreo());
        holder.txtcorreo.setTextColor(Color.WHITE);
        holder.txtcorreo.setTextSize((f*2)+11);

        holder.txtcomentario.setText(model.get(position).getComentario());
        holder.txtcomentario.setTextColor(Color.WHITE);
        holder.txtcomentario.setTextSize((f*2)+11);

        holder.txtfecha.setText(model.get(position).getFecha());
        holder.txtfecha.setTextColor(Color.GRAY);
        holder.txtfecha.setTextSize((f*2)+11);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView txtuser,txtcorreo,txtcomentario,txtfecha;
        private LinearLayout lyt;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            lyt=itemView.findViewById(R.id.lytcomentario);
            txtuser=itemView.findViewById(R.id.txtUser);
            txtcorreo=itemView.findViewById(R.id.txtCorreo);
            txtcomentario=itemView.findViewById(R.id.txtComentario);
            txtfecha=itemView.findViewById(R.id.txtFecha);
        }
    }
}