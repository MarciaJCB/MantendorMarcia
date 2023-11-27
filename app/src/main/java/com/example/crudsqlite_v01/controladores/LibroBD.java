package com.example.crudsqlite_v01.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.crudsqlite_v01.modelos.Libro;

import java.util.ArrayList;
import java.util.List;

public class LibroBD extends SQLiteOpenHelper implements ILibroBD {
    Context contexo;
    private List<Libro> librosList = new ArrayList<>();

    public LibroBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version); //Estos argumentos le llegan a la clase superior SQLiteOpenHelper (son los argumentos que necesita la clase)
        this.contexo = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //Cual es la estructura de la base de datos y los datos iniciales que va a contener ella (es como la primera versión de la base de datos)
        String sql = "CREATE TABLE libros ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "titulo TEXT, "+
                "subtitulo TEXT, "+
                "isbn TEXT, "+
                "autor TEXT, "+
                "anio INTEGER, "+
                "precio REAL)";
        sqLiteDatabase.execSQL(sql);

        String insert = "INSERT INTO libros VALUES (null, "+
                "'Como programar en Java', "+
                "'Más de 80 ejemplos', "+
                "'987654321', "+
                "'Deitel & Deitel', "+
                "2008, "+
                "145000)";
        sqLiteDatabase.execSQL(insert);

        insert = "INSERT INTO libros VALUES (null, "+
                "'El gran libro de Android', "+
                "'Android con ejemplos', "+
                "'123456789', "+
                "'Felipe Belmar', "+
                "2020, "+
                "135000)";
        sqLiteDatabase.execSQL(insert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public Libro elemento(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM libros WHERE _id=" + id;
        Cursor cursor = database.rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraerLibro(cursor);
            else
                return null;
        } catch (Exception e) {
            Log.d("TAG", "Error elemento(id) LibroBD" + e.getMessage());
            throw e;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    private Libro extraerLibro(Cursor cursor) {
        Libro libro = new Libro();
        libro.setId(cursor.getInt(0));
        libro.setTitulo(cursor.getString(1));
        libro.setSubtitulo(cursor.getString(2));
        libro.setIsbn(cursor.getString(3));
        libro.setAutor(cursor.getString(4));
        libro.setAnioPublicacion(cursor.getInt(5));
        libro.setPrecio(cursor.getDouble(6));
        return libro;
    }

    @Override
    public Libro elemento(String title) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM libros WHERE titulo = '" + title + "'";
        Cursor cursor = database.rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraerLibro(cursor);
            else
                return null;
        } catch (Exception e) {
            Log.d("TAG", "Error elemento(title) LibroBD" + e.getMessage());
            throw e;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public List<Libro> lista() { //Debe devolver el conjunto de elementos que se encontraron en la base de datos
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM libros ORDER BY titulo ASC";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                librosList.add( //Cada uno que se vaya creando se adicionando a la lista de libros (libroslist)
                        new Libro(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getInt(5),
                                cursor.getDouble(6))
                );
            }while(cursor.moveToNext());
        }
        cursor.close();
        return librosList;
    }

    @Override
    public void agregar(Libro book) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", book.getTitulo());
        values.put("subtitulo", book.getSubtitulo());
        values.put("isbn", book.getIsbn());
        values.put("autor", book.getAutor());
        values.put("anio", book.getAnioPublicacion());
        values.put("precio", book.getPrecio());
        database.insert("libros", null, values);
    }

    @Override
    public void actualizar(int id, Libro book) {
        SQLiteDatabase database = getWritableDatabase();
        String[] parametros = {String.valueOf(id)};

        ContentValues values = new ContentValues();
        values.put("titulo", book.getTitulo());
        values.put("subtitulo", book.getSubtitulo());
        values.put("isbn", book.getIsbn());
        values.put("autor", book.getAutor());
        values.put("anio", book.getAnioPublicacion());
        values.put("precio", book.getPrecio());

        database.update("libros", values, "_id=?",parametros);
    }

    @Override
    public void borrar(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String[] parametros = {String.valueOf(id)};

        database.delete("libros","_id=?",parametros);
    }
}//LibroBD
