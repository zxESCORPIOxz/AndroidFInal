package com.example.androidfinal.ui;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.speech.RecognizerIntent;
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

import com.example.androidfinal.Comentario;
import com.example.androidfinal.PostMLBB;
import com.example.androidfinal.R;
import com.example.androidfinal.vozManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Locale;

import techpaliyal.com.curlviewanimation.CurlPage;
import techpaliyal.com.curlviewanimation.CurlView;
import static android.content.Context.MODE_PRIVATE;

public class Noticias extends Fragment {

    private AdView mAdView;
    DatabaseReference df = FirebaseDatabase.getInstance().getReference();
    CurlView mCurlView;
    ImageView btnVoz;
    Button btnDetalle;
    ArrayList<PostMLBB> listaPost = new ArrayList<>();
    ArrayList<String> listaIDS = new ArrayList<>();
    int c1,c2,c3,c4,c5,f,tema,cfont=0;
    int tama=0;
    int index = 0;
    PostMLBB p = new PostMLBB();
    ArrayList<BitmapDrawable> mBitmapIds =new ArrayList<>();
    vozManager vM;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        mAdView = view.findViewById(R.id.adView);
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
        c1=Color.parseColor(sp.getString("Color1", "#3552f2"));
        c2=Color.parseColor(sp.getString("Color2", "#162d59"));
        c3=Color.parseColor(sp.getString("Color3", "#f2c53d"));
        c4=Color.parseColor(sp.getString("Color4", "#f2b05e"));
        c5=Color.parseColor(sp.getString("Color5", "#f27830"));
        tema= Integer.parseInt(sp.getString("Theme", "1"));
        f = Integer.parseInt(sp.getString("Fuente", "2"));
        cfont = Color.WHITE;
        switch(f){
            case 1:{
                tama = -1;
                break;
            }
            case 2:{
                tama = 0;
                break;
            }
            case 3:{
                tama = 1;
                break;
            }
            default:{
                Toast.makeText(getContext(), R.string.msjFuente, Toast.LENGTH_SHORT).show();
            }
        }
        try {
            if (getActivity().getLastNonConfigurationInstance() != null) {
                index = (Integer) getActivity().getLastNonConfigurationInstance();
            }
            btnDetalle = view.findViewById(R.id.btnDetalle);
            mCurlView = view.findViewById(R.id.pagNoticias);
            listaPost.clear();
            listaIDS.clear();
            mBitmapIds.add((BitmapDrawable) getResources().getDrawable(R.drawable.mllogo));
            df.child("PostMLBB").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                            listaIDS.add(objSnaptshot.getKey());
                            p=new PostMLBB();
                            p.setHeroe(objSnaptshot.child("heroe").getValue().toString());
                            p.setLikes(Integer.parseInt(objSnaptshot.child("likes").getValue().toString()));
                            p.setTipo(objSnaptshot.child("tipo").getValue().toString());
                            for (int i = 0 ; i < objSnaptshot.child("imagenes").getChildrenCount() ; i++ ){
                                p.setImagenes(objSnaptshot.child("imagenes").child(i+"").toString());
                            }
                            for (int i = 0 ; i < objSnaptshot.child("comentarios").getChildrenCount() ; i++ ){
                                p.setComentarios(new Comentario(
                                    objSnaptshot.child("comentarios").child(i+"").child("correo").toString(),
                                    objSnaptshot.child("comentarios").child(i+"").child("username").toString(),
                                    objSnaptshot.child("comentarios").child(i+"").child("comentario").toString(),
                                    objSnaptshot.child("comentarios").child(i+"").child("fecha").toString()));
                            }
                            p.setImagenes(objSnaptshot.child("imagenes").child("0").toString());
                            p.setDescripcionEN(objSnaptshot.child("descripcionEN").getValue().toString());
                            p.setDescripcionES(objSnaptshot.child("descripcionES").getValue().toString());
                            p.setFecha(objSnaptshot.child("fecha").getValue().toString());
                            listaPost.add(p);
                            LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.VERTICAL);
                            if(tema==1){
                                layout.setBackgroundResource(R.drawable.redn1_1);
                                view.setBackgroundResource(R.drawable.redn1_1);
//                                mCurlView.setBackgroundResource(R.drawable.redn1_1);
                                btnDetalle.setBackgroundResource(R.drawable.redn3_1);
                                btnVoz.setBackgroundResource(R.drawable.redn3_1);
                            }else {
                                layout.setBackgroundResource(R.drawable.redn1_2);
                                view.setBackgroundResource(R.drawable.redn1_2);
//                                mCurlView.setBackgroundResource(R.drawable.redn1_2);
                                btnDetalle.setBackgroundResource(R.drawable.redn3_2);
                                btnVoz.setBackgroundResource(R.drawable.redn3_2);
                            }
                            layout.setLayoutParams(new LinearLayout.LayoutParams(550, 1500));
                            layout.setGravity(Gravity.CENTER);

