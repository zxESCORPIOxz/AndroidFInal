package com.example.androidfinal.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.androidfinal.Comentario;
import com.example.androidfinal.PostMLBB;
import com.example.androidfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Postear extends Fragment {
    DatabaseReference df = FirebaseDatabase.getInstance().getReference();;
    Button btnSubir;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_postear, container, false);
        btnSubir = view.findViewById(R.id.btnSubir);
//        PostMLBB postMLBB = new PostMLBB();
//        postMLBB.setHeroe("Ling");
//        postMLBB.setTipo("Nerf");
//        postMLBB.setDescripcionES("[Ling] (↓)\n" +
//                "Con su poder crítico mejorado, Ling ha estado representando una amenaza demasiado grande para los objetivos de bajo HP. Estamos reduciendo su daño para darles a las víctimas la oportunidad de escapar.\n" +
//                "\n" +
//                "[Habilidad 2] (↓)\n" +
//                "Daño: 300-400 + 40% Ataque Físico >> 250-350 + 33% Ataque Físico");
//        postMLBB.setDescripcionEN("[Ling] (↓)\n" +
//                "With his enhanced critical power, Ling's been posing too high a threat to low-HP targets. We're lowering his damage to give the victims a chance to get away.\n" +
//                "\n" +
//                "[Skill 2] (↓)\n" +
//                "Damage: 300-400 + 40% Physical Attack >> 250-350 + 33% Physical Attack");
//        postMLBB.setLikes(0);
//        postMLBB.setImagenes("https://i.pinimg.com/originals/b0/8b/7b/b08b7bf14e2bcbd39afb9ff63241325f.jpg");
//        DateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
//        postMLBB.setComentarios(new Comentario("000000001","mycorreo@gmail.com","User1","Genial",dfDate.format(Calendar.getInstance().getTime())));
//        btnSubir.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                    df.child("PostMLBB").push().setValue(postMLBB);
//                }catch (Exception e){
//                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        return view;
    }
}