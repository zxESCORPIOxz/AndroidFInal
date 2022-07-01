package com.example.androidfinal.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfinal.Comentario;
import com.example.androidfinal.PostMLBB;
import com.example.androidfinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import techpaliyal.com.curlviewanimation.CurlPage;
import techpaliyal.com.curlviewanimation.CurlView;
import techpaliyal.com.curlviewanimation.CurlActivity;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Noticias extends Fragment {
    DatabaseReference df = FirebaseDatabase.getInstance().getReference();;
    CurlView mCurlView;
    Button btnDetalle;
    String er;
    ArrayList<PostMLBB> listaPost;
    int c1,c2,c3,c4,c5,f;
    int tama=0;
    int index = 0;
    PostMLBB p = new PostMLBB();
    ArrayList<BitmapDrawable> mBitmapIds =new ArrayList<>();
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);
        SharedPreferences sp = getContext().getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        c1=Color.parseColor(sp.getString("Color1", "#3552f2"));
        c2=Color.parseColor(sp.getString("Color2", "#162d59"));
        c3=Color.parseColor(sp.getString("Color3", "#f2c53d"));
        c4=Color.parseColor(sp.getString("Color4", "#f2b05e"));
        c5=Color.parseColor(sp.getString("Color5", "#f27830"));
        f = Integer.parseInt(sp.getString("Fuente", "2"));
        switch(f){
            case 1:{
                tama = -2;
                break;
            }
            case 2:{
                tama = 0;
                break;
            }
            case 3:{
                tama = 2;
                break;
            }
            default:{
                Toast.makeText(getContext(), "Fuente alterada", Toast.LENGTH_SHORT).show();
            }
        }
        try {
            if (getActivity().getLastNonConfigurationInstance() != null) {
                index = (Integer) getActivity().getLastNonConfigurationInstance();
            }
            btnDetalle = view.findViewById(R.id.btnDetalle);
            mCurlView = view.findViewById(R.id.pagNoticias);
            mBitmapIds.add((BitmapDrawable) getResources().getDrawable(R.drawable.image1));
            df.child("PostMLBB").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaPost = new ArrayList<>();
                    er=dataSnapshot.toString();
                    try {
                        for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                            p.setHeroe(objSnaptshot.child("heroe").getValue().toString());
                            p.setLikes(Integer.parseInt(objSnaptshot.child("likes").getValue().toString()));
                            p.setTipo(objSnaptshot.child("tipo").getValue().toString());
                            p.setDescripcionEN(objSnaptshot.child("descripcionEN").getValue().toString());
                            p.setDescripcionES(objSnaptshot.child("descripcionES").getValue().toString());
                            p.setFecha(objSnaptshot.child("fecha").getValue().toString());
                            listaPost.add(p);
                            LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.VERTICAL);
                            layout.setBackgroundColor(c1);
                            layout.setLayoutParams(new LinearLayout.LayoutParams(550, 1300));
                            layout.setGravity(Gravity.CENTER);

                            TextView Heroe = new TextView(getContext());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(480, 50);
                            layoutParams.setMargins(30,30,30,15);
                            Heroe.setLayoutParams(layoutParams);
                            Heroe.setText(p.getHeroe());

                            Heroe.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15+tama);
                            Heroe.setTextColor(c4);
                            Heroe.setBackgroundColor(c1);
                            Heroe.setGravity(Gravity.CENTER);

                            layout.addView(Heroe);

                            TextView Descripcion = new TextView(getContext());
                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(480, 854);
                            layoutParams1.setMargins(30,15,30,30);
                            Descripcion.setLayoutParams(layoutParams1);
                            Descripcion.setText(p.getDescripcionES());
                            Descripcion.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12+tama);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                Descripcion.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                            }
                            Descripcion.setTextColor(c3);
                            Descripcion.setBackgroundColor(c1);

                            layout.addView(Descripcion);

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
                }
            });
            mCurlView.setCurrentIndex(index);
            btnDetalle.setBackgroundColor(c3);
            btnDetalle.setTextColor(c1);
            btnDetalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "->"+mCurlView.getCurrentIndex(), Toast.LENGTH_SHORT).show();
                }
            });
//            Toast.makeText(getContext(), mCurlView.getCurrentIndex(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return view;
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