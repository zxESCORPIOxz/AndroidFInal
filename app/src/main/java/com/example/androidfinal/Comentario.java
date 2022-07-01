package com.example.androidfinal;

import java.util.Date;

public class Comentario {
    String idComentario,Correo,Username,Comentario;
    String Fecha;

    public Comentario(String idComentario, String correo, String username, String comentario, String fecha) {
        this.idComentario = idComentario;
        Correo = correo;
        Username = username;
        Comentario = comentario;
        Fecha = fecha;
    }

    public String getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(String idComentario) {
        this.idComentario = idComentario;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
