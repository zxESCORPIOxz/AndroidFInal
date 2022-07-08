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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfinal.AdapterIMG;
import com.example.androidfinal.Comentario;
import com.example.androidfinal.PostMLBB;
import com.example.androidfinal.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Detalle extends Fragment {

    private AdView mAdView;
    int c1,c2,c3,c4,c5,f,tema;
    DatabaseReference df = FirebaseDatabase.getInstance().getReference();
    RecyclerView RC;
    ImageView imgTipo;
    TextView txtHeroe,txtDescripcion;
    Button btnLike,btnComentarios;

    PostMLBB p;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);
        mAdView = view.findViewById(R.id.adViewDetalle);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
        SharedPreferences sp = getContext().getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        c1= Color.parseColor(sp.getString("Color1", "#3552f2"));
        c2=Color.parseColor(sp.getString("Color2", "#162d59"));
        c3=Color.parseColor(sp.getString("Color3", "#f2c53d"));
        c4=Color.parseColor(sp.getString("Color4", "#f2b05e"));
        c5=Color.parseColor(sp.getString("Color5", "#f27830"));
        String IDPost = sp.getString("IDPost", "0");
        tema= Integer.parseInt(sp.getString("Theme", "1"));
        f = Integer.parseInt(sp.getString("Fuente", "2"));



        RC = view.findViewById(R.id.rcvImgs);
        imgTipo=view.findViewById(R.id.imvTipo);
        txtHeroe=view.findViewById(R.id.txtHeroe);
        txtDescripcion=view.findViewById(R.id.txtDescripcion);
        btnComentarios=view.findViewById(R.id.btnComentario);
        LinearLayout lyttitulo=view.findViewById(R.id.lyttitulo);
        btnLike=view.findViewById(R.id.btnlike);
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

                    Drawable img;
                        switch (p.getTipo()){
                            case "Revamp":
                                img = getContext().getResources().getDrawable(R.drawable.ic_revamp);
                                break;
                            case "Nerf":
                                img = getContext().getResources().getDrawable(R.drawable.ic_nerf);
                                break;
                            case "Buff":
                                img = getContext().getResources().getDrawable(R.drawable.ic_buff);
                                break;
                            default:
                                img = getContext().getResources().getDrawable(R.drawable.ic_buff);
                        }
                        Bitmap bitmap = ((BitmapDrawable) img).getBitmap();
                        img = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 30, 30, true));
                        imgTipo.setBackground(img);

                        txtHeroe.setText(p.getHeroe());
                        txtHeroe.setTextSize((f*3)+30);
                        txtHeroe.setTypeface(null, Typeface.BOLD);
                        txtHeroe.setTextColor(Color.WHITE);
                        txtHeroe.setGravity(Gravity.LEFT);
                        if (Locale.getDefault().getDisplayLanguage().equals("English")){
                            txtDescripcion.setText(p.getDescripcionEN());
                        }else {
                            txtDescripcion.setText(p.getDescripcionES());
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            txtDescripcion.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                        }
                        txtDescripcion.setTextSize(f+13);
                        txtDescripcion.setTextColor(Color.WHITE);

                        img = getContext().getResources().getDrawable(R.drawable.ic_like);
                        bitmap = ((BitmapDrawable) img).getBitmap();
                        img = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 30, 30, true));
                        img.setTint(Color.WHITE);
                        btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                        btnLike.setText(p.getLikes()+"");
                        btnLike.setTextColor(Color.WHITE);
                        if(sp.getString(IDPost,"0").equals("0")) {
                            btnLike.setBackgroundColor(c5);
                        }else {
                            btnLike.setBackgroundColor(c3);
                        }
                        btnLike.setTypeface(null, Typeface.BOLD);
                        btnLike.setTextSize(f+15);

                        btnLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(sp.getString(IDPost,"0").equals("0")){
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString(IDPost, "1");
                                    editor.apply();
                                    btnLike.setText((p.getLikes()+1)+"");
                                    p.setLikes(p.getLikes()+1);
                                    df.child("PostMLBB").child(IDPost).setValue(p);
                                }
                            }
                        });

                        img = getResources().getDrawable(R.drawable.ic_comentario);
                        bitmap = ((BitmapDrawable) img).getBitmap();
                        img = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 30, 30, true));
                        img.setTint(Color.WHITE);
                        btnComentarios.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                        btnComentarios.setText(getResources().getString(R.string.btnComentario)+" "+p.getComentarios().size());
                        btnComentarios.setTextColor(Color.WHITE);
                        btnComentarios.setBackgroundColor(c5);
                        btnComentarios.setTypeface(null, Typeface.BOLD);
                        btnComentarios.setTextSize(f+15);
                        btnComentarios.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Navigation.findNavController(v).navigate(R.id.nav_Comentario);
                            }
                        });

                        RC.setBackgroundColor(c1);
                        RC.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
                        AdapterIMG adapterIMG = new AdapterIMG(getContext(),p.getImagenes());
                        RC.setAdapter(adapterIMG);
                        adapterIMG.setOnListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("imgFull", p.getImagenes().get(RC.getChildAdapterPosition(v)));
                                editor.apply();
                                Navigation.findNavController(v).navigate(R.id.nav_FullScreen);
                            }
                        });

                    if(tema==1){
                        view.setBackgroundResource(R.drawable.redn1_1);
                        lyttitulo.setBackgroundResource(R.drawable.redn3_1);
                        btnComentarios.setBackgroundResource(R.drawable.redn5_1);
                        if(sp.getString(IDPost,"0").equals("0")) {
                            btnLike.setBackgroundResource(R.drawable.redn5_1);
                        }else {
                            btnLike.setBackgroundResource(R.drawable.redn3_1);
                        }
                    }else {
                        view.setBackgroundResource(R.drawable.redn1_2);
                        lyttitulo.setBackgroundResource(R.drawable.redn3_2);
                        btnComentarios.setBackgroundResource(R.drawable.redn5_2);
                        if(sp.getString(IDPost,"0").equals("0")) {
                            btnLike.setBackgroundResource(R.drawable.redn5_2);
                        }else {
                            btnLike.setBackgroundResource(R.drawable.redn3_2);
                        }
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
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