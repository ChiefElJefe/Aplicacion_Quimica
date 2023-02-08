package com.example.aplicacion_quimica;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aplicacion_quimica.BD.DbHelper;

public class Identificador extends AppCompatActivity {
    private DbHelper based;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificador);
        based = new DbHelper(getApplicationContext());
        db = based.getWritableDatabase();
        if (db != null) {
            Toast.makeText(getApplicationContext(), "Base de datos creada", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error al crear la base de datos", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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