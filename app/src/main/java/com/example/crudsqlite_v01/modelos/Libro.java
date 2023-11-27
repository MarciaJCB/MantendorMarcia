package com.example.crudsqlite_v01.modelos;

public class Libro {
    //Atributos de la clase libro
    private int id;
    private String titulo, subtitulo, isbn, autor;
    private int anioPublicacion;
    private double precio;

    //Constructor sin argumentos o por defecto o vacío
    public Libro(){

    }

    //Constructor con argumentos
    public Libro(int id, String titutlo, String subititulo, String isbn, String autor, int anioPublicacion, double precio) {
        this.id = id;
        this.titulo = titutlo;
        this.subtitulo = subititulo;
        this.isbn = isbn;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titutlo) {
        this.titulo = titutlo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subititulo) {
        this.subtitulo = subititulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titutlo='" + titulo + '\'' +
                ", subititulo='" + subtitulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", autor='" + autor + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", precio=" + precio +
                '}';
    }

}//Libro
