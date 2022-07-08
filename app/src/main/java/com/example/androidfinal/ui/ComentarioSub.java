package com.example.androidfinal.ui;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfinal.AdapterComentario;
import com.example.androidfinal.AdapterIMG;
import com.example.androidfinal.Comentario;
import com.example.androidfinal.PostMLBB;
import com.example.androidfinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


public class ComentarioSub extends Fragment {
    TextView btnPublicar;
    EditText edtComentario;
    RecyclerView rcv;
    PostMLBB p = new PostMLBB();
    DatabaseReference df = FirebaseDatabase.getInstance().getReference();
    int c1,c2,c3,c4,c5,f,tema;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comentario, container, false);
        SharedPreferences sp = getContext().getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        c1= Color.parseColor(sp.getString("Color1", "#3552f2"));
        c2=Color.parseColor(sp.getString("Color2", "#162d59"));
        c3=Color.parseColor(sp.getString("Color3", "#f2c53d"));
        c4=Color.parseColor(sp.getString("Color4", "#f2b05e"));
        c5=Color.parseColor(sp.getString("Color5", "#f27830"));
        String IDPost = sp.getString("IDPost", "0");
        tema= Integer.parseInt(sp.getString("Theme", "1"));
        f = Integer.parseInt(sp.getString("Fuente", "2"));

        view.setBackgroundColor(c1);
        rcv = view.findViewById(R.id.rcvComentarios);
        edtComentario = view.findViewById(R.id.edtmensaje_chat);
        edtComentario.setTextSize((f*2)+11);
        btnPublicar = view.findViewById(R.id.btn_Send);
        btnPublicar.setTextSize((f*2)+11);
        btnPublicar.setTextColor(Color.WHITE);
        btnPublicar.setBackgroundColor(c5);
        DateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.setComentarios(new Comentario(
                        sp.getString("Correo", "Anonimo"),
                        sp.getString("Nombre", "Anonimo"),
                        edtComentario.getText().toString(),
                        dfDate.format(Calendar.getInstance().getTime())));
                df.child("PostMLBB").child(IDPost).setValue(p);
                edtComentario.setText("");
            }
        });
        df.child("PostMLBB").child(IDPost).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot objSnaptshot) {
                try {
                    p=new PostMLBB();
                    p.setHeroe(objSnaptshot.child("heroe").getValue().toString());
                    p.setLikes(Integer.parseInt(objSnaptshot.child("likes").getValue().toString()));
                    p.setTipo(objSnaptshot.child("tipo").getValue().toString());
                    for (int i = 0 ; i < objSnaptshot.child("imagenes").getChildrenCount() ; i++ ){
                        p.setImagenes(objSnaptshot.child("imagenes").child(i+"").getValue().toString());
                    }
                    for (int i = 0 ; i < objSnaptshot.child("comentarios").getChildrenCount() ; i++ ){
                        p.setComentarios(new Comentario(
                                objSnaptshot.child("comentarios").child(i+"").child("correo").getValue().toString(),
                                objSnaptshot.child("comentarios").child(i+"").child("username").getValue().toString(),
                                objSnaptshot.child("comentarios").child(i+"").child("comentario").getValue().toString(),
                                objSnaptshot.child("comentarios").child(i+"").child("fecha").getValue().toString()));
                    }
                    p.setDescripcionEN(objSnaptshot.child("descripcionEN").getValue().toString());
                    p.setDescripcionES(objSnaptshot.child("descripcionES").getValue().toString());
                    p.setFecha(objSnaptshot.child("fecha").getValue().toString());
                    rcv.setBackgroundColor(c1);
                    rcv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                    AdapterComentario adapterComentario = new AdapterComentario(getContext(),p.getComentarios());
                    rcv.setAdapter(adapterComentario);

                }catch (Exception e){
                    Toast.makeText(getContext(), "->"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}