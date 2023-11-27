package com.example.crudsqlite_v01.controladores;

import com.example.crudsqlite_v01.modelos.Libro;

import java.util.List;

public interface ILibroBD { //La definición de los metodos que queremos que se ejecuten dentro de la clase que va a manipular los datos con la base de datos
    Libro elemento(int id); //Devuelve el elemento dado su id
    Libro elemento(String title); //Devuelve el elemento dado su titulo exacto
    List<Libro> lista(); //Devuelve una lista con todos los elementos registrados
    void agregar(Libro book); //Añade el elemento indicado
    void actualizar(int id, Libro book); //Actualiza datos del elemento dado su id
    void borrar(int id); //Elimina el elemento indicado con el id
}
