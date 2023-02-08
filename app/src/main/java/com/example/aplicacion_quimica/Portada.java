package com.example.aplicacion_quimica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Portada extends AppCompatActivity {

    private TextView contador;
    private Intent intent;
    static final int TIEMPO = 5000;
    private Timer timer;
    private Button botonEntrar;
    private Handler manejador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contador = findViewById(R.id.idtxtContador);
        intent = new Intent(getApplicationContext(), Identificador.class);
        timer = new Timer();
        botonEntrar = findViewById(R.id.idbtEntrar);
        manejador = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 3;
            @Override
            public void run() {
                contador.setText("" + i);
                i--;
                if (i < 0) {
                    timer.cancel();
                }
            }
        },0, 1000);

        timer.scheduleAtFixedRate(new TimerTask() {
            float i = 3.0f;
            ImageView imagenPortada = findViewById(R.id.idimgCarga);
            boolean entrada = true;
            @Override
            public void run() {
                if (entrada){
                    imagenPortada.setImageResource(R.drawable.matraces_derecha);
                    entrada = false;
                }else{
                    imagenPortada.setImageResource(R.drawable.matraces_izquierda);
                    entrada = true;
                }
                i = i - 0.5f;
                if (i < 0) {
                    timer.cancel();
                }
            }
        },0, 500);


        manejador.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, TIEMPO);

        botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Importante borrar el hilo del manejador
                manejador.removeCallbacksAndMessages(null);
                startActivity(intent);
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