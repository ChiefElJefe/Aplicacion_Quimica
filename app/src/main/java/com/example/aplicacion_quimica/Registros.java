package com.example.aplicacion_quimica;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicacion_quimica.Adaptador.AdaptadorRegistros;
import com.example.aplicacion_quimica.Adaptador.AdapterDatos;
import com.example.aplicacion_quimica.BD.DbHelper;
import com.example.aplicacion_quimica.clases.Registro;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Registros extends AppCompatActivity {
    private final String CANAL_ID = "Canal_Notificaciones";
    private final int NOT_ID = 0;

    private String usuario;
    private SharedPreferences prefencias;
    private ArrayList<Registro> registro;
    private DbHelper based;
    private RecyclerView recycler;
    private AdaptadorRegistros adaptador;
    private Intent lista;
    private Button volver, csv, xml;
    private String[] permissions = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        if (checkPermission()) {

        } else {
            requestPermission(); // Request Permission
        }

        createNotificationChannel();

        registro = new ArrayList<>();
        based = new DbHelper(getApplicationContext());
        recycler = findViewById(R.id.idReciclerRegistros);
        volver = findViewById(R.id.idbtVolver);
        csv = findViewById(R.id.idbtCSV);
        xml = findViewById(R.id.idbtXML);
        lista = new Intent(getApplicationContext(), Lista_Operaciones.class);


        prefencias = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        usuario = prefencias.getString("usuario", "No hay usuario guardado");
        recu_tabla_registros();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager())
                        Toast.makeText(Registros.this, "We Have Permission", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Registros.this, "You Denied the permission", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Registros.this, "You Denied the permission", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int readCheck = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
            int writeCheck = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            return readCheck == PackageManager.PERMISSION_GRANTED && writeCheck == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            new AlertDialog.Builder(Registros.this)
                    .setTitle("Permission")
                    .setMessage("Please give the Storage permission")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
                                activityResultLauncher.launch(intent);
                            } catch (Exception e) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                activityResultLauncher.launch(intent);
                            }
                        }
                    })
                    .setCancelable(false)
                    .show();

        } else {

            ActivityCompat.requestPermissions(Registros.this, permissions, 30);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void createSimpleNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CANAL_ID)
                .setSmallIcon(R.drawable.calculos2)
                .setContentTitle("Fichero Guardado")
                .setContentText("Su fichero a sido guardado en su almacenamiento externo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOT_ID, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CANAL_ID, "MiCanal", importance);
            channel.setDescription("Notificacion para guardado de archivos");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adaptador = new AdaptadorRegistros(registro);
        recycler.setAdapter(adaptador);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(lista);
                finish();
            }
        });

        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ex = Environment.getExternalStorageState();
                Log.d("Esta", ""+ex.equals(Environment.getExternalStoragePublicDirectory("Archivos_quimica")));
                try {
                    File dir= Environment.getExternalStoragePublicDirectory("Archivos_quimica");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File fichero = new File(dir, usuario+".csv");
                    PrintWriter escritura = new PrintWriter(fichero);
                    for (int i = 0; i < registro.size(); i++) {
                        escritura.write(registro.get(i).getSustancia()+","+registro.get(i).getConcentracion()+","+registro.get(i).getMasa()+","+registro.get(i).getVolumen()+"\n");
                    }
                    escritura.close();
                    createSimpleNotification();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error en el guardado", Toast.LENGTH_LONG).show();
                }


            }
        });

        xml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    File dir= Environment.getExternalStoragePublicDirectory("Archivos_quimica");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                    Document doc = docBuilder.newDocument();
                    Element rootElement = doc.createElement("registro");
                    doc.appendChild(rootElement);

                    for (int i = 0; i < registro.size(); i++) {
                        Element susta = doc.createElement("Sustancia");
                        susta.setTextContent(registro.get(i).getSustancia());
                        rootElement.appendChild(susta);
                        Element conc = doc.createElement("Concentracion");
                        conc.setTextContent(registro.get(i).getConcentracion());
                        rootElement.appendChild(conc);
                        Element mas = doc.createElement("Masa");
                        mas.setTextContent(registro.get(i).getMasa());
                        rootElement.appendChild(mas);
                        Element volu = doc.createElement("Volumen");
                        volu.setTextContent(registro.get(i).getVolumen());
                        rootElement.appendChild(volu);
                    }

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File(dir, usuario+".xml"));
                    transformer.transform(source, result);
                    createSimpleNotification();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error en el guardado", Toast.LENGTH_LONG).show();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void recu_tabla_registros() {
        Cursor cursor = based.leerRegistros();
        try {
            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
            } else {
                while (cursor.moveToNext()) {
                    if (usuario.equals(cursor.getString(0))) {
                        registro.add(new Registro(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
        }


    }
}