                            TextView Heroe = new TextView(getContext());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(550, 60);
                            layoutParams.setMargins(30,0,30,0);
                            Heroe.setLayoutParams(layoutParams);
                            Heroe.setTypeface(null, Typeface.BOLD);
                            Heroe.setText(p.getHeroe());
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
                            Heroe.setCompoundDrawablesWithIntrinsicBounds(img, null, img, null);
                            Heroe.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15+tama);
                            Heroe.setTextColor(cfont);
                            //Heroe.setBackgroundColor(c1);
                            Heroe.setGravity(Gravity.CENTER);

                            layout.addView(Heroe);

                            TextView Descripcion = new TextView(getContext());
                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(490, 750);
                            layoutParams1.setMargins(30,15,30,30);
                            Descripcion.setLayoutParams(layoutParams1);
                            if (Locale.getDefault().getDisplayLanguage().equals("English")){
                                Descripcion.setText(p.getDescripcionEN());
                            }else {
                                Descripcion.setText(p.getDescripcionES());
                            }
                            Descripcion.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12+tama);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                Descripcion.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                            }

                            Descripcion.setTextColor(cfont);
                            //Descripcion.setBackgroundColor(c1);

                            layout.addView(Descripcion);

                            LinearLayout layoutDatos = new LinearLayout(getContext());
                            layoutDatos.setOrientation(LinearLayout.HORIZONTAL);
                            //layoutDatos.setBackgroundColor(c1);
                            layoutDatos.setLayoutParams(new LinearLayout.LayoutParams(550, 80));
                            layoutDatos.setGravity(Gravity.LEFT);

                                TextView Likes = new TextView(getContext());
                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(130, 80);
                                layoutParams2.setMargins(30,30,30,15);
                                Likes.setLayoutParams(layoutParams2);
                                Likes.setText(""+p.getLikes());
                                img = getContext().getResources().getDrawable(R.drawable.ic_like);
                                bitmap = ((BitmapDrawable) img).getBitmap();
                                img = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 15, 15, true));
                                img.setTint(Color.WHITE);
                                Likes.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                                Likes.setTypeface(null, Typeface.BOLD);
                                Likes.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13+tama);
                                Likes.setTextColor(cfont);
                                //Likes.setBackgroundColor(c1);
                                Likes.setGravity(Gravity.CENTER_VERTICAL);

                                layoutDatos.addView(Likes);

                                TextView Fecha = new TextView(getContext());
                                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(300, 80);
                                layoutParams3.setMargins(30,30,30,15);
                                Fecha.setLayoutParams(layoutParams3);
                                Fecha.setText(""+p.getFecha());
                                Fecha.setTypeface(null, Typeface.BOLD);
                                Fecha.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13+tama);
                                Fecha.setTextColor(cfont);
                                //Fecha.setBackgroundColor(c1);
                                Fecha.setGravity(Gravity.RIGHT);

                                layoutDatos.addView(Fecha);

                            layout.addView(layoutDatos);

                            mBitmapIds.add(new BitmapDrawable(loadBitmapFromView(layout)));
                        }
                    }catch (Exception e){
                        Toast.makeText(getContext(), "->"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            });

            mCurlView.setPageProvider(new CurlView.PageProvider() {
                @Override
                public int getPageCount() {
                    return mBitmapIds.size();
                }

                @Override
                public void updatePage(CurlPage page, int width, int height, int index) {
                    Bitmap front = loadBitmap(width, height, index);
                    page.setTexture(front, CurlPage.SIDE_FRONT);
                    page.setColor(c2, CurlPage.SIDE_BACK);
                }
            });
            mCurlView.setCurrentIndex(index);
            btnDetalle.setTextColor(cfont);
            btnDetalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((mCurlView.getCurrentIndex()>0 && mCurlView.getCurrentIndex()<=listaPost.size())){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("IDPost", listaIDS.get(mCurlView.getCurrentIndex()-1));
                        editor.apply();

                        Navigation.findNavController(v).navigate(R.id.nav_Detalle);
                    }else {
                        Toast.makeText(getContext(), R.string.msjPost, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnVoz=view.findViewById(R.id.btnVoz);
            vM=new vozManager();
            vM.init(getContext());
            btnVoz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if((mCurlView.getCurrentIndex()>0 && mCurlView.getCurrentIndex()<=listaPost.size())){
                        Cargartexto();
                    }else {
                        Toast.makeText(getContext(), R.string.msjPost, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }
    private void Cargartexto() {
        float pitch = (float) 1;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) 1;
        if (speed < 0.1) speed = 0.1f;
        String leer;
        if (Locale.getDefault().getDisplayLanguage().equals("English")){
            leer = listaPost.get(mCurlView.getCurrentIndex()-1).getDescripcionEN();
            leer = leer.replace(">>", " pass to ");
            leer = leer.replace("(↓)", " nerfed ");
            leer = leer.replace("(~)", " balanced ");
            leer = leer.replace("(↑)", " buffed ");
        }else {
            leer = listaPost.get(mCurlView.getCurrentIndex()-1).getDescripcionES();
            leer = leer.replace(">>", " pasa a ");
            leer = leer.replace("(↓)", " nerfeado ");
            leer = leer.replace("(~)", " balanceado ");
            leer = leer.replace("(↑)", " bufeado ");
        }
        vM.IniciarCola(leer,pitch,speed);
    }


    public static Bitmap loadBitmapFromView(View tv) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tv.measure(spec, spec);
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(tv.getMeasuredWidth(), tv.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate((-tv.getScrollX()), (-tv.getScrollY()));
        tv.draw(c);
        return b;
    }

    private Bitmap loadBitmap(int width, int height, int index) {
        Bitmap b = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        b.eraseColor(c1);
        Canvas c = new Canvas(b);
        Drawable d = mBitmapIds.get(index);

        int margin = 0;
        int border = 0;
        Rect r = new Rect(margin, margin, width - margin, height - margin);

        int imageWidth = r.width() - (border * 2);
        int imageHeight = imageWidth * d.getIntrinsicHeight()
                / d.getIntrinsicWidth();
        if (imageHeight > r.height() - (border * 2)) {
            imageHeight = r.height() - (border * 2);
            imageWidth = imageHeight * d.getIntrinsicWidth()
                    / d.getIntrinsicHeight();
        }

        r.left += ((r.width() - imageWidth) / 2) - border;
        r.right = r.left + imageWidth + border + border;
        r.top += ((r.height() - imageHeight) / 2) - border;
        r.bottom = r.top + imageHeight + border + border;

        Paint p = new Paint();
        p.setColor(c1);
        c.drawRect(r, p);
        r.left += border;
        r.right -= border;
        r.top += border;
        r.bottom -= border;

        d.setBounds(r);
        d.draw(c);
        return b;
    }
}