package com.ricm.audifast.recyclerviewadapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ricm.audifast.R;
import com.ricm.audifast.entidades.Auditoria;
import com.ricm.audifast.ui.infoAuditoria.InfoAuditoria;

import java.util.List;

public class listaAuditoriasAdapter extends RecyclerView.Adapter<listaAuditoriasAdapter.RowViewHolder> {
    List<Auditoria> auditoriaList;
    boolean sonLideradas;

    public listaAuditoriasAdapter(List<Auditoria> auditoriaList, boolean sonLideradas) {
        this.auditoriaList = auditoriaList;
        this.sonLideradas = sonLideradas;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_auditorias_item,null,false);

        RowViewHolder vHolder =  new RowViewHolder(itemView);
        vHolder.item_auditoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), InfoAuditoria.class);
                String id = vHolder.txtID.getText().toString();

                intent.putExtra("id",Integer.parseInt(id));
                parent.getContext().startActivity(intent);
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(RowViewHolder rowViewHolder, int position) {
        if(sonLideradas){
            rowViewHolder.txtCorreoLider.setVisibility(View.GONE);
        }

        Auditoria modal = auditoriaList.get(position);

        rowViewHolder.txtID.setText("Auditoría "+modal.getId());
        rowViewHolder.txtCorreoLider.setText("Auditor Líder: "+modal.getCorreo_auditor_lider());
        rowViewHolder.txtOrganizacion.setText("Organización: "+modal.getOrganizacion());
        rowViewHolder.txtFechaRegistro.setText("Creada: "+modal.getFecha_registro());
    }

    @Override
    public int getItemCount() {
        if(auditoriaList != null){
            return auditoriaList.size();
        }else{
            return 0;
        }

    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout item_auditoria;
        protected TextView txtID;
        protected TextView txtCorreoLider;
        protected TextView txtOrganizacion;
        protected TextView txtFechaRegistro;

        public RowViewHolder(View itemView) {
            super(itemView);

            item_auditoria = (LinearLayout) itemView.findViewById(R.id.item_auditoria);
            txtID = itemView.findViewById(R.id.txtID);
            txtCorreoLider = itemView.findViewById(R.id.txtCorreoLider);
            txtOrganizacion = itemView.findViewById(R.id.txtOrganizacion);
            txtFechaRegistro = itemView.findViewById(R.id.txtFechaRegistro);
        }
    }

    public void updateList (List<Auditoria> listaActualizada) {
        if (listaActualizada != null && listaActualizada.size() > 0) {
            auditoriaList.clear();
            auditoriaList.addAll(listaActualizada);
        }
    }
}