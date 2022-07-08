package com.example.androidfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    ImageView fonfo;
    TextView logotexto,txtbienvenida;
    Button btn_omitir;
    int c1,c2,c3,c4,c5,tema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sp = getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        c1= Color.parseColor(sp.getString("Color1", "#3552f2"));
        c2=Color.parseColor(sp.getString("Color2", "#162d59"));
        c3=Color.parseColor(sp.getString("Color3", "#f2c53d"));
        c4=Color.parseColor(sp.getString("Color4", "#f2b05e"));
        c5=Color.parseColor(sp.getString("Color5", "#f27830"));
        tema= Integer.parseInt(sp.getString("Theme", "1"));

        logotexto = findViewById(R.id.txtLogoTextoSplahs);
        fonfo = findViewById(R.id.imgfondo);
        if(tema==1){
            fonfo.setBackgroundResource(R.drawable.side_nav_bar);
        }else {
            fonfo.setBackgroundResource(R.drawable.side_nav_bar2);
        }
        txtbienvenida = findViewById(R.id.txtBienvenida);
        btn_omitir = findViewById(R.id.btn_omitir);
        btn_omitir.setBackgroundColor(c3);
        final Timer timer = new Timer("MY_TIMER");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent( Splash.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        },3000);
        btn_omitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timer.purge();
                Intent i = new Intent( Splash.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}