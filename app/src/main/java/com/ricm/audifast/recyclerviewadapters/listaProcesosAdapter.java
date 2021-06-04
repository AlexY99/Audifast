package com.ricm.audifast.recyclerviewadapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ricm.audifast.R;
import com.ricm.audifast.entidades.Proceso;
import com.ricm.audifast.ui.infoAuditoria.InfoAuditoria;

import java.util.List;

public class listaProcesosAdapter extends RecyclerView.Adapter<listaProcesosAdapter.RowViewHolder>  {
    List<Proceso> procesoList;
    String correoAuditor;

    public listaProcesosAdapter(List<Proceso> procesoList,String correoAuditor) {
        this.procesoList = procesoList;
        this.correoAuditor = correoAuditor;
    }

    @Override
    public listaProcesosAdapter.RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_procesos_item,parent,false);

        listaProcesosAdapter.RowViewHolder vHolder = new listaProcesosAdapter.RowViewHolder(itemView,parent.getContext(),correoAuditor);

        vHolder.item_proceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correoEncargado = procesoList.get(vHolder.getAdapterPosition()).getCorreo_encargado();
                if(correoEncargado.equals(correoAuditor)){
                    int id = procesoList.get(vHolder.getAdapterPosition()).getId();
                    //cambiar clase por la de la actividad de evaluacion (recuerda que se manda la id del proceso, por eso ahora no funca bien)
                    Intent intent = new Intent(parent.getContext(), InfoAuditoria.class);
                    intent.putExtra("id",id);
                    parent.getContext().startActivity(intent);
                }else{
                    Toast.makeText(parent.getContext(),"No eres el encargado de este proceso",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(listaProcesosAdapter.RowViewHolder rowViewHolder, int position) {
        Proceso modal = procesoList.get(position);

        rowViewHolder.txtDescripcion.setText(modal.getDescripcion());
        rowViewHolder.txtPonderacion.setText(modal.getPonderacion()+"");
        rowViewHolder.txtEncargado.setText(modal.getEncargado());

        if(correoAuditor.equals(modal.getCorreo_encargado())){
            rowViewHolder.itemView.setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public int getItemCount() {
        if(procesoList != null){
            return procesoList.size();
        }else{
            return 0;
        }

    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtDescripcion;
        protected TextView txtPonderacion;
        protected TextView txtEncargado;
        protected LinearLayout item_proceso;

        public RowViewHolder(View itemView,Context context, String correoAuditor) {
            super(itemView);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionProceso);
            txtPonderacion = itemView.findViewById(R.id.txtPonderacionProceso);
            txtEncargado = itemView.findViewById(R.id.txtEncargadoProceso);
            item_proceso = itemView.findViewById(R.id.item_proceso);

        }
    }

    public void updateList (List<Proceso> listaActualizada) {
        if (listaActualizada != null && listaActualizada.size() > 0) {
            procesoList.clear();
            procesoList.addAll(listaActualizada);
        }
    }
}
