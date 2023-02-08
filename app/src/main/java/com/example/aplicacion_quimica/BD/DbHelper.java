package com.example.aplicacion_quimica.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "registro.db";
    public static final String TABLE_USUARIOS = "t_usuarios";
    public static final String TABLE_REGISTROS = "t_registros";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_USUARIOS +
                "(nombre varchar(10) PRIMARY KEY," +
                "contrasenna varchar(10) NOT NULL)");

        db.execSQL("CREATE TABLE "+ TABLE_REGISTROS +
                "(nombre varchar(10) PRIMARY KEY REFERENCES t_usuarios(nombre)," +
                "sustancia varchar(5) NOT NULL,"+
                "concentracion varchar(5) NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE " + TABLE_REGISTROS);
        onCreate(db);
    }
}
