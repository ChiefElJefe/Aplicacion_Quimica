package com.example.aplicacion_quimica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aplicacion_quimica.Adaptador.AdapterDatos;
import com.example.aplicacion_quimica.Tarjetas.Tarjeta_Portadas;

import java.util.ArrayList;

public class Lista_Operaciones extends AppCompatActivity {
    private RecyclerView recycler;
    ArrayList<Tarjeta_Portadas> tarjetas;
    AdapterDatos adaptador;
    Intent paso_Concentracion;
    Intent paso_Masa;
    Intent paso_Registro;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_operaciones);
        recycler = findViewById(R.id.idNew);
        SharedPreferences prefencias = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        usuario = prefencias.getString("usuario", "No hay usuario guardado");
        Log.d("Usuario",usuario);
        Toast.makeText(getApplicationContext(), "Abriendo con: " + usuario, Toast.LENGTH_LONG).show();
        paso_Concentracion = new Intent(getApplicationContext(), Convertidor_Concentracion.class);
        paso_Masa = new Intent(getApplicationContext(), Convertidor_Masa.class);
        paso_Registro = new Intent(getApplicationContext(), Registros.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        tarjetas = new ArrayList<>();

        tarjetas.add(new Tarjeta_Portadas("Concentración", R.drawable.concentracion2));
        tarjetas.add(new Tarjeta_Portadas("Masa", R.drawable.masa2));
        tarjetas.add(new Tarjeta_Portadas("Tabla de Calculos", R.drawable.calculos2));
        adaptador = new AdapterDatos(tarjetas);
        recycler.setAdapter(adaptador);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (recycler.getChildAdapterPosition(view)){
                    case 0:
                        startActivity(paso_Concentracion);
                        break;
                    case 1:
                        startActivity(paso_Masa);
                        break;
                    case 2:
                        startActivity(paso_Registro);
                        break;
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}