package com.ricm.audifast.recyclerviewadapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ricm.audifast.R;
import com.ricm.audifast.entidades.AuditorAuxiliar;

import java.util.List;

public class listaAuxiliaresAdapter extends RecyclerView.Adapter<listaAuxiliaresAdapter.RowViewHolder> {
    List<AuditorAuxiliar> auxiliarList;

    public listaAuxiliaresAdapter(List<AuditorAuxiliar> auxiliarList) {
        this.auxiliarList = auxiliarList;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_auxiliares_item,parent,false);

        RowViewHolder vHolder = new RowViewHolder(itemView);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(RowViewHolder rowViewHolder, int position) {
        AuditorAuxiliar modal = auxiliarList.get(position);

        rowViewHolder.txtCorreo.setText(modal.getCorreo());
        rowViewHolder.txtNombre.setText(modal.getNombre());
        rowViewHolder.txtTelefono.setText(modal.getTelefono());
    }

    @Override
    public int getItemCount() {
        if(auxiliarList != null){
            return auxiliarList.size();
        }else{
            return 0;
        }

    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtCorreo;
        protected TextView txtNombre;
        protected TextView txtTelefono;

        public RowViewHolder(View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombreAuxiliar);
            txtCorreo = itemView.findViewById(R.id.txtCorreoAuxiliar);
            txtTelefono = itemView.findViewById(R.id.txtTelefonoAuxiliar);
        }
    }

    public void updateList (List<AuditorAuxiliar> listaActualizada) {
        if (listaActualizada != null && listaActualizada.size() > 0) {
            auxiliarList.clear();
            auxiliarList.addAll(listaActualizada);
        }
    }
}