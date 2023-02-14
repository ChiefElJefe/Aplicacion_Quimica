package com.example.aplicacion_quimica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion_quimica.BD.DbHelper;
import com.example.aplicacion_quimica.clases.Sustancia;

import java.util.ArrayList;

public class Convertidor_Masa extends AppCompatActivity {
    private Button calcular, volver, registros;
    private Intent lista, paso_Registros;
    private String usuario, sust;
    private SharedPreferences prefencias;
    private DbHelper based;
    private ArrayList<Sustancia> sustancia;

    private Spinner sustancias_eleccion;
    private double masa, volumen, mr, concentracion;
    private EditText vol, mas;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertidor_masa);
        calcular = findViewById(R.id.idbtCalcular2);
        volver = findViewById(R.id.idbtVolver2);
        registros = findViewById(R.id.idbtReg);
        vol = findViewById(R.id.idetVolumenConcentracion);
        mas = findViewById(R.id.idetMasaConcentracion);
        resultado = findViewById(R.id.idtxtMasaMasa);
        based = new DbHelper(getApplicationContext());
        sustancia = new ArrayList<>();


        lista = new Intent(getApplicationContext(), Lista_Operaciones.class);
        paso_Registros = new Intent(getApplicationContext(), Registros.class);

        prefencias = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        usuario = prefencias.getString("usuario", "No hay usuario guardado");
        sustancias_eleccion = (Spinner) findViewById(R.id.idSPmasa);
        recu_tabla_usuario();
        ArrayList<String> listaSpinner_sustancias = new ArrayList<>();
        for (int i = 0; i < sustancia.size(); i++) {
            listaSpinner_sustancias.add(sustancia.get(i).getSustancia());
        }

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, listaSpinner_sustancias);
        sustancias_eleccion.setAdapter(adaptador);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sustancias_eleccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < sustancia.size(); i++) {
                    if (parent.getItemAtPosition(position).toString().equals(sustancia.get(i).getSustancia())) {
                        sust = sustancia.get(i).getSustancia();
                        mr = sustancia.get(i).getMr();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masa = Double.parseDouble(mas.getText().toString());
                volumen = Double.parseDouble(vol.getText().toString());

                Double resul = masa * mr * volumen;
                String result = resul + "g";
                resultado.setText(result);
                based.anRegistros(usuario, sust, mas.getText().toString() + "M", result, vol.getText().toString() + "cm3");
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(lista);
                finish();
            }
        });

        registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(paso_Registros);
                finish();
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void recu_tabla_usuario() {
        Cursor cursor = based.leerSustancias();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No hay usuarios", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                sustancia.add(new Sustancia(cursor.getString(0), cursor.getDouble(1)));
            }
        }
    }

}