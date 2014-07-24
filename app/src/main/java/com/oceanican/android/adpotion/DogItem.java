package com.oceanican.android.adpotion;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rojo on 15/07/2014.
 */
public class DogItem implements Serializable {

    private String genero;
    private String situacion;
    private String nombre;
    private String codigo;
    private Boolean desparcitado;
    private String fecha;
    private String color;
    private String descripcion;
    private String edad;
    private String foto;
    private String tamanio;
    private String enegria;


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getDesparcitado() {
        return desparcitado;
    }

    public void setDesparcitado(Boolean desparcitado) {
        this.desparcitado = desparcitado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) throws ParseException {

        final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        final String NEW_FORMAT = "dd/MM/yyyy";
        // August 12, 2010
        String newDateString;
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = sdf.parse(fecha);
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);


        this.fecha =newDateString;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamaño) {
        this.tamanio = tamaño;
    }

    public String getEnegria() {
        return enegria;
    }

    public void setEnegria(String enegria) {
        this.enegria = enegria;
    }
}
