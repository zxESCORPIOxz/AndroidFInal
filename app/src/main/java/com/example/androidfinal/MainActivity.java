package com.example.androidfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    int c1,c2,c3,c4,c5,tema,f;
    DatabaseReference df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        df= FirebaseDatabase.getInstance().getReference();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sp = getSharedPreferences("ARCHIVOREG", MODE_PRIVATE);
        c1=Color.parseColor(sp.getString("Color1", "#3552f2"));
        c2=Color.parseColor(sp.getString("Color2", "#162d59"));
        c3=Color.parseColor(sp.getString("Color3", "#f2c53d"));
        c4=Color.parseColor(sp.getString("Color4", "#f2b05e"));
        c5=Color.parseColor(sp.getString("Color5", "#f27830"));
        tema= Integer.parseInt(sp.getString("Theme", "1"));
        f = Integer.parseInt(sp.getString("Fuente", "2"));
        if(tema==1){
            switch(f){
                case 1:{
                    setTheme(R.style.AppThemeA);
                    break;
                }
                case 2:{
                    setTheme(R.style.AppTheme);
                    break;
                }
                case 3:{
                    setTheme(R.style.AppThemeB);
                    break;
                }
                default:{
                    Toast.makeText(this, "Fuente alterada", Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            switch(f){
                case 1:{
                    setTheme(R.style.AppTheme2A);
                    break;
                }
                case 2:{
                    setTheme(R.style.AppTheme2);
                    break;
                }
                case 3:{
                    setTheme(R.style.AppTheme2B);
                    break;
                }
                default:{
                    Toast.makeText(this, "Fuente alterada", Toast.LENGTH_SHORT).show();
                }
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setBackgroundColor(c2);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_noticias, R.id.nav_Configuracion, R.id.nav_Post)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}