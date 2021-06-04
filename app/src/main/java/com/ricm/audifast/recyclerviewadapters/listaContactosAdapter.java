package com.ricm.audifast.recyclerviewadapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ricm.audifast.R;
import com.ricm.audifast.entidades.AuditorAuxiliar;
import com.ricm.audifast.entidades.Contacto;

import java.util.List;

public class listaContactosAdapter extends RecyclerView.Adapter<listaContactosAdapter.RowViewHolder> {
    List<Contacto> contactoList;

    public listaContactosAdapter(List<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_contactos_item,parent,false);

        RowViewHolder vHolder = new RowViewHolder(itemView);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(RowViewHolder rowViewHolder, int position) {
        Contacto modal = contactoList.get(position);

        rowViewHolder.txtCorreo.setText(modal.getCorreo());
        rowViewHolder.txtNombre.setText(modal.getNombre());
        rowViewHolder.txtPuesto.setText(modal.getPuesto());
        rowViewHolder.txtTelefono.setText(modal.getTelefono());
    }

    @Override
    public int getItemCount() {
        if(contactoList != null){
            return contactoList.size();
        }else{
            return 0;
        }

    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtCorreo;
        protected TextView txtNombre;
        protected TextView txtPuesto;
        protected TextView txtTelefono;

        public RowViewHolder(View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombreContacto);
            txtCorreo = itemView.findViewById(R.id.txtCorreoContacto);
            txtPuesto = itemView.findViewById(R.id.txtPuestoContacto);
            txtTelefono = itemView.findViewById(R.id.txtTelefonoContacto);
        }
    }

    public void updateList (List<Contacto> listaActualizada) {
        if (listaActualizada != null && listaActualizada.size() > 0) {
            contactoList.clear();
            contactoList.addAll(listaActualizada);
        }
    }
}