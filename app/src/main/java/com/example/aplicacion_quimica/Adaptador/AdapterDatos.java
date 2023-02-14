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

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<Tarjeta_Portadas> tarjetas;
    private View.OnClickListener escucha;

    public AdapterDatos(ArrayList<Tarjeta_Portadas> tarjetas) {
        this.tarjetas = tarjetas;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, null, false);

        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.titulo.setText(tarjetas.get(position).getTitulo());
        holder.portada.setImageResource(tarjetas.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return tarjetas.size();
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

        TextView titulo;
        ImageView portada;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.idtxtNombre);
            portada = (ImageView) itemView.findViewById(R.id.idivcsm);
        }


    }
}
