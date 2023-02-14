package com.example.aplicacion_quimica.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NOMBRE = "registro.db";
    public static final String TABLE_USUARIOS = "t_usuarios";
    public static final String TABLE_REGISTROS = "t_registros";
    public static final String TABLE_SUSTANCIAS = "t_sustancias";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_USUARIOS +
                "(nombre varchar(10) PRIMARY KEY," +
                "contrasenna varchar(50) NOT NULL)");

        db.execSQL("CREATE TABLE "+ TABLE_REGISTROS +
                "(nombre varchar(10) REFERENCES t_usuarios(nombre)," +
                "sustancia varchar(5) NOT NULL,"+
                "concentracion varchar(5) NOT NULL,"+
                "masa varchar(5) not null,"+
                "volumen varchar(5) not null," +
                "id integer primary key autoincrement)");

        db.execSQL("CREATE TABLE "+ TABLE_SUSTANCIAS +
                "(simbolo varchar(10) PRIMARY KEY," +
                "mr double(10) NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE " + TABLE_REGISTROS);
        db.execSQL("DROP TABLE " + TABLE_SUSTANCIAS);
        onCreate(db);
    }

    public long anUsuarios(String usuario, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insercion = new ContentValues();

        insercion.put("nombre", usuario);
        insercion.put("contrasenna", password);
        return  db.insert(TABLE_USUARIOS, null, insercion);

    }

    public void inSustancias(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insercion = new ContentValues();

        insercion.put("simbolo", "HCl");
        insercion.put("mr", 36.459);
        db.insert(TABLE_SUSTANCIAS, null, insercion);
        insercion.put("simbolo", "NaOH");
        insercion.put("mr", 39.997);
        db.insert(TABLE_SUSTANCIAS, null, insercion);
        insercion.put("simbolo", "NaCl");
        insercion.put("mr", 58.440);
        db.insert(TABLE_SUSTANCIAS, null, insercion);
        insercion.put("simbolo", "H2SO4");
        insercion.put("mr", 98.079);
        db.insert(TABLE_SUSTANCIAS, null, insercion);

    }

    public long anRegistros(String usuario, String sustancia, String concentracion, String masa, String volumen){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insercion = new ContentValues();

        insercion.put("nombre", usuario);
        insercion.put("sustancia", sustancia);
        insercion.put("concentracion", concentracion);
        insercion.put("masa", masa);
        insercion.put("volumen", volumen);
        return  db.insert(TABLE_REGISTROS, null, insercion);

    }

    public Cursor leerUsuarios(){
        String busqueda = "select * from " + TABLE_USUARIOS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(busqueda, null);
        }
        return cursor;
    }

        public Cursor leerRegistros(){
        String busqueda = "select * from " + TABLE_REGISTROS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(busqueda, null);
        }
        return cursor;
    }

    public Cursor leerSustancias(){
        String busqueda = "select * from " + TABLE_SUSTANCIAS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(busqueda, null);
        }
        return cursor;
    }
}
