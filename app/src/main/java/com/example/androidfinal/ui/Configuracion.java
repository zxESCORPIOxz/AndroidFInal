package com.example.androidfinal.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfinal.MainActivity;
import com.example.androidfinal.R;

import static android.content.Context.MODE_PRIVATE;

public class Configuracion extends Fragment {

    int c1,c2,c3,c4,c5,f;
    int sise=2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);
        Switch swtaltocontraste = view.findViewById(R.id.swtAltocontraste);
        TextView txtFuente = view.findViewById(R.id.txtFuente);
        TextView txtCorreo = view.findViewById(R.id.txtCorreo);
        TextView txtNombre = view.findViewById(R.id.txtUserName);
        EditText edtCorreo = view.findViewById(R.id.edtCorreo);
        EditText edtNombre = view.findViewById(R.id.edtNombre);
        Button btnF1 = view.findViewById(R.id.btnFuente1);
        Button btnF2 = view.findViewById(R.id.btnFuente2);
        Button btnF3 = view.findViewById(R.id.btnFuente3);
        Button btnSave = view.findViewById(R.id.btnGuardar);
        SharedPreferences sp = getContext().getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        c1=Color.parseColor(sp.getString("Color1", "#3552f2"));
        c2=Color.parseColor(sp.getString("Color2", "#162d59"));
        c3=Color.parseColor(sp.getString("Color3", "#f2c53d"));
        c4=Color.parseColor(sp.getString("Color4", "#f2b05e"));
        c5=Color.parseColor(sp.getString("Color5", "#f27830"));
        edtCorreo.setText(sp.getString("Correo", "Anonimo"));
        edtNombre.setText(sp.getString("Nombre", "Anonimo"));
        f = Integer.parseInt(sp.getString("Fuente", "2"));
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        if(sharedPreferences.getString("Theme","1").equals("1")){
            swtaltocontraste.setChecked(true);
        }else {
            swtaltocontraste.setChecked(false);
        }
        swtaltocontraste.setTextColor(c3);
        txtFuente.setTextColor(c3);
        txtCorreo.setTextColor(c3);
        txtNombre.setTextColor(c3);
        edtCorreo.setTextColor(c2);
        edtNombre.setTextColor(c2);
        btnF1.setTextColor(c3);
        btnF1.setBackgroundColor(c5);
        btnF2.setTextColor(c3);
        btnF2.setBackgroundColor(c5);
        btnF3.setTextColor(c3);
        btnF3.setBackgroundColor(c5);
        btnSave.setTextColor(c3);
        btnSave.setBackgroundColor(c5);
        switch(f){
            case 1:{
                btnF1.setTextColor(c1);
                btnF1.setBackgroundColor(c3);
                break;
            }
            case 2:{
                btnF2.setTextColor(c1);
                btnF2.setBackgroundColor(c3);
                break;
            }
            case 3:{
                btnF3.setTextColor(c1);
                btnF3.setBackgroundColor(c3);
                break;
            }
            default:{
                Toast.makeText(getContext(), "Fuente alterada", Toast.LENGTH_SHORT).show();
            }
        }
        view.setBackgroundColor(c1);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        btnF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=1;
                btnF1.setTextColor(c1);
                btnF1.setBackgroundColor(c3);
                btnF2.setTextColor(c3);
                btnF2.setBackgroundColor(c5);
                btnF3.setTextColor(c3);
                btnF3.setBackgroundColor(c5);
            }
        });
        btnF2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=2;
                btnF1.setTextColor(c3);
                btnF1.setBackgroundColor(c5);
                btnF2.setTextColor(c1);
                btnF2.setBackgroundColor(c3);
                btnF3.setTextColor(c3);
                btnF3.setBackgroundColor(c5);
            }
        });
        btnF3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=3;
                btnF1.setTextColor(c3);
                btnF1.setBackgroundColor(c5);
                btnF2.setTextColor(c3);
                btnF2.setBackgroundColor(c5);
                btnF3.setTextColor(c1);
                btnF3.setBackgroundColor(c3);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swtaltocontraste.isChecked()){
                    editor.putString("Theme", "1");
                    editor.putString("Color1", "#3552f2");
                    editor.putString("Color2", "#162d59");
                    editor.putString("Color3", "#f2c53d");
                    editor.putString("Color4", "#f2b05e");
                    editor.putString("Color5", "#f27830");
                    editor.putString("Correo", edtCorreo.getText().toString());
                    editor.putString("Nombre", edtNombre.getText().toString());
                    editor.putString("Fuente", ""+f);
                    editor.apply();
                }else{
                    editor.putString("Theme", "2");
                    editor.putString("Color1", "#081459");
                    editor.putString("Color2", "#010D26");
                    editor.putString("Color3", "#0762D9");
                    editor.putString("Color4", "#07DBF2");
                    editor.putString("Color5", "#A64724");
                    editor.putString("Correo", edtCorreo.getText().toString());
                    editor.putString("Nombre", edtNombre.getText().toString());
                    editor.putString("Fuente", ""+f);
                    editor.apply();
                }
                Toast.makeText(getContext(), "Guardado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}