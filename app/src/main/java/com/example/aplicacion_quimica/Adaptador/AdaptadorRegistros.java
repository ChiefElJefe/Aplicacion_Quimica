package com.example.aplicacion_quimica.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion_quimica.R;
import com.example.aplicacion_quimica.Tarjetas.Tarjeta_Portadas;
import com.example.aplicacion_quimica.clases.Registro;

import java.util.ArrayList;

public class AdaptadorRegistros extends RecyclerView.Adapter<AdaptadorRegistros.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<Registro> registro;
    private View.OnClickListener escucha;

    public AdaptadorRegistros(ArrayList<Registro> registro) {
        this.registro = registro;
    }

    @NonNull
    @Override
    public AdaptadorRegistros.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registros_card, null, false);

        view.setOnClickListener(this);
        return new AdaptadorRegistros.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRegistros.ViewHolderDatos holder, int position) {
        holder.sustancia_t.setText(registro.get(position).getSustancia());
        holder.concentracion_t.setText(registro.get(position).getConcentracion());
        holder.masa_t.setText(registro.get(position).getMasa());
        holder.volumen_t.setText(registro.get(position).getVolumen());

    }

    @Override
    public int getItemCount() {
        return registro.size();
    }

    public  void setOnClickListener(View.OnClickListener escucha){
        this.escucha = escucha;
    }

    @Override
    public void onClick(View view) {
        if (escucha != null){
            escucha.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView sustancia_t, concentracion_t, masa_t, volumen_t;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            sustancia_t = (TextView) itemView.findViewById(R.id.idSust);
            concentracion_t = (TextView) itemView.findViewById(R.id.idConc);
            masa_t = (TextView) itemView.findViewById(R.id.idMas);
            volumen_t = (TextView) itemView.findViewById(R.id.idVol);
        }


    }
}
