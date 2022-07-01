package com.example.androidfinal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PostMLBB {
    String heroe,tipo;
    String DescripcionES,DescripcionEN;
    int Likes;
    ArrayList<Comentario> comentarios = new ArrayList<>();
    ArrayList<String> imagenes = new ArrayList<>();
    String fecha;

    public void setTComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void setTImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcionES() {
        return DescripcionES;
    }

    public void setDescripcionES(String descripcionES) {
        DescripcionES = descripcionES;
    }

    public String getDescripcionEN() {
        return DescripcionEN;
    }

    public void setDescripcionEN(String descripcionEN) {
        DescripcionEN = descripcionEN;
    }

    public PostMLBB() {
        DateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
        fecha = dfDate.format(Calendar.getInstance().getTime());
    }

    public String getHeroe() {
        return heroe;
    }

    public void setHeroe(String heroe) {
        this.heroe = heroe;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(String imagen) {
        this.imagenes.add(imagen);
    }
}
