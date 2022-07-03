package com.example.androidfinal.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.androidfinal.R;

import static android.content.Context.MODE_PRIVATE;


public class FullScrem extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_screm, container, false);

        SharedPreferences sp = getContext().getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        ImageView imageView = view.findViewById(R.id.imgFULL);

        Glide.with(getContext()).load(sp.getString("imgFull","0")).into(imageView);

        return view;
    }
}