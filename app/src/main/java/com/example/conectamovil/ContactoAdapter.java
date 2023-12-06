package com.example.conectamovil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {
    private List<Contacto> listaContactos;

    public ContactoAdapter(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contacto_adapter, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto contacto = listaContactos.get(position);

        // Configurar la vista del elemento del contacto
        holder.textViewNombre.setText(contacto.getNombre());
        holder.textViewApellido.setText(contacto.getApellido());
        // Agrega más campos según sea necesario
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombre;
        public TextView textViewApellido;
        // Agrega más vistas según sea necesario

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewApellido = itemView.findViewById(R.id.textViewApellido);
            // Encuentra más vistas según sea necesario
        }
    }

    // Nuevo método para actualizar la lista de contactos
    public void actualizarLista(List<Contacto> nuevaLista) {
        this.listaContactos = nuevaLista;
        notifyDataSetChanged();
    }
}
