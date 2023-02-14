package com.example.aplicacion_quimica;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicacion_quimica.BD.DbHelper;
import com.example.aplicacion_quimica.clases.EncryptMD5;


public class Fragmento_Creador_Usuarios extends DialogFragment {

    private Button aceptar;
    private EditText nombre;
    private EditText contrasenna;
    private String nombre_Para_db;
    private String contrasenna_Para_db;
    private EncryptMD5 encryptMD5;
    private Toast Toast1;
    private Toast Toast2;
    private Toast Toast3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_fragmento__creador__usuarios, container, false);
        encryptMD5 = new EncryptMD5();

        aceptar = vista.findViewById(R.id.idbtnAceptar);
        nombre = vista.findViewById(R.id.idetUsuario2);
        contrasenna = vista.findViewById(R.id.idedtContrasenna2);

        Toast1 = Toast.makeText(getContext(), "Uno de los campos esta vacio", Toast.LENGTH_LONG);
        Toast2 = Toast.makeText(getContext(), "Creación exitosa", Toast.LENGTH_LONG);
        Toast3 = Toast.makeText(getContext(), "Error al añadir usuario", Toast.LENGTH_LONG);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre_Para_db = nombre.getText().toString();
                contrasenna_Para_db = contrasenna.getText().toString();
                if (nombre_Para_db.isEmpty() || contrasenna_Para_db.isEmpty()) {
                    Toast1.show();
                } else {
                    long a = new DbHelper(getContext()).anUsuarios(nombre_Para_db, encryptMD5.encriptacion(contrasenna_Para_db));
                    if (a == -1){
                        Toast3.show();
                    }else {
                        Toast2.show();
                        getDialog().dismiss();
                    }
                }
            }
        });

        return vista;
    }


}