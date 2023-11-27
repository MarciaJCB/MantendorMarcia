package com.example.crudsqlite_v01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudsqlite_v01.controladores.LibroBD;
import com.example.crudsqlite_v01.modelos.Libro;

public class GestionarLibroActivity extends AppCompatActivity implements View.OnClickListener{

    Context context;
    EditText txttitulo, txtsubtitulo, txtisbn, txtautor, txtaniopublicacion, txtprecio;
    int id;
    LibroBD libroBD;
    Button btnguardar, btnactualizar, btnborrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_libro);
        init();
    }

    private void init(){
        context = getApplicationContext();
        txttitulo = findViewById(R.id.ges_txttitulo);
        txtsubtitulo = findViewById(R.id.ges_txtsubtitulo);
        txtisbn = findViewById(R.id.ges_txtisbn);
        txtautor = findViewById(R.id.ges_txtautor);
        txtaniopublicacion = findViewById(R.id.ges_txtaniopublicacion);
        txtprecio = findViewById(R.id.ges_txtprecio);

        btnguardar = findViewById(R.id.ges_btnguardar);
        btnactualizar = findViewById(R.id.ges_btnactualizar);
        btnborrar = findViewById(R.id.ges_btnborrar);

        Intent i = getIntent();
        Bundle bolsa = i.getExtras();
        id = bolsa.getInt("id");
        if(id != 0){
            txttitulo.setText(bolsa.getString("titulo"));
            txtsubtitulo.setText(bolsa.getString("subtitulo"));
            txtisbn.setText(bolsa.getString("isbn"));
            txtautor.setText(bolsa.getString("autor"));
            txtaniopublicacion.setText(bolsa.getInt("anio_publicacion")+"");
            txtprecio.setText(bolsa.getDouble("precio")+"");
            btnguardar.setEnabled(false);
        }else{
            btnactualizar.setEnabled(false);
            btnborrar.setEnabled(false);
        }
    }

    private void limpiarCampos(){
        id = 0;
        txttitulo.setText("");
        txtsubtitulo.setText("");
        txtautor.setText("");
        txtisbn.setText("");
        txtaniopublicacion.setText("");
        txtprecio.setText("");
    }

    private Libro llenarDatosLibro(){
        Libro libro = new Libro();
        String t = txttitulo.getText().toString();
        String s = txtsubtitulo.getText().toString();
        String i = txtisbn.getText().toString();
        String a = txtautor.getText().toString();
        String anio = txtaniopublicacion.getText().toString();
        String precio = txtprecio.getText().toString();

        libro.setId(id);
        libro.setTitulo(t);
        libro.setSubtitulo(s);
        libro.setIsbn(i);
        libro.setAutor(a);
        libro.setAnioPublicacion(Integer.parseInt(anio));
        libro.setPrecio(Double.parseDouble(precio));

        return libro;
    }
    
    private void guardar(){
        libroBD = new LibroBD(context, "LibrosBD.db",null,1);
        Libro libro = llenarDatosLibro();
        if(id==0){
            libroBD.agregar(libro);
            Toast.makeText(context, "Libro Guardado Listo!", Toast.LENGTH_SHORT).show();
        }else{
            libroBD.actualizar(id, libro);
            btnactualizar.setEnabled(false);
            btnborrar.setEnabled(false);
            Toast.makeText(context, "Libro Actualizado Listo!", Toast.LENGTH_SHORT).show();
        }
    }

    private void borrar() {
        libroBD = new LibroBD(context, "LibrosBD.db", null, 1);
        if (id == 0) {
            Toast.makeText(context, "No es posible borrar", Toast.LENGTH_SHORT).show();
        } else {
            libroBD.borrar(id); // Corregir la llamada al método borrar en lugar de actualizar
            limpiarCampos();
            btnguardar.setEnabled(true);
            btnactualizar.setEnabled(false);
            btnborrar.setEnabled(false);
            Toast.makeText(context, "Libro Borrado!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        final int btnGuardar = R.id.ges_btnguardar;
        final int btnActulizar = R.id.ges_btnactualizar;
        final int btnBorrar = R.id.ges_btnborrar;

        int viewId = view.getId();

        if (viewId == btnGuardar) {
            guardar();
            //Toast.makeText(context, "Libro Guardado con éxito!", Toast.LENGTH_SHORT).show();
        } else if (viewId == btnActulizar) {
            guardar();
            //Toast.makeText(context, "Libro Actualizado con éxito!", Toast.LENGTH_SHORT).show();
        } else if (viewId == btnBorrar) {
            borrar();
            //Toast.makeText(context, "Libro Borrado con éxito!", Toast.LENGTH_SHORT).show();
        }
    }
}