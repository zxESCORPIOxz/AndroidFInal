package com.example.androidfinal;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class vozManager {
    TextToSpeech mt = null;
    boolean val = false;
    Context context;

    public void init(Context context){
        this.context=context;
        try {
            mt = new TextToSpeech(context, onInitListener);
        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private TextToSpeech.OnInitListener onInitListener=new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            Locale leng;
            if (Locale.getDefault().getDisplayLanguage().equals("English")){
                leng=new Locale("en","US");
            }else {
                leng=new Locale("es","PE");
            }
            if (status==TextToSpeech.SUCCESS){
                int res = mt.setLanguage(leng);
                val=true;
                if (res== TextToSpeech.LANG_MISSING_DATA || res==TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    public void agregarCola(String texto,float velT,float velR){
        if(val){
            mt.setPitch(velT);
            mt.setSpeechRate(velR);
            mt.speak(texto,TextToSpeech.QUEUE_ADD,null);
        }
        else Toast.makeText(context, "TTS no inicializado", Toast.LENGTH_SHORT).show();
    }

    public void IniciarCola(String s,float velT,float velR) {
        if (val){
            mt.setPitch(velT);
            mt.setSpeechRate(velR);
            mt.speak(s,TextToSpeech.QUEUE_ADD,null);
        }else {
            Toast.makeText(context, "No esta cargado", Toast.LENGTH_SHORT).show();
        }
    }

    public void apagar() {
        mt.shutdown();
    }
}