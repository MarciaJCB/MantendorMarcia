package com.example.crudsqlite_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private Button btnListar, btnRegistrar, btnBuscar;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private TextView txtUbicacion;
    private ProgressBar progressBar;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        obtainLocation();
    }

    private void init() {
        context = getApplicationContext();
        btnRegistrar = findViewById(R.id.btnregistrar);
        btnBuscar = findViewById(R.id.btnbuscar);
        btnListar = findViewById(R.id.btnlistar);
        progressBar = findViewById(R.id.progressBar);
        txtUbicacion = findViewById(R.id.txtUbicacion);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void obtainLocation() {
        // Mostrar ProgressBar mientras se obtiene la ubicación
        progressBar.setVisibility(View.VISIBLE);

        // Verificar si se tienen los permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            // Solicitar actualizaciones de ubicación
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        // Actualizar la interfaz de usuario con la ubicación
                        updateLocationUI(location);

                        // Ocultar ProgressBar después de obtener la ubicación
                        progressBar.setVisibility(View.GONE);
                    });
        }
    }

    private void updateLocationUI(Location location) {
        if (location != null) {
            // Convertir las coordenadas de la ubicación a una dirección legible
            String address = getAddressFromLocation(location);
            txtUbicacion.setText(address);
        } else {
            txtUbicacion.setText("Ubicación no disponible");
        }
    }

    private String getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1
            );

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                // Formatear la dirección como desees
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Ubicación no disponible";
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Verificar si se concedieron los permisos
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si se concedieron los permisos, obtener la ubicación
                obtainLocation();
            } else {
                // Si no se concedieron los permisos, mostrar un mensaje o tomar otra acción
                txtUbicacion.setText("Permiso de ubicación denegado");
            }
        }
    }

    @Override
    public void onClick(View view) {
        final int btnRegistrarId = R.id.btnregistrar;
        final int btnListarId = R.id.btnlistar;
        final int btnBuscarId = R.id.btnbuscar;

        int viewId = view.getId();

        if (viewId == btnRegistrarId) {
            Toast.makeText(context, "Registrar", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, GestionarLibroActivity.class);
            Bundle bolsa = new Bundle();
            bolsa.putInt("id", 0); // Como es igual a Cero 0 significa que es un valor nuevo que se va a registrar
            i.putExtras(bolsa);

            startActivity(i);
        } else if (viewId == btnListarId) {
            Toast.makeText(context, "Listar", Toast.LENGTH_SHORT).show();

            // Mostrar ProgressBar al hacer clic en el botón
            progressBar.setVisibility(View.VISIBLE);

            // Simular una operación de carga de 2 segundos antes de pasar a la lista
            new Handler().postDelayed(() -> {
                // Ocultar ProgressBar después de la operación de carga
                progressBar.setVisibility(View.GONE);

                // Iniciar la actividad ListadoLibrosActivity después del retraso
                Intent i2 = new Intent(context, ListadoLibrosActivity.class);
                startActivity(i2);
            }, 2000); // Simulación de una operación de carga que toma 2 segundos
        } else if (viewId == btnBuscarId) {
            Toast.makeText(context, "Buscar", Toast.LENGTH_SHORT).show();
            Intent i3 = new Intent(context, BuscarLibroActivity.class);
            startActivity(i3);
        }
    }
}
