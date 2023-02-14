package com.example.aplicacion_quimica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicacion_quimica.BD.DbHelper;
import com.example.aplicacion_quimica.clases.EncryptMD5;

import java.util.ArrayList;

public class Identificador extends AppCompatActivity {
    private DbHelper based;
    private ArrayList<String> nombre, contrasenna;
    private String nombre_t, contrasenna_t;
    private EditText nombre_texto, contrasenna_texto;
    private SQLiteDatabase db;
    private Button crearUsuario, aceptar;
    private Intent intento;
    private EncryptMD5 encryptMD5;

    private Fragmento_Creador_Usuarios fragmento_Creador = new Fragmento_Creador_Usuarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificador);
        based = new DbHelper(getApplicationContext());
        db = based.getWritableDatabase();
        based.inSustancias();
        encryptMD5 = new EncryptMD5();

        nombre_texto = findViewById(R.id.idetUsuario2);
        contrasenna_texto = findViewById(R.id.idedtContrasenna2);

        nombre = new ArrayList<>();
        contrasenna = new ArrayList<>();


        crearUsuario = findViewById(R.id.idbtCrearUsuario);
        aceptar = findViewById(R.id.idbtnAceptar);
        intento = new Intent(getApplicationContext(), Lista_Operaciones.class);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        crearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmento_Creador.show(getSupportFragmentManager(), "CrearUsuarios");
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre_t = nombre_texto.getText().toString();
                contrasenna_t = contrasenna_texto.getText().toString();
                recu_tabla_usuario();
                boolean mostrar = true;
                for (int i = 0; i < nombre.size(); i++) {
                    if (nombre.get(i).equals(nombre_t) && contrasenna.get(i).equals(encryptMD5.encriptacion(contrasenna_t))) {
                        intento.putExtra("Usuario",nombre_t);
                        startActivity(intento);
                        finish();
                        mostrar = false;
                    }
                }
                guardarUsuario();
                cargarPreferencias();
                if (mostrar) {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectas!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cargarPreferencias(){
        SharedPreferences prefencias = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String usuario = prefencias.getString("usuario","No hay usuario guardado");
    }

    private void guardarUsuario(){
        SharedPreferences prefencias = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefencias.edit();
        editor.putString("usuario", nombre_t);
        editor.commit();
    }

    public void recu_tabla_usuario() {
        Cursor cursor = based.leerUsuarios();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No hay usuarios", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                nombre.add(cursor.getString(0));
                contrasenna.add(cursor.getString(1));
            }
        }
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
}