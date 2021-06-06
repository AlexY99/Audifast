package com.ricm.audifast.recyclerviewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ricm.audifast.R;
import com.ricm.audifast.entidades.Proceso;
import com.ricm.audifast.entidades.Requisito;

import java.util.List;

public class listaRequisitosAdapter extends RecyclerView.Adapter<listaRequisitosAdapter.RowViewHolder>  {
    List<Requisito> requisitoList;

    public listaRequisitosAdapter(List<Requisito> requisitoList) {
        this.requisitoList = requisitoList;
    }

    public List<Requisito> getRequisitoList() {
        return requisitoList;
    }

    @Override
    public listaRequisitosAdapter.RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_requisitos_item,parent,false);
        listaRequisitosAdapter.RowViewHolder vHolder = new listaRequisitosAdapter.RowViewHolder(itemView,parent.getContext());
        vHolder.rgCumplimiento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 if(checkedId==vHolder.rb0.getId()){
                    requisitoList.get(vHolder.getAdapterPosition()).setCumplimiento(0);
                 }else if(checkedId==vHolder.rb1.getId()){
                     requisitoList.get(vHolder.getAdapterPosition()).setCumplimiento(1);
                 }else if(checkedId==vHolder.rb2.getId()){
                     requisitoList.get(vHolder.getAdapterPosition()).setCumplimiento(2);
                 }
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(listaRequisitosAdapter.RowViewHolder rowViewHolder, int position) {
        Requisito modal = requisitoList.get(position);

        rowViewHolder.txtDescripcion.setText(modal.getDescripcion());
        rowViewHolder.txtClaveNorma.setText(modal.getClave_norma());

        switch (modal.getCumplimiento()){
            case 1:
                rowViewHolder.rgCumplimiento.check(rowViewHolder.rb1.getId());
            break;
            case 2:
                rowViewHolder.rgCumplimiento.check(rowViewHolder.rb2.getId());
            break;
            default:
                rowViewHolder.rgCumplimiento.check(rowViewHolder.rb0.getId());
            break;
        }
    }

    @Override
    public int getItemCount() {
        if(requisitoList != null){
            return requisitoList.size();
        }else{
            return 0;
        }

    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtDescripcion;
        protected TextView txtClaveNorma;
        protected RadioGroup rgCumplimiento;
        protected RadioButton rb0;
        protected RadioButton rb1;
        protected RadioButton rb2;


        public RowViewHolder(View itemView,Context context) {
            super(itemView);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionRequisito);
            txtClaveNorma = itemView.findViewById(R.id.txtClaveNorma);
            rgCumplimiento = itemView.findViewById(R.id.radioGroupCumplimiento);
            rb0 = itemView.findViewById(R.id.radioButton0);
            rb1 = itemView.findViewById(R.id.radioButton1);
            rb2 = itemView.findViewById(R.id.radioButton2);
        }
    }

    public void updateList (List<Requisito> listaActualizada) {
        if (listaActualizada != null && listaActualizada.size() > 0) {
            requisitoList.clear();
            requisitoList.addAll(listaActualizada);
        }
    }
}
